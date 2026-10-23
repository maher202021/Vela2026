package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = YemenBlueLight,
    onPrimary = YemenDarkBg,
    primaryContainer = YemenBlueDark,
    onPrimaryContainer = YemenCyanSoft,
    secondary = YemenOchreLight,
    onSecondary = YemenDarkBg,
    secondaryContainer = YemenOchre,
    onSecondaryContainer = Color.White,
    tertiary = YemenRuby,
    background = YemenDarkBg,
    surface = YemenDarkSurface,
    onBackground = YemenDarkTextPrimary,
    onSurface = YemenDarkTextPrimary,
    surfaceVariant = YemenDarkCard,
    onSurfaceVariant = YemenDarkTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = YemenBluePrimary,
    onPrimary = Color.White,
    primaryContainer = YemenCyanSoft,
    onPrimaryContainer = YemenBlueDark,
    secondary = YemenOchre,
    onSecondary = Color.White,
    secondaryContainer = YemenOchreLight,
    onSecondaryContainer = YemenBlueDark,
    tertiary = YemenAgateRed,
    background = YemenBackgroundLight,
    surface = YemenSurfaceLight,
    onBackground = YemenTextPrimary,
    onSurface = YemenTextPrimary,
    surfaceVariant = YemenSand,
    onSurfaceVariant = YemenTextSecondary
)

@Composable
fun YemenMapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent Yemeni branding colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
