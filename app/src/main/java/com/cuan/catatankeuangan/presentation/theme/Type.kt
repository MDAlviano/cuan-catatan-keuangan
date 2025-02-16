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

// Custom TextStyle for

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)