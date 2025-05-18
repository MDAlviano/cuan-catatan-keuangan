package com.cuan.catatankeuangan.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R

val ralewayFamily = FontFamily(
    Font(R.font.raleway_extralight, FontWeight.ExtraLight),
    Font(R.font.raleway_light, FontWeight.Light),
    Font(R.font.raleway_regular, FontWeight.Normal),
    Font(R.font.raleway_medium, FontWeight.Medium),
    Font(R.font.raleway_semibold, FontWeight.SemiBold),
    Font(R.font.raleway_bold, FontWeight.Bold),
    Font(R.font.raleway_extrabold, FontWeight.ExtraBold),
    Font(R.font.raleway_black, FontWeight.Black)
)

val outfitFamily = FontFamily(
    Font(R.font.outfit_extralight, FontWeight.ExtraLight),
    Font(R.font.outfit_light, FontWeight.Light),
    Font(R.font.outfit_regular, FontWeight.Normal),
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_semibold, FontWeight.SemiBold),
    Font(R.font.outfit_bold, FontWeight.Bold),
    Font(R.font.outfit_extrabold, FontWeight.ExtraBold),
    Font(R.font.outfit_black, FontWeight.Black)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        color = Color.Black
    ),
    headlineMedium = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = Color.Black
    ),
    bodyLarge = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Black,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = ralewayFamily,
        fontSize = 12.sp
    )
)