package com.dtu.innovateX.bluetooth

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.util.*

class BluetoothManager(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val _connectionStatus = MutableStateFlow("")
    val connectionStatus: StateFlow<String> get() = _connectionStatus
    private val _discoveredDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val discoveredDevices: StateFlow<List<BluetoothDevice>> get() = _discoveredDevices

    fun startDiscovery() {
        if (hasBluetoothPermissions()) {
            Log.i("Chips", "Permission toh hai bc")
            Toast.makeText(getApplication(), "Permission toh hai bc", Toast.LENGTH_SHORT).show()
            try {
                bluetoothAdapter?.startDiscovery()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    fun connectToDevice(device: BluetoothDevice) {
        if (hasBluetoothPermissions()) {
            try {
                val uuid: UUID = device.uuids[0].uuid
                val socket: BluetoothSocket? = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothAdapter?.cancelDiscovery()
                socket?.connect()
                _connectionStatus.value = "Connected to ${device.name}"
                Toast.makeText(getApplication(), "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                _connectionStatus.value = "Connection failed"
                Toast.makeText(getApplication(), "Connected failed", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                e.printStackTrace()
                _connectionStatus.value = "Connection failed due to permissions"
                Toast.makeText(getApplication(), "Connection failed due to permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasBluetoothPermissions(): Boolean {
        val context = getApplication<Application>().applicationContext
        val scanPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
        val fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return scanPermission == PackageManager.PERMISSION_GRANTED && fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    // Add a receiver to handle discovered devices (not included in your original code)
    private val discoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        _discoveredDevices.value += device
                    }
                }
            }
        }
    }

    init {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        getApplication<Application>().registerReceiver(discoveryReceiver, filter)
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().unregisterReceiver(discoveryReceiver)
    }
}
