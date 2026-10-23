package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import com.example.model.AppTab
import com.example.model.CityDistance
import com.example.model.Governorate
import com.example.model.Landmark
import com.example.ui.screens.DistanceCalculatorScreen
import com.example.ui.screens.GovernoratesScreen
import com.example.ui.screens.LandmarksScreen
import com.example.ui.screens.MapScreen
import com.example.ui.screens.OfflineNavigationScreen
import com.example.ui.screens.QuizScreen

@Composable
fun YemenMapApp(modifier: Modifier = Modifier) {
    // Force RTL direction for authentic Arabic user experience
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        var currentTab by remember { mutableStateOf(AppTab.MAP) }

        var selectedGovernorateOnMap by remember { mutableStateOf<Governorate?>(null) }
        var selectedLandmarkOnMap by remember { mutableStateOf<Landmark?>(null) }
        var highlightedRouteOnMap by remember { mutableStateOf<CityDistance?>(null) }
        var distancePreselectedCity by remember { mutableStateOf<String?>(null) }

        // BackHandler: return to Map tab if user presses back while in secondary tab
        BackHandler(enabled = currentTab != AppTab.MAP) {
            currentTab = AppTab.MAP
        }

        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.testTag("app_bottom_bar")
                ) {
                    AppTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        val icon = when (tab) {
                            AppTab.MAP -> Icons.Default.Map
                            AppTab.NAVIGATION -> Icons.Default.Explore
                            AppTab.GOVERNORATES -> Icons.Default.LocationCity
                            AppTab.DISTANCES -> Icons.Default.AltRoute
                            AppTab.LANDMARKS -> Icons.Default.Landscape
                            AppTab.QUIZ -> Icons.Default.Quiz
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                currentTab = tab
                            },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = tab.iconDescription
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (currentTab) {
                AppTab.MAP -> {
                    MapScreen(
                        selectedGovernorate = selectedGovernorateOnMap,
                        selectedLandmark = selectedLandmarkOnMap,
                        highlightedRoute = highlightedRouteOnMap,
                        onSelectGovernorate = { gov ->
                            selectedGovernorateOnMap = gov
                            if (gov != null) highlightedRouteOnMap = null
                        },
                        onSelectLandmark = { landmark ->
                            selectedLandmarkOnMap = landmark
                        },
                        onNavigateToDistance = { city ->
                            distancePreselectedCity = city
                            currentTab = AppTab.DISTANCES
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppTab.NAVIGATION -> {
                    OfflineNavigationScreen(
                        onShowRouteOnMap = { route ->
                            highlightedRouteOnMap = route
                            selectedGovernorateOnMap = null
                            selectedLandmarkOnMap = null
                            currentTab = AppTab.MAP
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppTab.GOVERNORATES -> {
                    GovernoratesScreen(
                        onShowOnMap = { gov ->
                            selectedGovernorateOnMap = gov
                            selectedLandmarkOnMap = null
                            highlightedRouteOnMap = null
                            currentTab = AppTab.MAP
                        },
                        onNavigateToDistance = { city ->
                            distancePreselectedCity = city
                            currentTab = AppTab.DISTANCES
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppTab.DISTANCES -> {
                    DistanceCalculatorScreen(
                        initialCity = distancePreselectedCity,
                        onShowRouteOnMap = { route ->
                            highlightedRouteOnMap = route
                            selectedGovernorateOnMap = null
                            selectedLandmarkOnMap = null
                            currentTab = AppTab.MAP
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppTab.LANDMARKS -> {
                    LandmarksScreen(
                        onShowOnMap = { landmark ->
                            selectedLandmarkOnMap = landmark
                            selectedGovernorateOnMap = null
                            highlightedRouteOnMap = null
                            currentTab = AppTab.MAP
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppTab.QUIZ -> {
                    QuizScreen(
                        onNavigateToMap = {
                            currentTab = AppTab.MAP
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
