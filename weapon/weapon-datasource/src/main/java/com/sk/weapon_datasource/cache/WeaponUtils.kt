package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.*
import com.sk.weapondatasource.cache.Weapon_Entity

fun Weapon_Entity.toWeapon() : Weapon{
    var weaponStats : WeaponStats? = null
    var weaponADSStats : WeaponADSStats? = null

    hasADSStats?.let {
        if(it.toInt() == 1){
            weaponADSStats = WeaponADSStats(
                zoomMultiplierADS?.toFloat(),
                fireRateADS?.toFloat(),
                runSpeedMultiplierADS?.toFloat(),
                burstCountADS?.toInt(),
                firstBulletAccuracyADS?.toFloat()
            )
        }
    }

    hasWeaponStats?.let {
        if(it.toInt() == 1){
            weaponStats = WeaponStats(
                fireRate?.toFloat(),
                magazineSize?.toInt(),
                runSpeedMultiplier?.toFloat(),
                equipTimeSeconds?.toFloat(),
                reloadTimeSeconds?.toFloat(),
                firstBulletAccuracy?.toFloat(),
                shotgunPelletCount?.toInt(),
                wallPenetration?.let {pen -> getWeaponPenetrationTypeFromUiValue(pen)},
                feature?.let { feat -> getWeaponStatsFeatureFromUiValue(feat) },
                fireMode?.let { fMode -> getWeaponFireModeTypeFromUiValue(fMode) },
                altFireType?.let { altFMode -> getWeaponAltFireTypeFromUiValue(altFMode) },
                weaponADSStats
            )
        }
    }
    return Weapon(
        UUID = uuid,
        displayName = displayName,
        category = getWeaponCategoryFromUiValue(category),
        defaultSkinUUID = defaultSkinUUID,
        displayIcon = displayIcon,
        killStreamIcon = killStreamIcon,
        weaponStats = weaponStats,
    )
}