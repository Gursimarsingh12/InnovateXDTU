package com.dtu.innovateX.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dtu.innovateX.R
import com.dtu.innovateX.core.theme.greyColor
import com.dtu.innovateX.core.theme.lightestBlue
import com.dtu.innovateX.core.theme.midBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    name: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                LinearProgressIndicator(
                    progress = {
                        0.5f
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(15.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    trackColor = greyColor,
                    strokeCap = StrokeCap.Round,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "50%",
                    fontSize = 11.sp
                )
            }
        }
        IconsRow()
    }
}

@Composable
fun IconsRow() {
    Row {
        IconBox(icon = Icons.Filled.SettingsVoice)
        Spacer(modifier = Modifier.width(13.dp))
        IconBox(icon = Icons.Filled.Person)
    }
}

@Composable
fun IconBox(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .border(
                border = BorderStroke(1.dp, Color.Black),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTopAppBar() {
    CustomTopAppBar("")
}
