package com.dtu.innovateX.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dtu.innovateX.UserScreen
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.auth.presentation.register.SignUpScreen

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
            SignUpScreen(
                navigateToDetailsScreen = {
                    navController.navigate(Screens.HomeScreen)
                },
                navigateToLogInScreen = {

                },
                context = context
            )
        }
        composable<Screens.HomeScreen> {
            UserScreen()
        }
    }
}