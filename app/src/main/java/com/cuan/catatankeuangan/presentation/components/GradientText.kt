package com.cuan.catatankeuangan.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2

@Composable
fun GradientText(
    text: String,
    fontSize: TextUnit = 32.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Center
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color2,
            Color1
        ) // Warna gradient yang sama dengan button
    )

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        onTextLayout = { textLayoutResult = it },
        modifier = Modifier.drawWithContent {
            textLayoutResult?.let { layoutResult ->
                drawText(
                    textLayoutResult = layoutResult,
                    brush = gradientBrush
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun GradientTextPreview() {
    GradientText(
        text = "Selamat Datang Kembali!",
    )
}