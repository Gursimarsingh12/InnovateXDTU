package com.dtu.innovateX.api.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("userEmail")
    val email: String? = null
)
