package com.dtu.innovateX.bluetooth.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.bluetooth.presentation.BluetoothViewModel

@Composable
fun BluetoothApp(viewModel: BluetoothViewModel = hiltViewModel()) {
    val connectionStatus by viewModel.connectionStatus.collectAsState()
    val discoveredDevices by viewModel.discoveredDevices.collectAsState()

    val context = LocalContext.current
    val bluetoothPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            viewModel.startDiscovery()
        } else {

        }
    }

    LaunchedEffect(Unit) {
        bluetoothPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

}