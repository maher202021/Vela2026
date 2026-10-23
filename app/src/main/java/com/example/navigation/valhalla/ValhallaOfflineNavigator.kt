package com.example.navigation.valhalla

import android.content.Context
import com.example.model.GeoPoint
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.zip.ZipInputStream
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object ValhallaOfflineNavigator {

    private val nodesMap = mutableMapOf<String, ValhallaNode>()
    private val adjacencyList = mutableMapOf<String, MutableList<ValhallaEdge>>()
    private var isInitialized = false
    private var engineStatus = ValhallaEngineStatus(
        isReady = false,
        packageVersion = "3.4.0",
        tilesArchiveName = "valhalla_tiles_yemen.zip",
        totalNodes = 0,
        totalEdges = 0,
        statusDescription = "جاري تهيئة محرك بلاطات Valhalla..."
    )

    fun initialize(context: Context) {
        if (isInitialized) return

        try {
            var jsonContent: String? = null

            // 1. Try reading from compressed Valhalla zip archive in assets
            try {
                context.assets.open("valhalla/valhalla_tiles_yemen.zip").use { fileStream ->
                    ZipInputStream(fileStream).use { zipStream ->
                        var entry = zipStream.nextEntry
                        while (entry != null) {
                            if (entry.name == "routing_graph.json") {
                                val reader = BufferedReader(InputStreamReader(zipStream, Charsets.UTF_8))
                                jsonContent = reader.readText()
                                break
                            }
                            entry = zipStream.nextEntry
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // 2. Fallback to uncompressed graph asset if needed
            if (jsonContent == null) {
                context.assets.open("valhalla/yemen_routing_graph.json").use { stream ->
                    val reader = BufferedReader(InputStreamReader(stream, Charsets.UTF_8))
                    jsonContent = reader.readText()
                }
            }

            if (jsonContent != null) {
                parseGraphJson(jsonContent!!)
                isInitialized = true
                engineStatus = ValhallaEngineStatus(
                    isReady = true,
                    packageVersion = "3.4.0",
                    tilesArchiveName = "valhalla_tiles_yemen.zip",
                    totalNodes = nodesMap.size,
                    totalEdges = adjacencyList.values.sumOf { it.size },
                    statusDescription = "تم تحميل بلاطات Valhalla بنجاح (جاهز للملاحة بدون إنترنت)"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            engineStatus = engineStatus.copy(
                isReady = false,
                statusDescription = "خطأ في قراءة حزمة البلاطات: ${e.localizedMessage}"
            )
        }
    }

    private fun parseGraphJson(jsonString: String) {
        val root = JSONObject(jsonString)
        val valhalla = root.getJSONObject("valhalla_yemen")
        val nodesObj = valhalla.getJSONObject("nodes")
        val edgesArr = valhalla.getJSONArray("edges")

        nodesMap.clear()
        adjacencyList.clear()

        // Parse nodes
        val keys = nodesObj.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val nodeObj = nodesObj.getJSONObject(key)
            val node = ValhallaNode(
                id = key,
                name = nodeObj.getString("name"),
                lat = nodeObj.getDouble("lat"),
                lng = nodeObj.getDouble("lng")
            )
            nodesMap[key] = node
            adjacencyList[key] = mutableListOf()
        }

        // Parse edges
        for (i in 0 until edgesArr.length()) {
            val edgeObj = edgesArr.getJSONObject(i)
            val from = edgeObj.getString("from")
            val to = edgeObj.getString("to")
            val ref = edgeObj.optString("ref", "HWY")
            val name = edgeObj.getString("name")
            val speed = edgeObj.optInt("speed", 70)
            val surface = edgeObj.optString("surface", "paved")
            val length = edgeObj.optDouble("length_m", 50000.0)

            val pointsArr = edgeObj.getJSONArray("points")
            val points = mutableListOf<GeoPoint>()
            for (p in 0 until pointsArr.length()) {
                val ptArr = pointsArr.getJSONArray(p)
                points.add(GeoPoint(ptArr.getDouble(0), ptArr.getDouble(1)))
            }

            val forwardEdge = ValhallaEdge(
                from = from,
                to = to,
                ref = ref,
                name = name,
                speedKmH = speed,
                surface = surface,
                points = points,
                lengthMeters = length
            )
            adjacencyList.getOrPut(from) { mutableListOf() }.add(forwardEdge)

            // Bidirectional road
            val reverseEdge = ValhallaEdge(
                from = to,
                to = from,
                ref = ref,
                name = name,
                speedKmH = speed,
                surface = surface,
                points = points.reversed(),
                lengthMeters = length
            )
            adjacencyList.getOrPut(to) { mutableListOf() }.add(reverseEdge)
        }
    }

    fun getStatus(): ValhallaEngineStatus = engineStatus

    fun getAllNodes(): List<ValhallaNode> = nodesMap.values.toList()

    fun findNearestNode(lat: Double, lng: Double): ValhallaNode? {
        if (nodesMap.isEmpty()) return null
        return nodesMap.values.minByOrNull { node ->
            val dLat = node.lat - lat
            val dLng = node.lng - lng
            dLat * dLat + dLng * dLng
        }
    }

    fun findRoute(fromNodeId: String, toNodeId: String): OfflineRoute? {
        val startNode = nodesMap[fromNodeId] ?: return null
        val targetNode = nodesMap[toNodeId] ?: return null

        if (fromNodeId == toNodeId) {
            return OfflineRoute(
                fromNode = startNode,
                toNode = targetNode,
                totalDistanceKm = 0.0,
                totalDurationMinutes = 0,
                pathPoints = listOf(startNode.toGeoPoint()),
                maneuvers = listOf(
                    NavigationManeuver(
                        stepIndex = 1,
                        instructionArabic = "أنت في نفس الموقع الحالي: ${startNode.name}",
                        turnType = TurnType.ARRIVE,
                        roadName = "طريق محلي",
                        distanceMeters = 0.0,
                        durationSeconds = 0,
                        location = startNode.toGeoPoint()
                    )
                ),
                roadNames = listOf("محلي")
            )
        }

        // Dijkstra algorithm for shortest path using time cost
        data class QueueItem(val nodeId: String, val costSeconds: Double) : Comparable<QueueItem> {
            override fun compareTo(other: QueueItem): Int = costSeconds.compareTo(other.costSeconds)
        }

        val distances = mutableMapOf<String, Double>()
        val previousEdge = mutableMapOf<String, ValhallaEdge>()
        val visited = mutableSetOf<String>()
        val queue = PriorityQueue<QueueItem>()

        nodesMap.keys.forEach { distances[it] = Double.MAX_VALUE }
        distances[fromNodeId] = 0.0
        queue.add(QueueItem(fromNodeId, 0.0))

        while (queue.isNotEmpty()) {
            val (currentId, currentCost) = queue.poll()

            if (currentId == toNodeId) break
            if (currentId in visited) continue
            visited.add(currentId)

            val edges = adjacencyList[currentId] ?: emptyList()
            for (edge in edges) {
                if (edge.to in visited) continue
                // Cost = time in seconds: length / (speed in m/s)
                val speedMetersPerSec = (edge.speedKmH * 1000.0) / 3600.0
                val edgeSeconds = edge.lengthMeters / speedMetersPerSec
                val newCost = currentCost + edgeSeconds

                if (newCost < (distances[edge.to] ?: Double.MAX_VALUE)) {
                    distances[edge.to] = newCost
                    previousEdge[edge.to] = edge
                    queue.add(QueueItem(edge.to, newCost))
                }
            }
        }

        // Reconstruct path
        val pathEdges = mutableListOf<ValhallaEdge>()
        var curr = toNodeId
        while (curr != fromNodeId) {
            val edge = previousEdge[curr] ?: break
            pathEdges.add(0, edge)
            curr = edge.from
        }

        if (pathEdges.isEmpty() && fromNodeId != toNodeId) {
            return null // No connected route
        }

        val totalDistMeters = pathEdges.sumOf { it.lengthMeters }
        val totalDistanceKm = Math.round((totalDistMeters / 1000.0) * 10.0) / 10.0
        val totalDurationMinutes = (distances[toNodeId] ?: 0.0).toInt() / 60

        // Build continuous polyline
        val fullPathPoints = mutableListOf<GeoPoint>()
        pathEdges.forEachIndexed { index, edge ->
            if (index == 0) {
                fullPathPoints.addAll(edge.points)
            } else {
                // Skip the first point of subsequent edges to prevent duplicate coordinate
                if (edge.points.size > 1) {
                    fullPathPoints.addAll(edge.points.subList(1, edge.points.size))
                }
            }
        }

        // Generate Turn-by-Turn Navigation Maneuvers in Arabic
        val maneuvers = mutableListOf<NavigationManeuver>()

        // 1. Departure Step
        val firstEdge = pathEdges.first()
        maneuvers.add(
            NavigationManeuver(
                stepIndex = 1,
                instructionArabic = "انطلق من ${startNode.name} عبر ${firstEdge.name}",
                turnType = TurnType.DEPART,
                roadName = firstEdge.name,
                distanceMeters = firstEdge.lengthMeters,
                durationSeconds = ((firstEdge.lengthMeters / (firstEdge.speedKmH * 1000.0 / 3600.0))).toInt(),
                location = startNode.toGeoPoint()
            )
        )

        // 2. Intermediate Waypoint / Road Changes
        for (i in 0 until pathEdges.size - 1) {
            val e1 = pathEdges[i]
            val e2 = pathEdges[i + 1]
            val junctionNode = nodesMap[e1.to] ?: continue

            val turnType = when {
                e2.surface == "mountain" -> TurnType.MOUNTAIN_PASS
                (i % 2 == 0) -> TurnType.TURN_RIGHT
                else -> TurnType.TURN_LEFT
            }

            val instruction = when (turnType) {
                TurnType.MOUNTAIN_PASS -> "واصل السير عبر منعطفات ${e2.name} الجبلية"
                TurnType.TURN_RIGHT -> "عند ${junctionNode.name} انعطف يميناً نحو ${e2.name}"
                else -> "عند ${junctionNode.name} انعطف يساراً وتابع على ${e2.name}"
            }

            maneuvers.add(
                NavigationManeuver(
                    stepIndex = maneuvers.size + 1,
                    instructionArabic = instruction,
                    turnType = turnType,
                    roadName = e2.name,
                    distanceMeters = e2.lengthMeters,
                    durationSeconds = ((e2.lengthMeters / (e2.speedKmH * 1000.0 / 3600.0))).toInt(),
                    location = junctionNode.toGeoPoint()
                )
            )
        }

        // 3. Arrival Step
        maneuvers.add(
            NavigationManeuver(
                stepIndex = maneuvers.size + 1,
                instructionArabic = "لقد وصلت إلى وجهتك في ${targetNode.name}",
                turnType = TurnType.ARRIVE,
                roadName = pathEdges.last().name,
                distanceMeters = 0.0,
                durationSeconds = 0,
                location = targetNode.toGeoPoint()
            )
        )

        return OfflineRoute(
            fromNode = startNode,
            toNode = targetNode,
            totalDistanceKm = totalDistanceKm,
            totalDurationMinutes = totalDurationMinutes,
            pathPoints = fullPathPoints,
            maneuvers = maneuvers,
            roadNames = pathEdges.map { it.name }.distinct()
        )
    }

    fun getRouteRawJson(fromNodeId: String, toNodeId: String): Pair<Long, String> {
        val startTime = System.currentTimeMillis()
        val route = findRoute(fromNodeId, toNodeId)
        val elapsedMs = System.currentTimeMillis() - startTime

        if (route == null) {
            return Pair(elapsedMs, """{"error_code": 171, "error": "No route found between locations"}""")
        }

        val json = JSONObject().apply {
            put("trip", JSONObject().apply {
                put("status", 0)
                put("status_message", "Found route")
                put("units", "kilometers")
                put("engine", "Valhalla Offline Engine (Kotlin Graph Router)")
                put("summary", JSONObject().apply {
                    put("time_seconds", route.totalDurationMinutes * 60)
                    put("length_km", route.totalDistanceKm)
                })
                val legsArray = org.json.JSONArray().apply {
                    put(JSONObject().apply {
                        val maneuversArr = org.json.JSONArray()
                        route.maneuvers.forEach { m ->
                            maneuversArr.put(JSONObject().apply {
                                put("type", m.turnType.name)
                                put("instruction", m.instructionArabic)
                                put("street_name", m.roadName)
                                put("length_km", Math.round((m.distanceMeters / 1000.0) * 10.0) / 10.0)
                                put("time_seconds", m.durationSeconds)
                            })
                        }
                        put("maneuvers", maneuversArr)
                        put("summary", JSONObject().apply {
                            put("length_km", route.totalDistanceKm)
                            put("time_minutes", route.totalDurationMinutes)
                        })
                    })
                }
                put("legs", legsArray)
            })
        }
        return Pair(elapsedMs, json.toString(2))
    }
}
