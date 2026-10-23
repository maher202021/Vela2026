package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.YemenDataProvider
import com.example.model.CityDistance
import com.example.model.Governorate
import com.example.model.Landmark
import com.example.model.MapFilterState
import com.example.ui.components.GovernorateDetailBottomSheet
import com.example.ui.components.LandmarkDetailBottomSheet
import com.example.ui.map.YemenCanvasMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    selectedGovernorate: Governorate?,
    selectedLandmark: Landmark?,
    highlightedRoute: CityDistance?,
    onSelectGovernorate: (Governorate?) -> Unit,
    onSelectLandmark: (Landmark?) -> Unit,
    onNavigateToDistance: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var filterState by remember { mutableStateOf(MapFilterState()) }
    var showLayersMenu by remember { mutableStateOf(false) }
    var showFullDetailSheet by remember { mutableStateOf(false) }
    var selectedRegionFilter by remember { mutableStateOf("الكل") }

    val regions = listOf("الكل", "إقليم آزال", "إقليم سبأ", "إقليم الجند", "إقليم تهامة", "إقليم حضرموت", "إقليم عدن")

    val searchResults = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else YemenDataProvider.searchGovernorates(searchQuery)
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Main Interactive Canvas Map
        YemenCanvasMap(
            selectedGovernorate = selectedGovernorate,
            selectedLandmark = selectedLandmark,
            highlightedRoute = highlightedRoute,
            filterState = filterState,
            onSelectGovernorate = { gov ->
                onSelectGovernorate(gov)
                onSelectLandmark(null)
            },
            onSelectLandmark = { landmark ->
                onSelectLandmark(landmark)
            },
            onClearSelection = {
                onSelectGovernorate(null)
                onSelectLandmark(null)
            }
        )

        // Top Control Bar & Quick Region Filter
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Header Search / Action Row
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSearchActive) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("ابحث عن محافظة، مدينة أو معلم...") },
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        searchQuery = ""
                                        isSearchActive = false
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "إغلاق البحث")
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("map_search_input")
                            )
                        } else {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { isSearchActive = true }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "خريطة الجمهورية اليمنية",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "اضغط للبحث عن 22 محافظة ومعالمها",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Layer Filter Toggle
                        Box {
                            IconButton(
                                onClick = { showLayersMenu = !showLayersMenu },
                                modifier = Modifier.testTag("map_layers_btn")
                            ) {
                                Icon(
                                    Icons.Default.Layers,
                                    contentDescription = "طبقات الخريطة",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            DropdownMenu(
                                expanded = showLayersMenu,
                                onDismissRequest = { showLayersMenu = false }
                            ) {
                                Text(
                                    text = "طبقات وعناصر الخريطة",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                                DropdownMenuItem(
                                    text = { Text("حدود المحافظات") },
                                    trailingIcon = {
                                        Checkbox(
                                            checked = filterState.showBorders,
                                            onCheckedChange = {
                                                filterState = filterState.copy(showBorders = it)
                                            }
                                        )
                                    },
                                    onClick = {
                                        filterState = filterState.copy(showBorders = !filterState.showBorders)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("أسماء المحافظات") },
                                    trailingIcon = {
                                        Checkbox(
                                            checked = filterState.showGovernorateNames,
                                            onCheckedChange = {
                                                filterState = filterState.copy(showGovernorateNames = it)
                                            }
                                        )
                                    },
                                    onClick = {
                                        filterState = filterState.copy(showGovernorateNames = !filterState.showGovernorateNames)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("المراكز والمدن") },
                                    trailingIcon = {
                                        Checkbox(
                                            checked = filterState.showCities,
                                            onCheckedChange = {
                                                filterState = filterState.copy(showCities = it)
                                            }
                                        )
                                    },
                                    onClick = {
                                        filterState = filterState.copy(showCities = !filterState.showCities)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("المعالم والآثار") },
                                    trailingIcon = {
                                        Checkbox(
                                            checked = filterState.showLandmarks,
                                            onCheckedChange = {
                                                filterState = filterState.copy(showLandmarks = it)
                                            }
                                        )
                                    },
                                    onClick = {
                                        filterState = filterState.copy(showLandmarks = !filterState.showLandmarks)
                                    }
                                )
                            }
                        }
                    }

                    // Live Search Dropdown Suggestions
                    if (isSearchActive && searchResults.isNotEmpty()) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            searchResults.take(4).forEach { gov ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSelectGovernorate(gov)
                                            isSearchActive = false
                                            searchQuery = ""
                                        }
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(gov.nameArabic, fontWeight = FontWeight.Bold)
                                        Text("عاصمتها: ${gov.capital}", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Region Filter Horizontal Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                regions.forEach { region ->
                    val isSelected = selectedRegionFilter == region
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedRegionFilter = region
                            if (region != "الكل") {
                                val firstGovInRegion = YemenDataProvider.governorates.firstOrNull { it.region == region }
                                if (firstGovInRegion != null) {
                                    onSelectGovernorate(firstGovInRegion)
                                }
                            } else {
                                onSelectGovernorate(null)
                            }
                        },
                        label = {
                            Text(
                                text = region,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        // Bottom Selected Governorate Summary Card
        AnimatedVisibility(
            visible = selectedGovernorate != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            selectedGovernorate?.let { gov ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(16.dp))
                        .testTag("selected_gov_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = gov.nameArabic,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "المركز: ${gov.capital} • ${gov.nickname}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { onSelectGovernorate(null) }) {
                                Icon(Icons.Default.Close, contentDescription = "إغلاق")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = gov.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { showFullDetailSheet = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("gov_details_btn")
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("تفاصيل المحافظة الكاملة")
                            }
                        }
                    }
                }
            }
        }
    }

    // Governorate In-depth Profile Bottom Sheet
    if (showFullDetailSheet && selectedGovernorate != null) {
        GovernorateDetailBottomSheet(
            governorate = selectedGovernorate,
            onDismiss = { showFullDetailSheet = false },
            onNavigateToDistance = { govName ->
                onNavigateToDistance(govName)
            }
        )
    }

    // Landmark In-depth Profile Bottom Sheet
    selectedLandmark?.let { landmark ->
        LandmarkDetailBottomSheet(
            landmark = landmark,
            onDismiss = { onSelectLandmark(null) }
        )
    }
}
