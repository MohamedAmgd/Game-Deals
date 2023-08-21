package com.mohamed_amgd.gamedeals.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mohamed_amgd.gamedeals.R

private val Gotham = FontFamily(
    Font(R.font.gotham_light, FontWeight.Light),
    Font(R.font.gotham_medium, FontWeight.Medium)
)

val textColor = Color.White

val GothamTypography = Typography(
    h1 = TextStyle( // headlineMedium
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    h3 = TextStyle( // titleMedium
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle( // titleLarge
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Light,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    caption = TextStyle( // bodySmall
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Light,
        fontSize = 8.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle( // labelSmall
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle( // labelLarge
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Light,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle2 = TextStyle( // displaySmall
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle1 = TextStyle( // displayLarge
        color = textColor,
        fontFamily = Gotham,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )
)