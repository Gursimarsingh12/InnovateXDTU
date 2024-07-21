package com.dtu.innovateX.auth.presentation.register

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.R
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.auth.presentation.components.LottieAnimation
import com.dtu.innovateX.core.models.Resource

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    context: Context,
    navigateToHomeScreen: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LottieAnimation(R.raw.login_animation)
                    Spacer(modifier = Modifier.padding(32.dp))
                    GoogleSignInCard{ authViewModel.googleSignIn(context) }
                }
            }
            if (authState.state is Resource.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    LaunchedEffect(authState) {
        if (authState.state is Resource.Success) {
            navigateToHomeScreen()
        } else if (authState.state is Resource.Error) {
            (authState.state as Resource.Error).message?.let {
                snackbarHostState.showSnackbar(
                    message = it
                )
            }
        }
    }
}

@Composable
fun GoogleSignInCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = R.drawable.ic_goole),
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Continue With Google",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
