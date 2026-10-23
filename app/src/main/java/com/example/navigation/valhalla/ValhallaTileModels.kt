package com.example.navigation.valhalla

import com.example.model.GeoPoint

data class ValhallaNode(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double
) {
    fun toGeoPoint(): GeoPoint = GeoPoint(lat, lng)
}

data class ValhallaEdge(
    val from: String,
    val to: String,
    val ref: String,
    val name: String,
    val speedKmH: Int,
    val surface: String,
    val points: List<GeoPoint>,
    val lengthMeters: Double
)

enum class TurnType(val labelArabic: String, val iconSymbol: String) {
    DEPART("انطلق", "🚀"),
    CONTINUE("واصل السير", "⬆️"),
    TURN_RIGHT("انعطف يميناً", "↗️"),
    TURN_LEFT("انعطف يساراً", "↖️"),
    KEEP_RIGHT("الزم المسار الأيمن", "➡️"),
    KEEP_LEFT("الزم المسار الأيسر", "⬅️"),
    MOUNTAIN_PASS("نقيل جبلي / منعطفات", "⛰️"),
    ARRIVE("وصلت إلى وجهتك", "🏁")
}

data class NavigationManeuver(
    val stepIndex: Int,
    val instructionArabic: String,
    val turnType: TurnType,
    val roadName: String,
    val distanceMeters: Double,
    val durationSeconds: Int,
    val location: GeoPoint
)

data class OfflineRoute(
    val fromNode: ValhallaNode,
    val toNode: ValhallaNode,
    val totalDistanceKm: Double,
    val totalDurationMinutes: Int,
    val pathPoints: List<GeoPoint>,
    val maneuvers: List<NavigationManeuver>,
    val roadNames: List<String>,
    val usesValhallaTiles: Boolean = true
)

data class ValhallaEngineStatus(
    val isReady: Boolean,
    val packageVersion: String,
    val tilesArchiveName: String,
    val totalNodes: Int,
    val totalEdges: Int,
    val isOfflineAvailable: Boolean = true,
    val statusDescription: String
)
