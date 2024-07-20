package com.dtu.innovateX.api.models

import com.google.gson.annotations.SerializedName

data class UserPostRequest(
    @SerializedName("userEmail")
    val email: String? = null,
    @SerializedName("userDevices")
    val userDevices: List<DeviceRequest>? = emptyList()
)
