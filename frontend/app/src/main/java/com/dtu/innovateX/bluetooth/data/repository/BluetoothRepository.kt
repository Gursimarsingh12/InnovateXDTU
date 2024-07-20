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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
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
        } else {
            emit(Resource.Error("Missing Bluetooth permissions"))
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

                socket?.let {
                    try {
                        it.connect()
                        manageConnectedSocket(it)
                        emit(Resource.Success(Unit))
                    } catch (e: IOException) {
                        emit(Resource.Error("Connection failed: ${e.localizedMessage}"))
                        closeSocket(it)
                    } catch (e: SecurityException) {
                        emit(Resource.Error("Security exception: ${e.localizedMessage}"))
                        closeSocket(it)
                    }
                } ?: run {
                    emit(Resource.Error("Unable to create socket"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Connection setup failed: ${e.localizedMessage}"))
            }catch (e: SecurityException){
                emit(Resource.Error("Security exception: ${e.localizedMessage}"))
            }
        } else {
            emit(Resource.Error("Missing Bluetooth permissions"))
        }
    }

    private suspend fun manageConnectedSocket(socket: BluetoothSocket) {
        val inputStream = socket.inputStream
        val outputStream = socket.outputStream

        // Read and write operations should be handled in separate threads or coroutines
        withContext(Dispatchers.IO) {
            try {
                val buffer = ByteArray(1024)
                var bytes: Int

                while (true) {
                    bytes = inputStream.read(buffer)
                    if (bytes > 0) {
                        val receivedData = String(buffer, 0, bytes)
                        // Handle received data (e.g., update state, notify view model)
                    }
                }
            } catch (e: IOException) {
                // Handle read error
            } finally {
                closeSocket(socket)
            }
        }
    }

    private fun closeSocket(socket: BluetoothSocket) {
        try {
            socket.close()
        } catch (e: IOException) {
            // Handle socket close error
        }
    }

    private fun hasBluetoothPermissions(): Boolean {
        val scanPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
        val fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val connectPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
        return scanPermission == PackageManager.PERMISSION_GRANTED &&
                fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                connectPermission == PackageManager.PERMISSION_GRANTED
    }

    fun clear() {
        context.unregisterReceiver(discoveryReceiver)
    }
}

