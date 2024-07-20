package com.dtu.innovateX.bluetooth.data.repository

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.dtu.innovateX.core.models.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.*
import javax.inject.Inject

class BluetoothRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter
) {

    private val _discoveredDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val discoveredDevices = _discoveredDevices.asStateFlow()

    private val discoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        _discoveredDevices.value += device
                    }
                }
            }
        }
    }

    init {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(discoveryReceiver, filter)
    }

    suspend fun startDiscovery(): Flow<Resource<Unit>> = flow {
        if (hasBluetoothPermissions()) {
            emit(Resource.Loading)
            try {
                bluetoothAdapter.startDiscovery()
                emit(Resource.Success(Unit))
            } catch (e: SecurityException) {
                emit(Resource.Error(e.localizedMessage))
            }
        }
    }
    suspend fun connectToDevice(device: BluetoothDevice): Flow<Resource<Unit>> = flow {
        if (hasBluetoothPermissions()) {
            emit(Resource.Loading)
            try {
                val uuid: UUID = device.uuids?.firstOrNull()?.uuid
                    ?: UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

                val socket: BluetoothSocket? = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothAdapter.cancelDiscovery()
                socket?.connect()
                emit(Resource.Success(Unit))
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage))
            } catch (e: SecurityException) {
                emit(Resource.Error(e.localizedMessage))
            }
        }
    }

    private fun hasBluetoothPermissions(): Boolean {
        val scanPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
        val fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return scanPermission == PackageManager.PERMISSION_GRANTED && fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun clear() {
        context.unregisterReceiver(discoveryReceiver)
    }
}
