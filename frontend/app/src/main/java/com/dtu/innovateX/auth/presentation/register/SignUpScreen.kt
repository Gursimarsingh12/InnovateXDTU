package com.dtu.innovateX.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.auth.presentation.AuthViewModel
import com.dtu.innovateX.auth.presentation.components.AuthRegisterLoginComponent
import com.dtu.innovateX.core.components.ButtonComponent
import com.dtu.innovateX.core.components.HeadingTextComponent
import com.dtu.innovateX.core.components.TextComponent
import com.dtu.innovateX.core.components.TextFieldComponent
import com.dtu.innovateX.core.models.Resource
import com.dtu.innovateX.auth.presentation.components.ClickableTextComponent
import com.dtu.innovateX.auth.presentation.components.GoogleComponent

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLogInScreen: () -> Unit,
    navigateToDetailsScreen: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ){
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 20.dp)
                        .verticalScroll(
                            state = scrollState,
                            reverseScrolling = true
                        )
                ) {
                    Spacer(modifier = Modifier.height(105.dp))
                    HeadingTextComponent(text = "Sign Up")
                    Spacer(modifier = Modifier.height(38.dp))
                    ClickableTextComponent(
                        unClickableText = "Already have an account? ",
                        clickableText = "Log in",
                        onClick = {
                            navigateToLogInScreen()
                        }
                    )
                    Spacer(modifier = Modifier.height(38.dp))
                    TextComponent(text = "Email")
                    TextFieldComponent(
                        text = authState.email,
                        onValueChange = {
                            authViewModel.onEmailChange(it)
                        },
                        keyboardType = KeyboardType.Text,
                        hintText = "example@gmail.com",
                        errorText = authState.emailError,
                        leadingIcon = Icons.Outlined.Email,
                        trailingIcon = when{
                            authState.email.isEmpty() -> null
                            authState.emailError.isEmpty() -> Icons.Filled.CheckCircle
                            else -> Icons.Filled.Cancel
                        },
                        isError = authState.emailError.isNotEmpty(),
                        visualTransformation = VisualTransformation.None
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    TextComponent(text = "Password")
                    TextFieldComponent(
                        text = authState.password,
                        onValueChange = {
                            authViewModel.onPasswordChange(it)
                        },
                        keyboardType = KeyboardType.Password,
                        hintText = "must be 8 characters",
                        errorText = authState.passwordError,
                        leadingIcon = Icons.Outlined.Lock,
                        trailingIcon = if (authState.isPasswordVisible){
                            Icons.Filled.Visibility
                        }else{
                            Icons.Filled.VisibilityOff
                        },
                        onClickTrailingIcon = {
                            authViewModel.onPassWordVisible()
                        },
                        isError = authState.passwordError.isNotEmpty(),
                        visualTransformation = if (authState.isPasswordVisible){
                            VisualTransformation.None
                        }else{
                            PasswordVisualTransformation()
                        }
                    )
                    Spacer(modifier = Modifier.height(34.dp))
                    ButtonComponent(
                        onClickAction = {
                            authViewModel.signUp()
                        },
                        buttonText = "Sign up"
                    )
                    Spacer(modifier = Modifier.height(38.dp))
                    AuthRegisterLoginComponent(text = "or login with")
                    Spacer(modifier = Modifier.height(38.dp))
                    GoogleComponent(
                        onClickGoogle = {
                            authViewModel.googleSignIn()
                        }
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                }
                if (authState.state == Resource.Loading){
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .zIndex(1f)
                                .align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(authState.state) {
        if (authState.state is Resource.Success) {
            navigateToDetailsScreen()
        } else if (authState.state is Resource.Error) {
            snackbarHostState.showSnackbar(message = (authState.state as Resource.Error).message ?: "Unknown Error")
        }
    }
}