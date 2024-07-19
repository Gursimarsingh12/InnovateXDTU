package com.dtu.innovateX

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dtu.innovateX.auth.presentation.AuthViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ){
            authViewModel.getCurrentUser()?.email?.let { Text(text = it, color = Color.White) }
        }
    }
}