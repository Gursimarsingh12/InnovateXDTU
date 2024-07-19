package com.dtu.innovateX.auth.domain.model

import com.dtu.innovateX.core.models.Resource

data class UserState<T> (
    val name: String = "",
    val nameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordError: String = "",
    val state: Resource<T> = Resource.Idle
)