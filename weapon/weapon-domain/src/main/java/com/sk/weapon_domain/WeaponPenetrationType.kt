package com.sk.weapon_domain

sealed class WeaponPenetrationType(val uiValue: String, val apiValue: String) {

    object High :
        WeaponPenetrationType(uiValue = "High", apiValue = "EWallPenetrationDisplayType::High")

    object Medium :
        WeaponPenetrationType(uiValue = "Medium", apiValue = "EWallPenetrationDisplayType::Medium")

    object Low :
        WeaponPenetrationType(uiValue = "Low", apiValue = "EWallPenetrationDisplayType::Low")

    object Unknown : WeaponPenetrationType(uiValue = "Unknown", apiValue = "Unknown")
}

fun WeaponPenetrationType.getWeaponPenetrationTypeFromUiValue(uiValue: String): WeaponPenetrationType {
    return when (uiValue) {
        WeaponPenetrationType.High.uiValue -> {
            WeaponPenetrationType.High
        }

        WeaponPenetrationType.Medium.uiValue -> {
            WeaponPenetrationType.Medium
        }

        WeaponPenetrationType.Low.uiValue -> {
            WeaponPenetrationType.Low
        }
        else -> WeaponPenetrationType.Unknown
    }
}

fun WeaponPenetrationType.getWeaponPenetrationTypeFromApiValue(apiValue: String): WeaponPenetrationType {
    return when (apiValue) {
        WeaponPenetrationType.High.apiValue -> {
            WeaponPenetrationType.High
        }

        WeaponPenetrationType.Medium.apiValue -> {
            WeaponPenetrationType.Medium
        }

        WeaponPenetrationType.Low.apiValue -> {
            WeaponPenetrationType.Low
        }
        else -> WeaponPenetrationType.Unknown
    }
}
