package com.sk.weapon_domain

sealed class WeaponFireModeType(
    val uiValue: String,
    val apiValue: String
) {

    object SemiAutomatic : WeaponFireModeType(
        uiValue = "WeaponFireMode",
        apiValue = "EWeaponFireModeDisplayType::SemiAutomatic"
    )

    object Unknown : WeaponFireModeType(uiValue = "Unknown", apiValue = "Unknown")
}

fun getWeaponFireModeTypeFromUiValue(uiValue: String): WeaponFireModeType {
    return when (uiValue) {

        WeaponFireModeType.SemiAutomatic.uiValue -> {
            WeaponFireModeType.SemiAutomatic
        }

        else -> WeaponFireModeType.Unknown

    }

}

fun getWeaponFireModeTypeFromApiValue(apiValue: String?): WeaponFireModeType {
    apiValue?.let {
        return when (apiValue) {

            WeaponFireModeType.SemiAutomatic.apiValue -> {
                WeaponFireModeType.SemiAutomatic
            }

            else -> WeaponFireModeType.Unknown

        }
    } ?: return WeaponFireModeType.Unknown

}