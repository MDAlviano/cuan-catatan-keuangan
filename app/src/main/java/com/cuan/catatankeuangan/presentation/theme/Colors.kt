package com.cuan.catatankeuangan.presentation.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer

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

// Custom modifier
fun Modifier.grayScale(): Modifier {

    return graphicsLayer {
        scaleX = 1f
        scaleY = 1f
        alpha = 0.5f
    }
        .drawWithCache {
            onDrawWithContent {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    paint.filterQuality = FilterQuality.High

                    canvas.saveLayer(Rect(Offset.Zero, size), paint)
                    drawContent()
                    canvas.restore()
                }
            }
        }
}

