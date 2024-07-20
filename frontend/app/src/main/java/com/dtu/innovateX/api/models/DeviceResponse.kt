package com.dtu.innovateX.api.models

import com.google.gson.annotations.SerializedName

data class DeviceResponse(
    val deviceType: String,
    val deviceName: String,
    val deviceLocation: String,
    val deviceFloor: Int,
    val deviceHourlyConsumption: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val id: Int,
    val deviceModel: String,
    val deviceBuilding: String,
    val deviceRoom: String,
    val deviceUsageTime: Int
)
