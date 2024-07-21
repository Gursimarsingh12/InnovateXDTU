package com.dtu.innovateX.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BluetoothList(
    deviceName: String,
    onConnectClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = deviceName, style = MaterialTheme.typography.headlineMedium)
            Button(
                onClick = onConnectClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Add device",
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            color = Color(0xFFB0B0B0)
        )
    }
}