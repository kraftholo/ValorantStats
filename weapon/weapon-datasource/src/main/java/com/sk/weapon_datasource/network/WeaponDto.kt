package com.sk.weapon_datasource.network

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.WeaponCategory
import com.sk.weapon_domain.WeaponStats

data class WeaponDto(
    val UUID: String,
    val displayName: String,
    val category: WeaponCategory,
    val defaultSkinUUID: String,
    val displayIcon: String,
    val killStreamIcon: String,
    val weaponStats: WeaponStats
) {
}

fun WeaponDto.toWeapon(): Weapon {
    return Weapon(
        UUID = UUID,
        displayName = displayName,
        category = category,
        defaultSkinUUID = defaultSkinUUID,
        displayIcon = displayIcon,
        killStreamIcon = killStreamIcon,
        weaponStats = weaponStats
    )
}