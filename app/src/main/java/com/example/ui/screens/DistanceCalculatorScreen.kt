package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.YemenDataProvider
import com.example.model.CityDistance
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceCalculatorScreen(
    initialCity: String? = null,
    onShowRouteOnMap: (CityDistance) -> Unit,
    modifier: Modifier = Modifier
) {
    val allCities = listOf(
        "صنعاء", "عدن", "تعز", "الحديدة", "المكلا", "سيئون", "إب", "ذمار",
        "مأرب", "صعدة", "عمران", "حجة", "الغيضة", "زنجبار", "الحوطة (لحج)",
        "الضالع", "عتق", "البيضاء", "المحويت", "الجبين (ريمة)", "الحزم (الجوف)"
    )

    var fromCity by remember { mutableStateOf(initialCity ?: "صنعاء") }
    var toCity by remember { mutableStateOf("عدن") }
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    // Find predefined route or calculate approximate road distance
    val calculatedRoute = remember(fromCity, toCity) {
        val existing = YemenDataProvider.cityDistances.firstOrNull {
            (it.fromCity.contains(fromCity) && it.toCity.contains(toCity)) ||
            (it.fromCity.contains(toCity) && it.toCity.contains(fromCity))
        }

        if (existing != null) {
            existing
        } else if (fromCity == toCity) {
            CityDistance(
                fromCity = fromCity,
                toCity = toCity,
                distanceKm = 0,
                drivingHours = 0.0,
                roadCondition = "داخل نفس المدينة",
                routeDescription = "تنقل محلي داخل نطاق المدينة وضواحيها",
                waypoints = emptyList()
            )
        } else {
            // Geographic Haversine estimate + road tortuosity factor (~1.3x for Yemeni mountain roads)
            val fromGov = YemenDataProvider.governorates.firstOrNull { it.nameArabic.contains(fromCity) || it.capital.contains(fromCity) }
            val toGov = YemenDataProvider.governorates.firstOrNull { it.nameArabic.contains(toCity) || it.capital.contains(toCity) }

            val straightKm = if (fromGov != null && toGov != null) {
                calculateHaversine(
                    fromGov.center.lat, fromGov.center.lng,
                    toGov.center.lat, toGov.center.lng
                )
            } else 200.0

            val estimatedRoadKm = (straightKm * 1.35).toInt().coerceAtLeast(30)
            val hours = (estimatedRoadKm / 55.0)

            CityDistance(
                fromCity = fromCity,
                toCity = toCity,
                distanceKm = estimatedRoadKm,
                drivingHours = (hours * 10).toInt() / 10.0,
                roadCondition = "طريق رئيسي بين المحافظات عبر التضاريس اليمنية",
                routeDescription = "مسار بري يربط بين $fromCity و $toCity عبر المحاور الرئيسية",
                waypoints = listOf(fromCity, "محطة استراحة وسيطة", toCity)
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Text(
                text = "حاسبة المسافات والطرق بين المدن",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "احسب المسافة بالكيلومتر وزمن السفر المقدر بين أي مدينتين يمنيتين",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            // Selection Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // From City Selector
                    ExposedDropdownMenuBox(
                        expanded = fromExpanded,
                        onExpandedChange = { fromExpanded = !fromExpanded }
                    ) {
                        OutlinedTextField(
                            value = fromCity,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("مدينة الانطلاق (من)") },
                            leadingIcon = {
                                Icon(Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("distance_from_city_input")
                        )
                        ExposedDropdownMenu(
                            expanded = fromExpanded,
                            onDismissRequest = { fromExpanded = false }
                        ) {
                            allCities.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city) },
                                    onClick = {
                                        fromCity = city
                                        fromExpanded = false
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
                                val temp = fromCity
                                fromCity = toCity
                                toCity = temp
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                .size(36.dp)
                                .testTag("swap_cities_btn")
                        ) {
                            Icon(
                                Icons.Default.SwapVert,
                                contentDescription = "تبديل المدينتين",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // To City Selector
                    ExposedDropdownMenuBox(
                        expanded = toExpanded,
                        onExpandedChange = { toExpanded = !toExpanded }
                    ) {
                        OutlinedTextField(
                            value = toCity,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("مدينة الوصول (إلى)") },
                            leadingIcon = {
                                Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFFEF4444))
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("distance_to_city_input")
                        )
                        ExposedDropdownMenu(
                            expanded = toExpanded,
                            onDismissRequest = { toExpanded = false }
                        ) {
                            allCities.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city) },
                                    onClick = {
                                        toCity = city
                                        toExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            // Calculation Result Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("distance_result_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "تفاصيل المسار: $fromCity ⟵⟶ $toCity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("المسافة", style = MaterialTheme.typography.labelSmall)
                                }
                                Text(
                                    text = "${calculatedRoute.distanceKm} كم",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.AccessTime,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("الوقت التقديري", style = MaterialTheme.typography.labelSmall)
                                }
                                Text(
                                    text = "${calculatedRoute.drivingHours} ساعة",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Road details
                    Text(
                        text = "طبيعة الطريق: ${calculatedRoute.roadCondition}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "المسار: ${calculatedRoute.routeDescription}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (calculatedRoute.waypoints.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "أهم المحطات: ${calculatedRoute.waypoints.joinToString(" ⟵ ")}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Draw on Map Button
                    Button(
                        onClick = { onShowRouteOnMap(calculatedRoute) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("draw_route_on_map_btn")
                    ) {
                        Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("رسم وعرض المسار على الخريطة")
                    }
                }
            }
        }

        item {
            Text(
                text = "المسارات الأكثر طلباً بين المحافظات",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(YemenDataProvider.cityDistances) { route ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        fromCity = route.fromCity
                        toCity = route.toCity
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${route.fromCity} ⟵⟶ ${route.toCity}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${route.distanceKm} كم • حوالي ${route.drivingHours} ساعة",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { onShowRouteOnMap(route) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("عرض المسار", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

private fun calculateHaversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371.0 // Earth radius in km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return r * c
}
