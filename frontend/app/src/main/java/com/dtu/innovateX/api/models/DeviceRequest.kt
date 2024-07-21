package com.dtu.innovateX.api.models

data class DeviceRequest(
    val deviceName: String? = null,
    val deviceType: String? = null,
    val deviceModel: String? = null,
    val deviceLocation: String? = null,
    val deviceBuilding: String? = null,
    val deviceFloor: Int? = null,
    val deviceRoom: String? = null,
    val deviceHourlyConsumption: Int? = null,
    val deviceUsageTime: Int? = null
)
