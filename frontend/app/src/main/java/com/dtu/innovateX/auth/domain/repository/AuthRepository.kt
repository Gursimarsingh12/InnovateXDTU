package com.dtu.innovateX.auth.domain.repository

import com.dtu.innovateX.core.models.Resource
import com.dtu.innovateX.core.models.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(user: User): Flow<Resource<FirebaseUser>>
    suspend fun signIn(user: User): Flow<Resource<FirebaseUser>>
    suspend fun googleSignIn(): Flow<Resource<FirebaseUser>>
    fun getCurrentUser(): FirebaseUser?
    suspend fun signOut()
}