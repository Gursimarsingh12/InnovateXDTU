package com.dtu.innovateX.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dtu.innovateX.HomeScreen
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.auth.presentation.register.SignUpScreen

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = hiltViewModel()
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
            SignUpScreen(
                navigateToDetailsScreen = {
                    navController.navigate(Screens.HomeScreen)
                },
                navigateToLogInScreen = {

                }
            )
        }
        composable<Screens.HomeScreen> {
            HomeScreen()
        }
    }
}