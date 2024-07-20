package com.dtu.innovateX.bluetooth

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class BluetoothService(private val socket: BluetoothSocket) : Thread() {
    private val inputStream: InputStream = socket.inputStream
    private val outputStream: OutputStream = socket.outputStream

    override fun run() {
        val buffer = ByteArray(1024)
        var bytes: Int

        while (true) {
            try {
                bytes = inputStream.read(buffer)
                val readMessage = String(buffer, 0, bytes)
                Log.d("BluetoothService", "Received: $readMessage")
            } catch (e: IOException) {
                Log.e("BluetoothService", "Input stream was disconnected", e)
                break
            }
        }
    }

    fun write(bytes: ByteArray) {
        try {
            outputStream.write(bytes)
        } catch (e: IOException) {
            Log.e("BluetoothService", "Error occurred when sending data", e)
        }
    }

    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {
            Log.e("BluetoothService", "Could not close the client socket", e)
        }
    }
}
