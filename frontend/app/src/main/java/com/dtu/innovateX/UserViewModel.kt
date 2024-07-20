package com.dtu.innovateX

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dtu.innovateX.api.remote.ApiService
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.dtu.innovateX.api.models.DeviceRequest
import com.dtu.innovateX.api.models.DeviceResponse
import com.dtu.innovateX.api.models.UserPostRequest
import com.dtu.innovateX.api.models.UserResponse
import com.dtu.innovateX.core.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<UserResponse>>(Resource.Idle)
    val userState: StateFlow<Resource<UserResponse>> = _userState.asStateFlow()

    private val _devicesState = MutableStateFlow<Resource<List<DeviceResponse>>>(Resource.Idle)
    val devicesState: StateFlow<Resource<List<DeviceResponse>>> = _devicesState.asStateFlow()

    private val _deviceState = MutableStateFlow<Resource<DeviceResponse>>(Resource.Idle)
    val deviceState: StateFlow<Resource<DeviceResponse>> = _deviceState.asStateFlow()

    private val _deleteState = MutableStateFlow<Resource<Map<String, String>>>(Resource.Idle)
    val deleteState: StateFlow<Resource<Map<String, String>>> = _deleteState.asStateFlow()

    fun createUser(userPostRequest: UserPostRequest) {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            try {
                val response = apiService.createUser(userPostRequest)
                _userState.value = handleResponse(response)
            } catch (e: Exception) {
                _userState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun addDeviceToDB(userEmail: String, deviceRequest: DeviceRequest) {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            try {
                val response = apiService.addDeviceToDB(userEmail, deviceRequest)
                _userState.value = handleResponse(response)
            } catch (e: Exception) {
                _userState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getUser(userEmail: String) {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            try {
                val response = apiService.getUser(userEmail)
                _userState.value = handleResponse(response)
            } catch (e: Exception) {
                _userState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getDevices(userEmail: String) {
        viewModelScope.launch {
            _devicesState.value = Resource.Loading
            try {
                val response = apiService.getDevices(userEmail)
                _devicesState.value = handleResponse(response)
            } catch (e: Exception) {
                _devicesState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun updateDevice(userEmail: String, deviceRequest: DeviceRequest) {
        viewModelScope.launch {
            _deviceState.value = Resource.Loading
            try {
                val response = apiService.updateDevice(userEmail, deviceRequest)
                _deviceState.value = handleResponse(response)
            } catch (e: Exception) {
                _deviceState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun deleteDevice(userEmail: String, deviceName: String) {
        viewModelScope.launch {
            _deleteState.value = Resource.Loading
            try {
                val response = apiService.deleteDevice(userEmail, deviceName)
                _deleteState.value = handleResponse(response)
            } catch (e: Exception) {
                _deleteState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun deleteUser(userEmail: String) {
        viewModelScope.launch {
            _deleteState.value = Resource.Loading
            try {
                val response = apiService.deleteUser(userEmail)
                _deleteState.value = handleResponse(response)
            } catch (e: Exception) {
                _deleteState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            Log.d("TAG", response.body().toString())
            Resource.Success(response.body()!!)
        } else {
            Log.d("error", response.message())
            Resource.Error(response.message())
        }
    }
}

