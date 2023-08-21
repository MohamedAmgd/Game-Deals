package com.mohamed_amgd.gamedeals.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = ChineseBlack,
    secondary = CharlestonGreen,
    secondaryVariant = ScreamingGreen, // tertiary
    background = CharlestonGreen,
    surface = ChineseBlack70Alpha // onBackground

)

private val LightColorPalette = lightColors(
    primary = ChineseBlack,
    secondary = CharlestonGreen,
    secondaryVariant = ScreamingGreen, // tertiary
    background = CharlestonGreen,
    surface = ChineseBlack70Alpha,// onBackground
)

@Composable
fun GameDealsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.secondary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colors = colors,
        typography = GothamTypography,
        shapes = Shapes,
        content = content
    )
}