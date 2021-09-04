package com.sk.weapon_domain

sealed class WeaponCategory(
    val uiValue: String,
    val apiValue: String
) {
    object Heavy : WeaponCategory(uiValue = "Heavy", apiValue = "EEquippableCategory::Heavy")
    object Rifle : WeaponCategory(uiValue = "Rifle", apiValue = "EEquippableCategory::Rifle")
    object SMG : WeaponCategory(uiValue = "SMG", apiValue = "EEquippableCategory::SMG")
    object SideArm : WeaponCategory(uiValue = "SideArm", apiValue = "EEquippableCategory::SideArm")
    object Melee : WeaponCategory(uiValue = "Melee", apiValue = "EEquippableCategory::Melee")
    object Unknown : WeaponCategory(uiValue = "Unknown", apiValue = "Unknown")

}

//kotlin extension functions to help
fun WeaponCategory.getWeaponCategoryFromUiValue(uiValue: String): WeaponCategory {
    return when (uiValue) {
        WeaponCategory.Heavy.uiValue -> {
            WeaponCategory.Heavy
        }
        WeaponCategory.Rifle.uiValue -> {
            WeaponCategory.Rifle
        }
        WeaponCategory.SMG.uiValue -> {
            WeaponCategory.SMG
        }
        WeaponCategory.SideArm.uiValue -> {
            WeaponCategory.SideArm
        }
        WeaponCategory.Melee.uiValue -> {
            WeaponCategory.Melee
        }

        else -> WeaponCategory.Unknown
    }
}

fun WeaponCategory.getWeaponCategoryFromApiValue(apiValue: String): WeaponCategory {
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
        WeaponCategory.SideArm.apiValue -> {
            WeaponCategory.SideArm
        }
        WeaponCategory.Melee.apiValue -> {
            WeaponCategory.Melee
        }

        else -> WeaponCategory.Unknown
    }
}

