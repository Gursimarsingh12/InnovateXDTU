package com.dtu.innovateX.core.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    hintText: String,
    errorText: String,
    trailingIcon: ImageVector?,
    isError: Boolean
) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = hintText,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ),
            )
        },
        supportingText = {
            Text(
                text = errorText,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ),
            )
        },
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(
                    onClick = {

                    },
                ) {
                    Icon(imageVector = trailingIcon, contentDescription = "")
                }
            }
        },
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(13.dp),
    )
}