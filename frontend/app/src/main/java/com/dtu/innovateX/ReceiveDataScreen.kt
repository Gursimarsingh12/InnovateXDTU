package com.dtu.innovateX

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dtu.innovateX.bluetooth.BluetoothManager

@Composable
fun ReceiveDataScreen() {
    val bluetoothManager: BluetoothManager = viewModel()
    val connectionStatus by bluetoothManager.connectionStatus.collectAsState("")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Receive Data", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(connectionStatus)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { bluetoothManager.startDiscovery() }) {
            Text("Discover Devices")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val devices by bluetoothManager.discoveredDevices.collectAsState()
        devices.forEach { device ->
            Button(onClick = { bluetoothManager.connectToDevice(device) }) {
                if (ActivityCompat.checkSelfPermission(
                        LocalContext.current,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Text(device.name ?: "Unknown Device")
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        val receivedMessages = remember { mutableStateListOf<String>() }
        val receivedData by bluetoothManager.connectionStatus.collectAsState("")

        LaunchedEffect(receivedData) {
            if (receivedData.isNotEmpty()) {
                receivedMessages.add(receivedData)
            }
        }

        if (receivedMessages.isNotEmpty()) {
            Text("Received Messages:")
            receivedMessages.forEach { message ->
                Text(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
