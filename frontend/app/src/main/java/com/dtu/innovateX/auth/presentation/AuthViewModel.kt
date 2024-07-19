package com.dtu.innovateX.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtu.innovateX.auth.domain.model.UserState
import com.dtu.innovateX.auth.domain.model.UserValidator
import com.dtu.innovateX.auth.domain.use_case.AuthUseCase
import com.dtu.innovateX.core.models.User
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow(UserState<FirebaseUser>())
    val authState: StateFlow<UserState<FirebaseUser>> = _authState.asStateFlow()

    fun onEmailChange(email: String) {
        _authState.update {
            it.copy(
                email = email,
                emailError = UserValidator.validateEmail(email)
            )
        }
    }

    fun onPasswordChange(password: String) {
        _authState.update {
            it.copy(
                password = password,
                passwordError = UserValidator.validatePassword(password)
            )
        }
    }

    fun onPassWordVisible() {
        _authState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    fun signUp() {
        if (isValidInput()) {
            val user = User(
                email = _authState.value.email,
                password = _authState.value.password,
                name = authState.value.name
            )
            viewModelScope.launch {
                authUseCase.signUp(user).collectLatest { state ->
                    _authState.update {
                        it.copy(
                            state = state
                        )
                    }
                }
            }
        }
    }

    fun signIn() {
        if (isValidInput()) {
            val user = User(
                email = _authState.value.email,
                password = _authState.value.password
            )
            viewModelScope.launch {
                authUseCase.signIn(user).collectLatest { state ->
                    _authState.update {
                        it.copy(
                            state = state
                        )
                    }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val emailError = UserValidator.validateEmail(_authState.value.email)
        val passwordError = UserValidator.validatePassword(_authState.value.password)
        _authState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }
        return emailError.isEmpty() && passwordError.isEmpty()
    }

    fun googleSignIn() = viewModelScope.launch {
        authUseCase.googleSignIn().collectLatest { state ->
            _authState.update {
                it.copy(
                    state = state
                )
            }
        }
    }

    fun getCurrentUser() = authUseCase.getCurrentUser()

    fun signOut() = viewModelScope.launch {
        authUseCase.signOut()
    }

}