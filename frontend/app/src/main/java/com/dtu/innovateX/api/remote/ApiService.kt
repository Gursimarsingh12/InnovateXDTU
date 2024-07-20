package com.dtu.innovateX.api.remote

import com.dtu.innovateX.api.models.DeviceRequest
import com.dtu.innovateX.api.models.DeviceResponse
import com.dtu.innovateX.api.models.UserPostRequest
import com.dtu.innovateX.api.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("user/create-user")
    suspend fun createUser(
        @Body userPostRequest: UserPostRequest
    ): Response<UserResponse>

    @POST("user/add-device")
    suspend fun addDeviceToDB(
        @Query("userEmail") userEmail: String,
        @Body deviceRequest: DeviceRequest
    ): Response<UserResponse>

    @GET("user/get-user")
    suspend fun getUser(
        @Query("userEmail") userEmail: String
    ): Response<UserResponse>

    @GET("user/get-devices")
    suspend fun getDevices(
        @Query("userEmail") userEmail: String
    ): Response<List<DeviceResponse>>

    @POST("user/update-device")
    suspend fun updateDevice(
        @Query("userEmail") userEmail: String,
        @Body deviceRequest: DeviceRequest
    ): Response<DeviceResponse>

    @DELETE("user/delete-device")
    suspend fun deleteDevice(
        @Query("userEmail") userEmail: String,
        @Query("deviceName") deviceName: String
    ): Response<Map<String, String>>

    @POST("user/delete-user")
    suspend fun deleteUser(
        @Query("userEmail") userEmail: String
    ): Response<Map<String, String>>
}
