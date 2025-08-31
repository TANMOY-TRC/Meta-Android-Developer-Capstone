package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.littlelemon.R

val Karla = FontFamily(
    Font(R.font.karla, FontWeight.Normal)
)

val MarkazText = FontFamily(
    Font(R.font.markazitext, FontWeight.Normal)
)

// Set of Material typography styles
val Typography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = MarkazText,
        fontWeight = FontWeight.Medium
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = Karla
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold
    )
)
