package com.dtu.innovateX.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dtu.innovateX.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.days_one_regular)),
        fontWeight = FontWeight(400),
        fontSize = 20.sp,
        color = Color.Black
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.days_one_regular)),
        fontWeight = FontWeight(400),
        fontSize = 22.sp,
        color = Color.Black
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontWeight = FontWeight(400),
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