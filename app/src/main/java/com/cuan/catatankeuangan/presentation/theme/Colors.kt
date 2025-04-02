package com.cuan.catatankeuangan.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// primary
val Color1 = Color(0xFF6263CE)
val Color2 = Color(0xFF1D2A56)
val Color3 = Color(0xFFE36161)

// optional
val MainBgColor = Color(0xFFF7F7F7)
val OptionalColor1 = Color(0xFF565685)
val OptionalColor2 = Color(0xFFC9D9F8)
val OptionalColor3 = Color(0xFF979797)
val OptionalColor4 = Color(0xFFF9F9F9)

// gradient
fun VerticalGradient(colorTop: Color, colorBottom: Color): Brush {
    return Brush.verticalGradient(colors = listOf(colorTop, colorBottom))
}

fun HorizontalGradient(colorTop: Color, colorBottom: Color): Brush {
    return Brush.horizontalGradient(colors = listOf(colorTop, colorBottom))
}

val Color1Color2Vert = Brush.verticalGradient(colors = listOf(Color1, Color2))
val Color2Color1Vert = Brush.verticalGradient(colors = listOf(Color2, Color1))

val Color1Color2Hor = Brush.horizontalGradient(colors = listOf(Color1, Color2))
val Color2Color1Hor = Brush.horizontalGradient(colors = listOf(Color2, Color1))
