package com.dtu.innovateX.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dtu.innovateX.AddDeviceScreen
import com.dtu.innovateX.UserScreen
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.auth.presentation.register.SignUpScreen
import com.dtu.innovateX.bluetooth.presentation.screens.BluetoothAddScreen

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = hiltViewModel(),
    context: Context
) {
    val navController = rememberNavController()
    val startDestination = if(authViewModel.getCurrentUser() != null){
        Screens.HomeScreen
    }else{
        Screens.SignUpScreen
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.SignUpScreen> {
            SignUpScreen(context = context) {
                navController.navigate(Screens.HomeScreen){
                    popUpTo(Screens.HomeScreen){
                        inclusive = true
                    }
                }
            }
        }
        composable<Screens.HomeScreen> {
            UserScreen(
                navigateToBluetoothAddScreen = {
                    navController.navigate(Screens.BluetoothScreen)
                }
            )
        }
        composable<Screens.BluetoothScreen> {
            BluetoothAddScreen {
                navController.navigate(Screens.AddDeviceScreen(it))
            }
        }
        composable<Screens.AddDeviceScreen> {
            val deviceName = it.toRoute<String>()
            AddDeviceScreen(deviceName = deviceName)
        }
    }
}