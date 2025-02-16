package com.cuan.catatankeuangan.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// primary
val Color1 = Color(0xFF6263CE)
val Color2 = Color(0xFF1D2A56)
val Color3 = Color(0xFF6263CE)
val Color4 = Color(0xFF1D2A56)
val Color5 = Color(0xFFE36161)

// optional
val OptionalColor1 = Color(0xFF565685)
val OptionalColor2 = Color(0xFFC9D9F8)
val OptionalColor3 = Color(0xFF979797)

// gradient
val Color1Color2Vert = Brush.verticalGradient(colors = listOf(Color1, Color2))
val Color2Color1Vert = Brush.verticalGradient(colors = listOf(Color2, Color1))

val Color1Color2Hor = Brush.horizontalGradient(colors = listOf(Color1, Color2))
val Color2Color1Hor = Brush.horizontalGradient(colors = listOf(Color2, Color1))
