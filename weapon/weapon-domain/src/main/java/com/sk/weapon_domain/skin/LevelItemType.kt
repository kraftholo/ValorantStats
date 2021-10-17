package com.sk.weapon_domain.skin

import com.sk.weapon_domain.stats.WeaponAltFireType

sealed class LevelItemType(val uiValue: String, val apiValue: String) {

    object VFX :
        LevelItemType(uiValue = "VFX", apiValue = "EEquippableSkinLevelItem::VFX")

    object Finisher :
        LevelItemType(uiValue = "Finisher", apiValue = "EEquippableSkinLevelItem::Finisher")

    object Animation :
        LevelItemType(uiValue = "Animation", apiValue = "EEquippableSkinLevelItem::Animation")

    object KillCounter :
        LevelItemType(uiValue = "KillCounter", apiValue = "EEquippableSkinLevelItem::KillCounter")

    object Unknown : LevelItemType(uiValue = "Unknown", apiValue = "Unknown")
}

fun getLevelItemTypeFromUiValue(uiValue: String): LevelItemType {
    return when (uiValue) {
        LevelItemType.VFX.uiValue -> {
            LevelItemType.VFX
        }

        LevelItemType.Finisher.uiValue -> {
            LevelItemType.Finisher
        }

        LevelItemType.Animation.uiValue -> {
            LevelItemType.Animation
        }

        LevelItemType.KillCounter.uiValue -> {
            LevelItemType.KillCounter
        }

        else -> LevelItemType.Unknown
    }
}

fun getLevelItemTypeFromApiValue(apiValue: String): LevelItemType {
    return when (apiValue) {
        LevelItemType.VFX.apiValue -> {
            LevelItemType.VFX
        }

        LevelItemType.Finisher.apiValue -> {
            LevelItemType.Finisher
        }

        LevelItemType.Animation.apiValue -> {
            LevelItemType.Animation
        }

        LevelItemType.KillCounter.apiValue -> {
            LevelItemType.KillCounter
        }

        else -> LevelItemType.Unknown
    }
}