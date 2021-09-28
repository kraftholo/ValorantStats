package com.sk.weapon_domain

sealed class WeaponFireModeType(
    val uiValue: String,
    val apiValue: String
) {

    object SemiAutomatic : WeaponFireModeType(
        uiValue = "SemiAutomatic",
        apiValue = "EWeaponFireModeDisplayType::SemiAutomatic"
    )

    object Automatic : WeaponFireModeType(uiValue = "Automatic", apiValue = "")     //Api gives null for automatic
}

fun getWeaponFireModeTypeFromUiValue(uiValue: String): WeaponFireModeType {
    return when (uiValue) {

        WeaponFireModeType.SemiAutomatic.uiValue -> {
            WeaponFireModeType.SemiAutomatic
        }

        else -> WeaponFireModeType.Automatic

    }

}

fun getWeaponFireModeTypeFromApiValue(apiValue: String?): WeaponFireModeType {
    apiValue?.let {
        return when (apiValue) {

            WeaponFireModeType.SemiAutomatic.apiValue -> {
                WeaponFireModeType.SemiAutomatic
            }

            else -> WeaponFireModeType.Automatic

        }
    } ?: return WeaponFireModeType.Automatic

}