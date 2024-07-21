package com.dtu.innovateX.bluetooth.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.bluetooth.presentation.BluetoothViewModel
import com.dtu.innovateX.core.components.BluetoothList
import com.dtu.innovateX.core.models.Resource
import com.dtu.innovateX.core.theme.lightBlue

@Composable
fun BluetoothAddScreen(
    bluetoothViewModel: BluetoothViewModel = hiltViewModel(),
    navigateToAddDeviceScreen: (String) -> Unit
) {
    val connectionStatus by bluetoothViewModel.connectionStatus.collectAsState()
    val discoveredDevices by bluetoothViewModel.discoveredDevices.collectAsState()
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            bluetoothViewModel.startDiscovery()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            bluetoothViewModel.startDiscovery()
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(310.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.55f),
                        disabledContainerColor = Color.White.copy(alpha = 0.55f),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Bluetooth, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Bluetooth", style = MaterialTheme.typography.displaySmall)
                            Spacer(modifier = Modifier.height(29.dp))
                        }
                        LazyColumn {
                            if (discoveredDevices.isNotEmpty()) {
                                items(discoveredDevices.size) { index ->
                                    val device = discoveredDevices[index]
                                    BluetoothList(
                                        deviceName = device.name ?: "Unknown Device",
                                        onConnectClick = {
                                            try {
                                                bluetoothViewModel.connectToDevice(device)
                                                navigateToAddDeviceScreen(device.name ?: "Unknown Device")
                                            } catch (e: SecurityException) {
                                                e.printStackTrace()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                if (connectionStatus is Resource.Loading){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    LaunchedEffect(connectionStatus) {
        when (connectionStatus) {
            is Resource.Success -> {
                snackbarHostState.showSnackbar(message = (connectionStatus as Resource.Success<String>).data!!)
            }
            is Resource.Error -> {
                val errorMessage = (connectionStatus as Resource.Error).message
                errorMessage?.let { snackbarHostState.showSnackbar(it) }
            }
            else -> {}
        }
    }
}

