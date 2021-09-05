package com.sk.weapon_domain

sealed class WeaponStatsFeature(
    val uiValue: String,
    val apiValue: String
) {

    object ROFIncrease : WeaponStatsFeature(
        uiValue = "Rate Of Fire Increase",
        apiValue = "EWeaponsStatsFeature::ROFIncrease"
    )

    object Silenced :
        WeaponStatsFeature(uiValue = "Silenced", apiValue = "EWeaponsStatsFeature::Silenced")

    object DualZoom :
        WeaponStatsFeature(uiValue = "DualZoom", apiValue = "EWeaponsStatsFeature::DualZoom")

    object Unknown : WeaponStatsFeature(uiValue = "Unknown", apiValue = "Unknown")

}

fun getWeaponStatsFeatureFromUiValue(uiValue: String): WeaponStatsFeature {
    return when (uiValue) {
        WeaponStatsFeature.ROFIncrease.uiValue -> {
            WeaponStatsFeature.ROFIncrease
        }
        WeaponStatsFeature.Silenced.uiValue -> {
            WeaponStatsFeature.Silenced
        }
        WeaponStatsFeature.DualZoom.uiValue -> {
            WeaponStatsFeature.DualZoom
        }

        else -> WeaponStatsFeature.Unknown
    }
}

fun getWeaponStatsFeatureFromApiValue(apiValue: String?): WeaponStatsFeature {
    apiValue?.let {
        return when (apiValue) {
            WeaponStatsFeature.ROFIncrease.apiValue -> {
                WeaponStatsFeature.ROFIncrease
            }
            WeaponStatsFeature.Silenced.apiValue -> {
                WeaponStatsFeature.Silenced
            }
            WeaponStatsFeature.DualZoom.apiValue -> {
                WeaponStatsFeature.DualZoom
            }
            else -> WeaponStatsFeature.Unknown
        }
    } ?: return WeaponStatsFeature.Unknown
}