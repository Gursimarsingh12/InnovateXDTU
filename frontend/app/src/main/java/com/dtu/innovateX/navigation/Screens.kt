package com.dtu.innovateX.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object SignUpScreen: Screens()

    @Serializable
    data object HomeScreen : Screens()

    @Serializable
    data object BluetoothScreen: Screens()

    @Serializable
    data class AddDeviceScreen(val deviceName: String): Screens()
}