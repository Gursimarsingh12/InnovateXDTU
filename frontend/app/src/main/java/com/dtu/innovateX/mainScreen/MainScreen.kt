package com.dtu.innovateX.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.R
import com.dtu.innovateX.UserViewModel
import com.dtu.innovateX.api.models.UserPostRequest
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.core.components.CustomTopAppBar
import com.dtu.innovateX.core.components.HomeItemCard
import com.dtu.innovateX.core.models.Resource
import com.dtu.innovateX.core.theme.blue
import com.dtu.innovateX.core.theme.lightBlue
import com.dtu.innovateX.core.theme.lightestBlue
import com.dtu.innovateX.core.theme.midBlue

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToBluetoothAddScreen: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userState by userViewModel.userState.collectAsState()
    val devicesState by userViewModel.devicesState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = Unit) {
        userViewModel.createUser(
            UserPostRequest(
                email = authViewModel.getCurrentUser()?.email,
                userDevices = emptyList()
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.gradient),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Scaffold(
            floatingActionButton = {
                Button(
                    onClick = navigateToBluetoothAddScreen,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Add Devices")
                }
            },
            floatingActionButtonPosition = FabPosition.EndOverlay,
        ){ paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                lightestBlue,
                                lightBlue,
                                blue,
                                midBlue
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.gradient),
                            contentScale = ContentScale.FillBounds
                        )
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    authViewModel.getCurrentUser()?.displayName?.let { CustomTopAppBar(it) }
                    Spacer(modifier = Modifier.height(57.dp))
                    HomeItemCard(text = "Living Room")
                    Spacer(modifier = Modifier.height(17.dp))
                    HomeItemCard(text = "Kitchen")
                }
            }
        }
        if (userState is Resource.Loading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
    }
    LaunchedEffect(key1 = userState) {
        if (userState is Resource.Success){
            snackbarHostState.showSnackbar(message = (userState as Resource.Success).data.toString())
        }else if (userState is Resource.Error){
            snackbarHostState.showSnackbar(message = (userState as Resource.Error).message!!)
        }
    }
}