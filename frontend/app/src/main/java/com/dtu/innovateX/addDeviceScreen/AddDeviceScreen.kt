package com.dtu.innovateX.addDeviceScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.UserViewModel
import com.dtu.innovateX.api.models.DeviceRequest
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.bluetooth.presentation.BluetoothViewModel
import com.dtu.innovateX.core.components.DropDownMenuComponent
import com.dtu.innovateX.core.components.TextComponent
import com.dtu.innovateX.core.components.TextFieldComponent
import com.dtu.innovateX.core.models.Resource

@Composable
fun AddDeviceScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    deviceName: String
) {
    var deviceType by remember { mutableStateOf("") }
    var deviceModel by remember { mutableStateOf("") }
    var deviceLocation by remember { mutableStateOf("") }
    var deviceBuilding by remember { mutableStateOf("") }
    var deviceFloor by remember { mutableStateOf("") }
    var deviceRoom by remember { mutableStateOf("") }
    var deviceHourlyConsumption by remember { mutableStateOf("") }
    var deviceUsageTime by remember { mutableStateOf("") }

    val userState by userViewModel.userState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ){
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    TextComponent(text = "Add Device")
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextFieldComponent(
                    text = deviceName,
                    isError = deviceName.isEmpty(),
                    errorText = "Device Name is required",
                    hintText = "Device Name",
                    trailingIcon = null,
                    onValueChange = {

                    }
                )
                TextFieldComponent(
                    text = deviceType,
                    onValueChange = {
                        deviceType = it
                    },
                    hintText = "Enter Device Type",
                    errorText = "Device Type is required",
                    trailingIcon = null,
                    isError = deviceType.isEmpty()
                )
                TextFieldComponent(
                    text = deviceModel,
                    onValueChange = { deviceModel = it },
                    isError = deviceModel.isEmpty(),
                    errorText = "Device Model is required",
                    hintText = "Device Model",
                    trailingIcon = null,
                )
                TextFieldComponent(
                    text = deviceLocation,
                    onValueChange = { deviceLocation = it },
                    isError = deviceLocation.isEmpty(),
                    errorText = "Device Location is required",
                    hintText = "Device Location",
                    trailingIcon = null,
                )
                TextFieldComponent(
                    text = deviceBuilding,
                    onValueChange = { deviceBuilding = it },
                    isError = deviceBuilding.isEmpty(),
                    errorText = "Device Building is required",
                    hintText = "Device Building",
                    trailingIcon = null,
                )
                TextFieldComponent(
                    text = deviceFloor,
                    onValueChange = { deviceFloor = it },
                    isError = deviceFloor.isEmpty(),
                    errorText = "Device Floor is required",
                    hintText = "Device Floor",
                    trailingIcon = null
                )
                TextFieldComponent(
                    text = deviceRoom,
                    onValueChange = { deviceRoom = it },
                    isError = deviceRoom.isEmpty(),
                    errorText = "Device Room is required",
                    hintText = "Device Room",
                    trailingIcon = null
                )
                TextFieldComponent(
                    text = deviceHourlyConsumption,
                    onValueChange = { deviceHourlyConsumption = it },
                    isError = deviceHourlyConsumption.isEmpty(),
                    errorText = "Device Hourly Consumption is required",
                    hintText = "Device Hourly Consumption",
                    trailingIcon = null
                )
                TextFieldComponent(
                    text = deviceUsageTime,
                    onValueChange = { deviceUsageTime = it },
                    isError = deviceUsageTime.isEmpty(),
                    errorText = "Device Usage Time is required",
                    hintText = "Device Usage Time",
                    trailingIcon = null
                )

                Button(
                    onClick = {
                        if (deviceType.isNotEmpty() && deviceBuilding.isNotEmpty() && deviceLocation.isNotEmpty()
                            && deviceFloor.isNotEmpty() && deviceRoom.isNotEmpty() && deviceHourlyConsumption.isNotEmpty()
                        ){
                            val deviceRequest = DeviceRequest(
                                deviceName = deviceName,
                                deviceType = deviceType,
                                deviceModel = deviceModel,
                                deviceLocation = deviceLocation,
                                deviceBuilding = deviceBuilding,
                                deviceFloor = deviceFloor.toIntOrNull(),
                                deviceRoom = deviceRoom,
                                deviceHourlyConsumption = deviceHourlyConsumption.toIntOrNull(),
                                deviceUsageTime = deviceUsageTime.toIntOrNull()
                            )
                            userViewModel.addDeviceToDB(userEmail = authViewModel.getCurrentUser()?.email!!, deviceRequest = deviceRequest)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(text = "Add Device")
                }

            }
            if(userState is Resource.Loading){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }
    }
    LaunchedEffect(userState) {
        if(userState is Resource.Success){
            snackbarHostState.showSnackbar("Device added successfully", actionLabel = "Dismiss")
        } else if(userState is Resource.Error){
            (userState as Resource.Error).message?.let { snackbarHostState.showSnackbar(message = it) }
        }
    }
}