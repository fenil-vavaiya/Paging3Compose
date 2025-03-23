package com.example.paging3compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.paging3compose.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
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

val Jost = FontFamily(
    Font(R.font.jost_bold, FontWeight.Bold),
    Font(R.font.jost_semibold, FontWeight.SemiBold),
    Font(R.font.jost_medium, FontWeight.Medium),
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_light, FontWeight.Light),
)

val Frastha = FontFamily(
    Font(R.font.frastha_bold, FontWeight.Bold),
    Font(R.font.frastha_semibold, FontWeight.SemiBold),
    Font(R.font.frastha_medium, FontWeight.Medium),
    Font(R.font.frastha_regular, FontWeight.Normal),
    Font(R.font.frastha_light, FontWeight.Light),
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Jost, fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Jost,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
)