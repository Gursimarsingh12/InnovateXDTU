package com.dtu.innovateX

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.api.models.UserPostRequest
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.core.models.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val devicesState by viewModel.devicesState.collectAsState()
    val deviceState by viewModel.deviceState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = userState) {
            is Resource.Idle -> {
                Text("Idle")
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                Text("User: ${state.data}")
            }

            is Resource.Error -> {
                Text("Error: ${state.message}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.createUser(
                UserPostRequest(
                    email = authViewModel.getCurrentUser()?.email,
                    userDevices = emptyList()
                )
            )
        }) {
            Text("Create User")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.getCurrentUser()?.email?.let { viewModel.getDevices(it) }
        }) {
            Text("Get Devices")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = devicesState) {
            is Resource.Idle -> {
                Text("Idle")
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                state.data?.forEach { device ->
                    Text("Device: ${device.deviceName}")
                }
            }

            is Resource.Error -> {
                Text("Error: ${state.message}")
            }
        }
    }
}
