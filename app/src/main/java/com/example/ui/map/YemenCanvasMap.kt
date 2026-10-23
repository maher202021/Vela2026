package com.example.ui.map

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.navigation.valhalla.ValhallaOfflineNavigator
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.YemenDataProvider
import com.example.model.CityDistance
import com.example.model.GeoPoint
import com.example.model.Governorate
import com.example.model.Landmark
import com.example.model.MapFilterState
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

// Geographical bounds for Yemen map projection
private const val MIN_LNG = 41.8
private const val MAX_LNG = 54.8
private const val MIN_LAT = 11.9
private const val MAX_LAT = 18.6

@Composable
fun YemenCanvasMap(
    modifier: Modifier = Modifier,
    selectedGovernorate: Governorate? = null,
    selectedLandmark: Landmark? = null,
    highlightedRoute: CityDistance? = null,
    filterState: MapFilterState = MapFilterState(),
    onSelectGovernorate: (Governorate) -> Unit = {},
    onSelectLandmark: (Landmark) -> Unit = {},
    onClearSelection: () -> Unit = {}
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.85f, 4.5f)
        offset = Offset(
            x = (offset.x + offsetChange.x).coerceIn(-1200f * scale, 1200f * scale),
            y = (offset.y + offsetChange.y).coerceIn(-1200f * scale, 1200f * scale)
        )
    }

    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val seaColor = if (isDark) Color(0xFF071824) else Color(0xFFE2F1F8)
    val seaTextColor = if (isDark) Color(0xFF2E5E7A) else Color(0xFF6B9BB3)
    val neighborColor = if (isDark) Color(0xFF16202A) else Color(0xFFE2E8F0)
    val neighborTextColor = if (isDark) Color(0xFF475569) else Color(0xFF94A3B8)
    val defaultBorderColor = if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(seaColor)
            .testTag("yemen_canvas_map_container")
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // Coordinate transformation function
        fun geoToCanvas(point: GeoPoint): Offset {
            val padding = 40f
            val drawWidth = width - (padding * 2)
            val drawHeight = height - (padding * 2)

            val normX = ((point.lng - MIN_LNG) / (MAX_LNG - MIN_LNG)).toFloat()
            val normY = (1f - ((point.lat - MIN_LAT) / (MAX_LAT - MIN_LAT))).toFloat()

            val canvasX = (padding + (normX * drawWidth) * 0.95f) * scale + offset.x
            val canvasY = (padding + (normY * drawHeight) * 0.88f + (height * 0.06f)) * scale + offset.y

            return Offset(canvasX, canvasY)
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = transformState)
                .pointerInput(selectedGovernorate, scale, offset) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            // Check landmark tap first
                            if (filterState.showLandmarks) {
                                for (landmark in YemenDataProvider.landmarks) {
                                    val screenPos = geoToCanvas(landmark.location)
                                    val dist = sqrt(
                                        (screenPos.x - tapOffset.x) * (screenPos.x - tapOffset.x) +
                                        (screenPos.y - tapOffset.y) * (screenPos.y - tapOffset.y)
                                    )
                                    if (dist < 40f * scale) {
                                        onSelectLandmark(landmark)
                                        return@detectTapGestures
                                    }
                                }
                            }

                            // Check governorate tap by proximity to center or polygon
                            var closestGov: Governorate? = null
                            var minDistance = Float.MAX_VALUE

                            for (gov in YemenDataProvider.governorates) {
                                val centerPos = geoToCanvas(gov.center)
                                val dist = sqrt(
                                    (centerPos.x - tapOffset.x) * (centerPos.x - tapOffset.x) +
                                    (centerPos.y - tapOffset.y) * (centerPos.y - tapOffset.y)
                                )
                                // Weight by area so larger governorates get slightly larger tap radii
                                val maxThreshold = (if (gov.areaKm2 > 30000) 110f else 65f) * scale
                                if (dist < maxThreshold && dist < minDistance) {
                                    minDistance = dist
                                    closestGov = gov
                                }
                            }

                            if (closestGov != null) {
                                onSelectGovernorate(closestGov)
                            } else {
                                onClearSelection()
                            }
                        }
                    )
                }
        ) {
            val textPaint = Paint().apply {
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
            }

            // Draw Neighboring Countries and Maritime Waters labels
            drawWaterAndNeighborLabels(
                geoToCanvas = ::geoToCanvas,
                isDark = isDark,
                seaTextColor = seaTextColor,
                neighborTextColor = neighborTextColor,
                textPaint = textPaint,
                scale = scale
            )

            // Draw Governorate Polygons
            for (gov in YemenDataProvider.governorates) {
                if (gov.polygon.size < 3) continue

                val path = Path()
                val firstPoint = geoToCanvas(gov.polygon.first())
                path.moveTo(firstPoint.x, firstPoint.y)
                for (i in 1 until gov.polygon.size) {
                    val pt = geoToCanvas(gov.polygon[i])
                    path.lineTo(pt.x, pt.y)
                }
                path.close()

                val isSelected = selectedGovernorate?.id == gov.id
                val fillColor = if (isSelected) {
                    Color(0xFFE29547).copy(alpha = 0.85f)
                } else {
                    val baseColor = Color(gov.colorHex)
                    if (isDark) baseColor.copy(alpha = 0.35f) else baseColor.copy(alpha = 0.22f)
                }

                drawPath(path = path, color = fillColor, style = Fill)

                if (filterState.showBorders) {
                    val strokeColor = if (isSelected) Color(0xFFC2410C) else defaultBorderColor
                    val strokeWidth = if (isSelected) 3.5f * scale else 1.5f
                    drawPath(
                        path = path,
                        color = strokeColor,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }

            // Draw Active Travel Route if available
            highlightedRoute?.let { route ->
                val fromNode = ValhallaOfflineNavigator.getAllNodes().firstOrNull { it.name.contains(route.fromCity) || route.fromCity.contains(it.name) }
                val toNode = ValhallaOfflineNavigator.getAllNodes().firstOrNull { it.name.contains(route.toCity) || route.toCity.contains(it.name) }
                val valhallaRoute = if (fromNode != null && toNode != null) {
                    ValhallaOfflineNavigator.findRoute(fromNode.id, toNode.id)
                } else null

                val routePoints = valhallaRoute?.pathPoints?.map { geoToCanvas(it) }

                if (routePoints != null && routePoints.size >= 2) {
                    val polyline = Path().apply {
                        moveTo(routePoints.first().x, routePoints.first().y)
                        for (i in 1 until routePoints.size) {
                            lineTo(routePoints[i].x, routePoints[i].y)
                        }
                    }

                    // Route casing glow
                    drawPath(
                        path = polyline,
                        color = Color(0xFF0284C7).copy(alpha = 0.35f),
                        style = Stroke(width = 10f * scale)
                    )

                    // Core Route Line
                    drawPath(
                        path = polyline,
                        color = Color(0xFF0284C7),
                        style = Stroke(
                            width = 4.5f * scale,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 8f), 0f)
                        )
                    )

                    // Draw start and end pins
                    val start = routePoints.first()
                    val end = routePoints.last()
                    drawCircle(color = Color(0xFF1E293B), radius = 7f * scale, center = start)
                    drawCircle(color = Color(0xFF10B981), radius = 5f * scale, center = start)
                    drawCircle(color = Color(0xFF1E293B), radius = 7f * scale, center = end)
                    drawCircle(color = Color(0xFFEF4444), radius = 5f * scale, center = end)
                } else {
                    val fromGov = YemenDataProvider.governorates.firstOrNull { it.nameArabic.contains(route.fromCity) || it.capital.contains(route.fromCity) }
                    val toGov = YemenDataProvider.governorates.firstOrNull { it.nameArabic.contains(route.toCity) || it.capital.contains(route.toCity) }

                    if (fromGov != null && toGov != null) {
                        val start = geoToCanvas(fromGov.center)
                        val end = geoToCanvas(toGov.center)

                        val routePath = Path().apply {
                            moveTo(start.x, start.y)
                            val midX = (start.x + end.x) / 2f
                            val midY = (start.y + end.y) / 2f - (40f * scale)
                            quadraticTo(midX, midY, end.x, end.y)
                        }

                        drawPath(
                            path = routePath,
                            color = Color(0xFFDC2626).copy(alpha = 0.3f),
                            style = Stroke(width = 8f * scale)
                        )

                        drawPath(
                            path = routePath,
                            color = Color(0xFFE11D48),
                            style = Stroke(
                                width = 3.5f * scale,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                            )
                        )

                        drawCircle(color = Color(0xFF1E293B), radius = 6f * scale, center = start)
                        drawCircle(color = Color(0xFF10B981), radius = 4f * scale, center = start)
                        drawCircle(color = Color(0xFF1E293B), radius = 6f * scale, center = end)
                        drawCircle(color = Color(0xFFEF4444), radius = 4f * scale, center = end)
                    }
                }
            }

            // Draw Governorate Centers & Labels
            if (filterState.showGovernorateNames || filterState.showCities) {
                for (gov in YemenDataProvider.governorates) {
                    val center = geoToCanvas(gov.center)
                    val isSelected = selectedGovernorate?.id == gov.id

                    // Marker Dot
                    val dotColor = if (isSelected) Color(0xFFB91C1C) else if (isDark) Color(0xFFF1F5F9) else Color(0xFF0F172A)
                    val dotRadius = if (isSelected) 6f * scale else 4f * scale
                    drawCircle(color = dotColor, radius = dotRadius, center = center)

                    // Pin Ring for selected
                    if (isSelected) {
                        drawCircle(
                            color = Color(0xFFB91C1C).copy(alpha = 0.35f),
                            radius = 14f * scale,
                            center = center
                        )
                    }

                    // Text Label
                    if (filterState.showGovernorateNames) {
                        drawContext.canvas.nativeCanvas.apply {
                            textPaint.color = if (isSelected) {
                                android.graphics.Color.parseColor("#991B1B")
                            } else if (isDark) {
                                android.graphics.Color.WHITE
                            } else {
                                android.graphics.Color.parseColor("#0F172A")
                            }
                            textPaint.textSize = (if (isSelected) 13f else 11f) * scale.coerceIn(0.9f, 2.2f)
                            textPaint.isFakeBoldText = isSelected || gov.areaKm2 > 30000

                            val label = gov.nameArabic
                            drawText(label, center.x, center.y - (8f * scale), textPaint)

                            if (filterState.showCities && scale > 1.2f && gov.capital != gov.nameArabic) {
                                textPaint.textSize = 9f * scale.coerceIn(0.8f, 1.8f)
                                textPaint.color = if (isDark) android.graphics.Color.LTGRAY else android.graphics.Color.DKGRAY
                                textPaint.isFakeBoldText = false
                                drawText("(${gov.capital})", center.x, center.y + (12f * scale), textPaint)
                            }
                        }
                    }
                }
            }

            // Draw Landmark Markers
            if (filterState.showLandmarks) {
                for (landmark in YemenDataProvider.landmarks) {
                    val pos = geoToCanvas(landmark.location)
                    val isSelected = selectedLandmark?.id == landmark.id

                    val iconBgColor = when (landmark.category) {
                        com.example.model.LandmarkCategory.HISTORICAL -> Color(0xFFD97706)
                        com.example.model.LandmarkCategory.MOUNTAIN -> Color(0xFF4B5563)
                        com.example.model.LandmarkCategory.WATER_COAST -> Color(0xFF0284C7)
                        com.example.model.LandmarkCategory.NATURE_ISLAND -> Color(0xFF10B981)
                    }

                    // Shadow / Outer Circle
                    drawCircle(
                        color = Color.Black.copy(alpha = 0.25f),
                        radius = (if (isSelected) 9f else 7f) * scale,
                        center = Offset(pos.x, pos.y + 2f)
                    )
                    // Inner Circle
                    drawCircle(
                        color = if (isSelected) Color(0xFFEF4444) else iconBgColor,
                        radius = (if (isSelected) 8f else 6f) * scale,
                        center = pos
                    )
                    drawCircle(
                        color = Color.White,
                        radius = (if (isSelected) 3.5f else 2.5f) * scale,
                        center = pos
                    )

                    // Landmark Label if zoomed in or selected
                    if (scale > 1.4f || isSelected) {
                        drawContext.canvas.nativeCanvas.apply {
                            textPaint.color = if (isDark) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                            textPaint.textSize = (if (isSelected) 10f else 9f) * scale.coerceIn(0.8f, 1.6f)
                            textPaint.isFakeBoldText = true
                            drawText(landmark.name, pos.x, pos.y - (10f * scale), textPaint)
                        }
                    }
                }
            }
        }

        // Overlay Map Controls (Zoom, Reset, Socotra Focus, Compass)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Focus on Socotra Quick Button
            SmallFloatingActionButton(
                onClick = {
                    // Center and zoom towards Socotra archipelago
                    scale = 2.4f
                    val socotraPt = geoToCanvas(GeoPoint(12.46, 53.82))
                    offset = Offset(
                        x = -(socotraPt.x - width / 2f),
                        y = -(socotraPt.y - height / 2f)
                    )
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.testTag("focus_socotra_btn")
            ) {
                Text("سقطرى", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Reset Map View
            SmallFloatingActionButton(
                onClick = {
                    scale = 1f
                    offset = Offset.Zero
                    onClearSelection()
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("reset_map_btn")
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = "إعادة ضبط الخريطة")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Zoom In (+)
            SmallFloatingActionButton(
                onClick = { scale = (scale + 0.35f).coerceAtMost(4.5f) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("zoom_in_btn")
            ) {
                Icon(Icons.Default.Add, contentDescription = "تكبير الخريطة")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Zoom Out (-)
            SmallFloatingActionButton(
                onClick = { scale = (scale - 0.35f).coerceAtLeast(0.85f) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("zoom_out_btn")
            ) {
                Icon(Icons.Default.Remove, contentDescription = "تصغير الخريطة")
            }
        }

        // Compass Rose / North Indicator at Top Left
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CompassCalibration,
                    contentDescription = "اتجاه الشمال",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "شمال N",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Scale bar indicator at bottom left
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${(150 / scale).toInt()} كم",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(1.5.dp))
                )
            }
        }
    }
}

private fun DrawScope.drawWaterAndNeighborLabels(
    geoToCanvas: (GeoPoint) -> Offset,
    isDark: Boolean,
    seaTextColor: Color,
    neighborTextColor: Color,
    textPaint: Paint,
    scale: Float
) {
    drawContext.canvas.nativeCanvas.apply {
        // Red Sea (البحر الأحمر)
        val redSea = geoToCanvas(GeoPoint(15.2, 42.2))
        textPaint.color = seaTextColor.hashCode()
        textPaint.textSize = 13f * scale.coerceIn(0.9f, 2.0f)
        textPaint.isFakeBoldText = true
        drawText("البحر الأحمر", redSea.x, redSea.y, textPaint)

        // Gulf of Aden (خليج عدن)
        val gulfOfAden = geoToCanvas(GeoPoint(12.3, 46.2))
        drawText("خـلـيـج عــدن", gulfOfAden.x, gulfOfAden.y, textPaint)

        // Arabian Sea (بحر العرب)
        val arabianSea = geoToCanvas(GeoPoint(14.0, 51.8))
        drawText("بـحـر الـعـرب", arabianSea.x, arabianSea.y, textPaint)

        // Bab al Mandeb Strait (مضيق باب المندب)
        val babMandeb = geoToCanvas(GeoPoint(12.5, 43.15))
        textPaint.textSize = 10f * scale.coerceIn(0.8f, 1.6f)
        drawText("مضيق باب المندب", babMandeb.x, babMandeb.y, textPaint)

        // Saudi Arabia (المملكة العربية السعودية)
        val saudi = geoToCanvas(GeoPoint(17.8, 45.8))
        textPaint.color = neighborTextColor.hashCode()
        textPaint.textSize = 12f * scale.coerceIn(0.9f, 1.8f)
        textPaint.isFakeBoldText = true
        drawText("المملكة العربية السعودية", saudi.x, saudi.y, textPaint)

        // Sultanate of Oman (سلطنة عُمان)
        val oman = geoToCanvas(GeoPoint(17.2, 53.6))
        drawText("سلطنة عُمان", oman.x, oman.y, textPaint)
    }
}
