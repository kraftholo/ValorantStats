package com.sk.weapon_domain

sealed class WeaponAltFireType(val uiValue: String, val apiValue: String) {

    object ADS :
        WeaponAltFireType(uiValue = "ADS", apiValue = "EWeaponAltFireDisplayType::ADS")

    object AirBurst :
        WeaponAltFireType(uiValue = "AirBurst", apiValue = "EWallPenetrationDisplayType::AirBurst")

    object Shotgun :
        WeaponAltFireType(uiValue = "Shotgun", apiValue = "EWallPenetrationDisplayType::Shotgun")

    object Unknown : WeaponAltFireType(uiValue = "Unknown", apiValue = "Unknown")
}

fun WeaponAltFireType.getWeaponAltFireTypeFromUiValue(uiValue: String): WeaponAltFireType {
    return when (uiValue) {
        WeaponAltFireType.ADS.uiValue -> {
            WeaponAltFireType.ADS
        }

        WeaponAltFireType.AirBurst.uiValue -> {
            WeaponAltFireType.AirBurst
        }

        WeaponAltFireType.Shotgun.uiValue -> {
            WeaponAltFireType.Shotgun
        }
        else -> WeaponAltFireType.Unknown
    }
}

fun WeaponAltFireType.getWeaponPenetrationTypeFromApiValue(apiValue: String): WeaponAltFireType {
    return when (apiValue) {
        WeaponAltFireType.ADS.apiValue -> {
            WeaponAltFireType.ADS
        }

        WeaponAltFireType.AirBurst.apiValue -> {
            WeaponAltFireType.AirBurst
        }

        WeaponAltFireType.Shotgun.apiValue -> {
            WeaponAltFireType.Shotgun
        }
        else -> WeaponAltFireType.Unknown
    }
}