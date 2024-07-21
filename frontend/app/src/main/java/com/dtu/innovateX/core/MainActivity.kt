package com.dtu.innovateX.core

import DualLinePlot
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dtu.innovateX.bluetooth.BluetoothManager
import com.dtu.innovateX.core.theme.InnovateXDTUTheme
import com.dtu.innovateX.startScanning
import dagger.hilt.android.AndroidEntryPoint


private var PERMISSIONS_STORAGE = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_CONNECT,
    Manifest.permission.BLUETOOTH_PRIVILEGED
)
private var PERMISSIONS_LOCATION = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_CONNECT,
    Manifest.permission.BLUETOOTH_PRIVILEGED
)


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions(this)
//        setContent {
//            InnovateXDTUTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val navController = rememberNavController()
//                    AppNavHost(navController)
//                }
//            }
//        }
        setContent {
            MaterialTheme() {
                // Your app content
                DualLinePlot()
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner

    var devices by remember { mutableStateOf(listOf<BluetoothDevice>()) }
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.BLUETOOTH_SCAN] == true &&
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            startScanning(bluetoothLeScanner) { scannedDevices ->
                devices = scannedDevices
            }
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                startScanning(bluetoothLeScanner) { scannedDevices ->
                    devices = scannedDevices
                }
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

    NavHost(navController = navController, startDestination = "ygraph") {
        composable("home") {
            BluetoothScreen(
                onSendDataClicked = { navController.navigate("sendData") },
                onReceiveDataClicked = { navController.navigate("receiveData") }
            )
        }
        composable("sendData") {
            SendDataScreen(onBackClicked = { navController.popBackStack() })
        }
        composable("receiveData") {
            ReceiveDataScreen(onBackClicked = { navController.popBackStack() })
        }
        composable("ygraph") {
            DualLinePlot()
        }
    }
}

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
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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

@Composable
fun SendDataScreen(onBackClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Send Data Screen", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Add UI elements for sending data here

        Button(onClick = onBackClicked) {
            Text("Back")
        }
    }
}

@Composable
fun ReceiveDataScreen(onBackClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Receive Data Screen", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Add UI elements for receiving data here

        Button(onClick = onBackClicked) {
            Text("Back")
        }
    }
}
private fun checkPermissions(mainActivity: MainActivity) {
    val permission1 =
        ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val permission2 = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.BLUETOOTH_SCAN)
    if (permission1 != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
            mainActivity,
            PERMISSIONS_STORAGE,
            1
        )
    } else if (permission2 != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            mainActivity,
            PERMISSIONS_LOCATION,
            1
        )
    }
}