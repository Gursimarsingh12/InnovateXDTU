package com.dtu.innovateX

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dtu.innovateX.bluetooth.BluetoothManager

@Composable
fun BluetoothScreen(
    onSendDataClicked: () -> Unit,
    onReceiveDataClicked: () -> Unit
) {
    val context = LocalContext.current
    val bluetoothManager: BluetoothManager = viewModel()
    val connectionStatus by bluetoothManager.connectionStatus.collectAsState("")
    val devices by bluetoothManager.discoveredDevices.collectAsState()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.BLUETOOTH_SCAN] == true &&
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            bluetoothManager.startDiscovery()
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                bluetoothManager.startDiscovery()
            }
            else -> {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bluetooth App", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            try {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    bluetoothManager.startDiscovery()
                } else {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }) {
            Text("Discover Devices")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (devices.isNotEmpty()) {
            Text("Select a device to connect")
            devices.forEach { device ->
                Button(onClick = {
                    try {
                        bluetoothManager.connectToDevice(device)
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }) {
                    Text(device.name ?: "Unknown Device")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(connectionStatus)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSendDataClicked) {
            Text("Send Data")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onReceiveDataClicked) {
            Text("Receive Data")
        }
    }
}
