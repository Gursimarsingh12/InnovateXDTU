package com.dtu.innovateX.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuComponent(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String?>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(value = false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {

            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            placeholder = {
                Text(
                    text = label,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            shape = RoundedCornerShape(12.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.fillMaxWidth(),
            scrollState = rememberScrollState(),
            properties = PopupProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                clippingEnabled = true,
                focusable = true,
                excludeFromSystemGesture = true
            )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        if (item != null) {
                            Text(
                                text = item,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                )
                            )
                        }
                    },
                    onClick = {
                        if (item != null) {
                            onItemSelected(item)
                        }
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
