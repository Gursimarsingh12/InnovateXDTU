package com.dtu.innovateX.di

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.dtu.innovateX.api.remote.ApiService
import com.dtu.innovateX.auth.data.repository.AuthRepositoryImpl
import com.dtu.innovateX.auth.domain.repository.AuthRepository
import com.dtu.innovateX.auth.domain.use_case.AuthUseCase
import com.dtu.innovateX.bluetooth.data.repository.BluetoothRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://innovatexdtu.onrender.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesAuthUseCase(repository: AuthRepository): AuthUseCase = AuthUseCase(repository)

    @Provides
    @Singleton
    fun providesBluetoothAdapter(): BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    @Provides
    @Singleton
    fun providesBluetoothRepository(@ApplicationContext context: Context, bluetoothAdapter: BluetoothAdapter) = BluetoothRepository(context, bluetoothAdapter)

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}