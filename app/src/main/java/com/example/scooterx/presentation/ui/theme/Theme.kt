package com.example.scooterx.presentation.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = Primary,
    secondary = Secondary,
    surface = Surface,
    onSurface = onSurface
)

private val LightColorPalette = lightColors(
    primary = Primary,
    secondary = Secondary,
    surface = Surface,
    onSurface = onSurface
)

@Composable
fun ScooterXTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
