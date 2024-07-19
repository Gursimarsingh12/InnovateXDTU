package com.dtu.innovateX.auth.domain.use_case

import com.dtu.innovateX.auth.domain.repository.AuthRepository
import com.dtu.innovateX.core.models.User
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun signUp(user: User) = repository.signUp(user)
    suspend fun signIn(user: User) = repository.signIn(user)
    suspend fun googleSignIn() = repository.googleSignIn()
    fun getCurrentUser() = repository.getCurrentUser()
    suspend fun signOut() = repository.signOut()
}