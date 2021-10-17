package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.*
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.LevelItemType
import com.sk.weapon_domain.skin.Skin
import com.sk.weapon_domain.stats.*
import com.sk.weapondatasource.cache.Chroma_Entity
import com.sk.weapondatasource.cache.Level_Entity
import com.sk.weapondatasource.cache.Skin_Entity
import com.sk.weapondatasource.cache.Weapon_Entity


fun Weapon_Entity.toWeapon(): Weapon {
    var weaponStats: WeaponStats? = null
    var weaponADSStats: WeaponADSStats? = null



    hasADSStats?.let {
        if (it.toInt() == 1) {
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
        if (it.toInt() == 1) {
            weaponStats = WeaponStats(
                fireRate?.toFloat(),
                magazineSize?.toInt(),
                runSpeedMultiplier?.toFloat(),
                equipTimeSeconds?.toFloat(),
                reloadTimeSeconds?.toFloat(),
                firstBulletAccuracy?.toFloat(),
                shotgunPelletCount?.toInt(),
                wallPenetration?.let { pen -> getWeaponPenetrationTypeFromUiValue(pen) },
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
        shopData = ShopData(cost = shopDataCost?.toInt() ?: 0),
        skins = listOf()
    )
}

fun Skin_Entity.toSkin(): Skin {
    return Skin(
        uuid = uuid,
        displayName = displayName,
        themeUuid = themeUuid,
        contentTierUuid = contentTierUuid,
        displayIcon = displayIcon,
        chromas = listOf(),
        levels = listOf(),
        hasLevels = hasLevels?.toInt() == 1
    )
}

fun Chroma_Entity.toChroma(): Chroma {
    return Chroma(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
        fullRender = fullRender,
        swatch = swatch,
        streamedVideo = streamedVideo
    )
}


fun Level_Entity.getItemType(): LevelItemType {
    if (hasVFX?.toInt() == 1) return LevelItemType.VFX
    if (hasAnimation?.toInt() == 1) return LevelItemType.Animation
    if (hasFinisher?.toInt() == 1) return LevelItemType.Finisher
    if (hasKillCounter?.toInt() == 1) return LevelItemType.KillCounter
    return LevelItemType.Unknown
}

fun Level_Entity.toLevel(): Level {
    return Level(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
        streamedVideo = streamedVideo,
        levelItemType = getItemType()
    )
}