package com.dtu.innovateX.bluetooth.presentation

import android.app.Application
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtu.innovateX.bluetooth.data.repository.BluetoothRepository
import com.dtu.innovateX.core.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    application: Application,
    private val repository: BluetoothRepository
) : AndroidViewModel(application) {

    private val _connectionStatus = MutableStateFlow<Resource<String>>(Resource.Loading)
    val connectionStatus: StateFlow<Resource<String>> get() = _connectionStatus

    val discoveredDevices: StateFlow<List<BluetoothDevice>> get() = repository.discoveredDevices

    fun startDiscovery() {
        viewModelScope.launch {
            repository.startDiscovery().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _connectionStatus.value = Resource.Loading
                    }
                    is Resource.Success -> {
                        _connectionStatus.value = Resource.Success("Discovery started")
                    }
                    is Resource.Error -> {
                        _connectionStatus.value = Resource.Error(resource.message)
                    }
                    else -> {

                    }
                }
            }
        }
    }

    fun connectToDevice(device: BluetoothDevice) {
        viewModelScope.launch {
            repository.connectToDevice(device).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _connectionStatus.value = Resource.Loading
                    }
                    is Resource.Success -> {
                        try {
                            _connectionStatus.value = Resource.Success("Connected to ${device.name}")
                        }catch (e: SecurityException){
                            _connectionStatus.value = Resource.Error(e.localizedMessage)
                        }
                    }
                    is Resource.Error -> {
                        _connectionStatus.value = Resource.Error(resource.message)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }
}
