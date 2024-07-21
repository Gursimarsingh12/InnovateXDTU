package com.dtu.innovateX.core.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextComponent(
    text: String
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight(400),
            color = Color.Black
        ),
    )
}