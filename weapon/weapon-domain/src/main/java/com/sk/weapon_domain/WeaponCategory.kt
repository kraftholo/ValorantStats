package com.sk.weapon_domain

sealed class WeaponCategory(
    val uiValue: String,
    val apiValue: String
) {
    object Heavy : WeaponCategory(uiValue = "Heavy", apiValue = "EEquippableCategory::Heavy")
    object Rifle : WeaponCategory(uiValue = "Rifle", apiValue = "EEquippableCategory::Rifle")
    object SMG : WeaponCategory(uiValue = "SMG", apiValue = "EEquippableCategory::SMG")
    object Shotgun : WeaponCategory(uiValue = "Shotgun", apiValue = "EEquippableCategory::Shotgun")
    object SideArm : WeaponCategory(uiValue = "Sidearm", apiValue = "EEquippableCategory::Sidearm")
    object Sniper : WeaponCategory(uiValue = "Sniper", apiValue = "EEquippableCategory::Sniper")
    object Melee : WeaponCategory(uiValue = "Melee", apiValue = "EEquippableCategory::Melee")
    object Unknown : WeaponCategory(uiValue = "Unknown", apiValue = "Unknown")

}

//kotlin extension functions to help
fun getWeaponCategoryFromUiValue(uiValue: String): WeaponCategory {
    val weaponCategory = when (uiValue) {
        WeaponCategory.Heavy.uiValue -> {
            WeaponCategory.Heavy
        }
        WeaponCategory.Rifle.uiValue -> {
            WeaponCategory.Rifle
        }
        WeaponCategory.SMG.uiValue -> {
            WeaponCategory.SMG
        }
        WeaponCategory.Shotgun.uiValue -> {
            WeaponCategory.Shotgun
        }
        WeaponCategory.Sniper.uiValue -> {
            WeaponCategory.Sniper
        }
        WeaponCategory.SideArm.uiValue -> {
            WeaponCategory.SideArm
        }
        WeaponCategory.Melee.uiValue -> {
            WeaponCategory.Melee
        }

        else -> WeaponCategory.Unknown
    }
    return weaponCategory
}

fun getWeaponCategoryFromApiValue(apiValue: String): WeaponCategory {
    return when (apiValue) {
        WeaponCategory.Heavy.apiValue -> {
            WeaponCategory.Heavy
        }
        WeaponCategory.Rifle.apiValue -> {
            WeaponCategory.Rifle
        }
        WeaponCategory.SMG.apiValue -> {
            WeaponCategory.SMG
        }
        WeaponCategory.Shotgun.apiValue -> {
            WeaponCategory.Shotgun
        }
        WeaponCategory.Sniper.apiValue -> {
            WeaponCategory.Sniper
        }
        WeaponCategory.SideArm.apiValue -> {
            WeaponCategory.SideArm
        }
        WeaponCategory.Melee.apiValue -> {
            WeaponCategory.Melee
        }

        else -> WeaponCategory.Unknown
    }
}

