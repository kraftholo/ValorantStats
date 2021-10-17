package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin

class WeaponCacheImpl(
    val database: WeaponDatabase
) : WeaponCache {

    val queries = database.weaponDbQueries

    override suspend fun insertWeapons(weapons: List<Weapon>) {
        for (weapon in weapons) {
            try {
                insertWeapon(weapon)    // Insert others even if one fails
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //gets weapons (NO SKINS)
    override suspend fun getAllWeapons(): List<Weapon> {
        return queries.selectAllWeapons().executeAsList().map { it.toWeapon() }
    }

    //inserts all Weapons (NO SKINS)
    override suspend fun insertWeapon(weapon: Weapon) {
        return weapon.run {
            queries.insertWeapon(
                uuid = UUID,
                displayName = displayName,
                category = category.uiValue,
                defaultSkinUUID = defaultSkinUUID,
                killStreamIcon = killStreamIcon,
                displayIcon = displayIcon,
                hasWeaponStats = when (weaponStats != null) {
                    true -> 1
                    false -> 0
                },
                fireRate = weaponStats?.fireRate?.toDouble(),
                magazineSize = weaponStats?.magazineSize?.toLong(),
                runSpeedMultiplier = weaponStats?.runSpeedMultiplier?.toDouble(),
                equipTimeSeconds = weaponStats?.equipTimeSeconds?.toDouble(),
                reloadTimeSeconds = weaponStats?.reloadTimeSeconds?.toDouble(),
                firstBulletAccuracy = weaponStats?.firstBulletAccuracy?.toDouble(),
                shotgunPelletCount = weaponStats?.shotgunPelletCount?.toLong(),
                wallPenetration = weaponStats?.wallPenetration?.uiValue,
                feature = weaponStats?.feature?.uiValue,
                fireMode = weaponStats?.fireMode?.uiValue,
                altFireType = weaponStats?.altFireType?.uiValue,
                hasADSStats = when (weaponStats?.adsStats != null) {
                    true -> 1
                    false -> 0
                },
                fireRateADS = weaponStats?.adsStats?.fireRate?.toDouble(),
                runSpeedMultiplierADS = weaponStats?.adsStats?.runSpeedMultiplier?.toDouble(),
                firstBulletAccuracyADS = weaponStats?.adsStats?.firstBulletAccuracy?.toDouble(),
                zoomMultiplierADS = weaponStats?.adsStats?.zoomMultiplier?.toDouble(),
                burstCountADS = weaponStats?.adsStats?.burstCount?.toLong(),
                shopDataCost = shopData?.cost?.toLong() ?: 0
            )
        }
    }

    override suspend fun insertSkins(weaponUuid: String, skins: List<Skin>) {
        //Insert Skins
        skins.forEach { skin ->
            queries.insertSkin(
                weaponUUID = weaponUuid,
                uuid = skin.uuid,
                displayName = skin.displayName,
                themeUuid = skin.themeUuid,
                contentTierUuid = skin.contentTierUuid,
                displayIcon = skin.displayIcon ?: "",
                hasLevels = if (skin.levels.size>1) 1 else 0
            )

            //Insert Chromas
            skin.chromas.forEach { chroma ->
                queries.insertChroma(
                    skinUUID = skin.uuid,
                    uuid = chroma.uuid,
                    displayName = chroma.displayName,
                    displayIcon = chroma.displayIcon ?: "",
                    fullRender = chroma.fullRender,
                    swatch = chroma.swatch,
                    streamedVideo = chroma.streamedVideo
                )
            }

            //Insert Levels
            skin.levels.forEach { level ->
                queries.insertLevel(
                    skinUUID = skin.uuid,
                    uuid = level.uuid,
                    displayName = level.displayName ?: "",
                    displayIcon = level.displayIcon ?: "",
                    streamedVideo = level.streamedVideo
                )
            }
        }
    }

    //Weapons(NO SKINS)
    override suspend fun getWeaponsByCategory(category: String): List<Weapon> {
        return queries.searchWeaponByCategory(category).executeAsList().map { it.toWeapon() }
    }

    //Weapon(NO SKINS)
    override suspend fun getWeaponByName(displayName: String): Weapon {
        return queries.searchWeaponByName(displayName).executeAsOne().toWeapon()
    }

    //Weapon(NO SKINS)
    override suspend fun getWeaponByUUID(uuid: String): Weapon? {
        return queries.getWeapon(uuid).executeAsOne().toWeapon()
    }

    //Remove Weapon + Skins
    override suspend fun removeWeaponByUUID(uuid: String) {
        val weapon = queries.getWeapon(uuid).executeAsOne().toWeapon()
        //remove data from weapon table
        return queries.removeWeapon(uuid).also {
            //remove data from skins table
            queries.removeAllSkinsForWeapon(uuid)
            weapon.skins.forEach { skin ->
                //remove data from chroma and level table
                queries.removeAllChromasForSkin(skin.uuid)
                queries.removeAllLevelsForSkin(skin.uuid)
            }
        }
    }

    override suspend fun getSkinByUUID(skinUuid: String): Skin {
        val skin = queries.getSkin(skinUuid).executeAsOne().toSkin()
        skin.chromas = queries.getChromas(skinUuid).executeAsList().map { it.toChroma() }
        return skin
    }

    override suspend fun getSkinsForWeapon(weaponUuid: String): List<Skin> {
        return queries.getSkins(weaponUuid).executeAsList().map {
            it.toSkin().also { skin ->
                skin.chromas = queries.getChromas(skin.uuid).executeAsList().map { it.toChroma() }
               // skin.levels = queries.getLevels(skin.uuid).executeAsList().map { it.toLevel() }
            }

        }
    }

    override suspend fun getChromasForSkin(skinUuid: String): List<Chroma> {
        return queries.getChromas(skinUuid).executeAsList().map { it.toChroma() }
    }

    override suspend fun getLevelsForSkin(skinUuid: String): List<Level> {
        return queries.getLevels(skinUuid).executeAsList().map { it.toLevel() }
    }


}