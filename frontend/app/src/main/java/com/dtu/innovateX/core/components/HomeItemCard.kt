package com.dtu.innovateX.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.LaptopMac
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dtu.innovateX.core.theme.lightBlue

@Preview
@Composable
fun HomeItemCard(
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {

        },
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .shadow(
                elevation = 4.dp,
                spotColor = lightBlue,
            ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.55f),
            disabledContainerColor = Color.White.copy(alpha = 0.55f),
            contentColor = Color.Black,
            disabledContentColor = Color.Black
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 13.dp, top = 13.dp)
        ) {
            Text(text = "Living Room", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Icon(
                    imageVector = Icons.Filled.Computer,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(18.dp))
                Icon(imageVector = Icons.Filled.LaptopMac, contentDescription = null)
                Spacer(modifier = Modifier.width(18.dp))
                Icon(imageVector = Icons.Filled.LaptopMac, contentDescription = null)
            }
        }
    }
}