package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CityDistance
import com.example.navigation.valhalla.OfflineRoute
import com.example.navigation.valhalla.TurnType
import com.example.navigation.valhalla.ValhallaOfflineNavigator
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineNavigationScreen(
    onShowRouteOnMap: (CityDistance) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ValhallaOfflineNavigator.initialize(context)
    }

    val nodes = remember { ValhallaOfflineNavigator.getAllNodes() }
    val engineStatus = remember { ValhallaOfflineNavigator.getStatus() }

    var fromNodeId by remember { mutableStateOf("sanaa") }
    var toNodeId by remember { mutableStateOf("aden") }
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    var isNavigating by remember { mutableStateOf(false) }
    var currentManeuverStep by remember { mutableIntStateOf(0) }
    var isSimulating by remember { mutableStateOf(false) }
    var showDiagnostics by remember { mutableStateOf(false) }

    val activeRoute = remember(fromNodeId, toNodeId) {
        ValhallaOfflineNavigator.findRoute(fromNodeId, toNodeId)
    }

    // Auto simulation runner
    LaunchedEffect(isSimulating, activeRoute) {
        if (isSimulating && activeRoute != null && activeRoute.maneuvers.isNotEmpty()) {
            while (isSimulating && currentManeuverStep < activeRoute.maneuvers.size - 1) {
                delay(2400)
                currentManeuverStep++
            }
            if (currentManeuverStep >= activeRoute.maneuvers.size - 1) {
                isSimulating = false
            }
        }
    }

    val currentManeuver = activeRoute?.maneuvers?.getOrNull(currentManeuverStep)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Valhalla Tiles Engine Status Banner
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0F2B48)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("valhalla_status_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(Color(0xFF10B981), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "محرك Valhalla المدمج (أوفلاين 100%)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Surface(
                            color = Color(0xFF10B981).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Tiles v3",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF34D399),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "حزمة البلاطات: ${engineStatus.tilesArchiveName} مدمجة بذاكرة التطبيق لتوجيه مسارات الملاحة والمنعطفات بدون الحاجة لأي اتصال بالإنترنت.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "📍 ${nodes.size} مركز وعقدة مفهرسة",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF93C5FD)
                        )
                        Text(
                            text = "🛣️ شبكة الطرق والأنقال الوطنية",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF93C5FD)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Diagnostic Toggle Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .clickable { showDiagnostics = !showDiagnostics }
                                .testTag("engine_diagnostics_toggle")
                        ) {
                            Text(
                                text = if (showDiagnostics) "إخفاء الفحص الهندسي والـ JSON ▲" else "🔬 فحص المحرك وعرض الـ JSON الخام ▼",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Expandable Diagnostic Details & Raw JSON
                    AnimatedVisibility(visible = showDiagnostics) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .background(Color(0xFF071B2D), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            val (elapsedMs, rawJson) = remember(fromNodeId, toNodeId, showDiagnostics) {
                                ValhallaOfflineNavigator.getRouteRawJson(fromNodeId, toNodeId)
                            }

                            Text(
                                text = "نتائج الفحص الهندسي المباشر (On-Device Benchmark):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "⚡ زمن معالجة المسار: $elapsedMs مللي ثانية (محلياً بدون اتصال)",
                                fontSize = 11.sp,
                                color = Color(0xFF4ADE80)
                            )
                            Text(
                                text = "📁 مصدر الحزمة: assets/valhalla/valhalla_tiles_yemen.zip",
                                fontSize = 10.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = "⚙️ الخوارزمية: Dijkstra Graph Router عبر طبقات البلاطات",
                                fontSize = 10.sp,
                                color = Color(0xFF94A3B8)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "محتوى الـ JSON المسترجع من المحرك (Valhalla Payload):",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE047)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = rawJson,
                                    fontSize = 10.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                    color = Color(0xFFA7F3D0),
                                    lineHeight = 14.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Origin and Destination Selector Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "تحديد مسار الملاحة بدون إنترنت",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // From Node
                    ExposedDropdownMenuBox(
                        expanded = fromExpanded,
                        onExpandedChange = { fromExpanded = !fromExpanded }
                    ) {
                        OutlinedTextField(
                            value = nodes.firstOrNull { it.id == fromNodeId }?.name ?: fromNodeId,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("نقطة الانطلاق (البداية)") },
                            leadingIcon = {
                                Icon(Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("nav_from_input")
                        )
                        ExposedDropdownMenu(
                            expanded = fromExpanded,
                            onDismissRequest = { fromExpanded = false }
                        ) {
                            nodes.forEach { node ->
                                DropdownMenuItem(
                                    text = { Text(node.name) },
                                    onClick = {
                                        fromNodeId = node.id
                                        fromExpanded = false
                                        currentManeuverStep = 0
                                        isSimulating = false
                                    }
                                )
                            }
                        }
                    }

                    // Swap Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                val temp = fromNodeId
                                fromNodeId = toNodeId
                                toNodeId = temp
                                currentManeuverStep = 0
                                isSimulating = false
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                .size(36.dp)
                        ) {
                            Icon(Icons.Default.SwapVert, contentDescription = "تبديل", modifier = Modifier.size(20.dp))
                        }
                    }

                    // To Node
                    ExposedDropdownMenuBox(
                        expanded = toExpanded,
                        onExpandedChange = { toExpanded = !toExpanded }
                    ) {
                        OutlinedTextField(
                            value = nodes.firstOrNull { it.id == toNodeId }?.name ?: toNodeId,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("وجهة الوصول (النهاية)") },
                            leadingIcon = {
                                Icon(Icons.Default.Navigation, contentDescription = null, tint = Color(0xFFEF4444))
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("nav_to_input")
                        )
                        ExposedDropdownMenu(
                            expanded = toExpanded,
                            onDismissRequest = { toExpanded = false }
                        ) {
                            nodes.forEach { node ->
                                DropdownMenuItem(
                                    text = { Text(node.name) },
                                    onClick = {
                                        toNodeId = node.id
                                        toExpanded = false
                                        currentManeuverStep = 0
                                        isSimulating = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Route Summary & Navigation Actions
        activeRoute?.let { route ->
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${route.totalDistanceKm} كم",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                val hours = route.totalDurationMinutes / 60
                                val mins = route.totalDurationMinutes % 60
                                Text(
                                    text = "زمن السفر: $hours س و $mins د",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "أسرع مسار أوفلاين",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Control Buttons: Start Navigation & Show on Map
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    isNavigating = true
                                    isSimulating = !isSimulating
                                },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("start_valhalla_nav_btn"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSimulating) Color(0xFFDC2626) else MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(
                                    imageVector = if (isSimulating) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isSimulating) "إيقاف مؤقت" else "بدء الملاحة الحية")
                            }

                            OutlinedButton(
                                onClick = {
                                    onShowRouteOnMap(
                                        CityDistance(
                                            fromCity = route.fromNode.name,
                                            toCity = route.toNode.name,
                                            distanceKm = route.totalDistanceKm.toInt(),
                                            drivingHours = route.totalDurationMinutes / 60.0,
                                            roadCondition = "مسار Valhalla المدمج",
                                            routeDescription = route.roadNames.joinToString(" ⟵ "),
                                            waypoints = route.roadNames
                                        )
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("على الخريطة")
                            }
                        }
                    }
                }
            }

            // Live Turn HUD (when navigating or step active)
            currentManeuver?.let { maneuver ->
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("nav_turn_hud")
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = maneuver.turnType.iconSymbol,
                                        fontSize = 32.sp
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = maneuver.instructionArabic,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = maneuver.roadName,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White.copy(alpha = 0.85f)
                                        )
                                    }
                                }

                                if (maneuver.distanceMeters > 0) {
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "${(maneuver.distanceMeters / 1000.0).toInt()} كم",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "المسافة للخطوة",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White.copy(alpha = 0.75f)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Step Progress Bar
                            LinearProgressIndicator(
                                progress = {
                                    (currentManeuverStep + 1) / route.maneuvers.size.toFloat()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = Color.White,
                                trackColor = Color.White.copy(alpha = 0.25f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Step Switchers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "الخطوة ${currentManeuverStep + 1} من ${route.maneuvers.size}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (currentManeuverStep > 0) {
                                        Button(
                                            onClick = { currentManeuverStep-- },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("السابق", color = Color.White, fontSize = 11.sp)
                                        }
                                    }
                                    if (currentManeuverStep < route.maneuvers.size - 1) {
                                        Button(
                                            onClick = { currentManeuverStep++ },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.25f)),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("التالي", color = Color.White, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Step-by-Step Maneuver Instructions List
            item {
                Text(
                    text = "خطوات وإرشادات الملاحة بالتفصيل (${route.maneuvers.size} خطوات)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            itemsIndexed(route.maneuvers) { index, maneuver ->
                val isCurrent = index == currentManeuverStep
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { currentManeuverStep = index }
                        .then(
                            if (isCurrent) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                            else Modifier
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrent) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = maneuver.turnType.iconSymbol,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = maneuver.instructionArabic,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = maneuver.roadName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (maneuver.distanceMeters > 0) {
                            Text(
                                text = "${(maneuver.distanceMeters / 1000.0).toInt()} كم",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
