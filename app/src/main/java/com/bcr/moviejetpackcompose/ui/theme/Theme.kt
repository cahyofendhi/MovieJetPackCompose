package com.bcr.moviejetpackcompose.ui.theme

import android.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


private val DarkThemeColors = darkColors(
    primary = primaryBlack,
    primaryVariant = blueYoung,
    onPrimary = secondPrimary,
    secondary = secondPrimary,
    error = secondPrimary,
    onBackground = white
)

@Composable
fun AppTheme(content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkThemeColors,
        typography = appTypography,
        shapes = appShapes,
        content = content
    )
}