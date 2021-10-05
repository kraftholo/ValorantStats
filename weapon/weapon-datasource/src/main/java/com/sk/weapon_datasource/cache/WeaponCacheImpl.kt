package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin

class WeaponCacheImpl(
    val database: WeaponDatabase
) : WeaponCache {

    val queries = database.weaponDbQueries

    override suspend fun getAllWeapons(): List<Weapon> {
        return queries.selectAllWeapons().executeAsList().map { it.toWeapon() }
    }

    override suspend fun insert(weapons: List<Weapon>) {
        for(weapon in weapons){
            try{
                insertWeapon(weapon)    // Insert others even if one fails
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

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
                shopDataCost = shopData.cost.toLong()
            )
        }
    }

    override suspend fun getWeaponsByCategory(category: String): List<Weapon> {
       return queries.searchWeaponByCategory(category).executeAsList().map{
           it.toWeapon().apply {
            //Todo
           }
       }
    }

    override suspend fun getWeaponByName(displayName: String): Weapon {
       return queries.searchWeaponByName(displayName).executeAsOne().toWeapon()
    }

    override suspend fun getWeaponByUUID(uuid: String): Weapon? {
       return queries.getWeapon(uuid).executeAsOne().toWeapon()
    }

    override suspend fun removeWeaponByUUID(uuid: String) {
       return queries.removeWeapon(uuid)
    }

    override suspend fun insertSkin(skin: Skin) {
        TODO("Not yet implemented")
    }

    override suspend fun getSkinsForWeapon(weaponUuid: String): List<Skin> {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllSkinsForWeapon(weaponUuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSkin(uuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLevel(level: Level) {
        TODO("Not yet implemented")
    }

    override suspend fun getLevelsForSkin(skinUuid: String): List<Level> {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllLevelsForSkin(skinUuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeLevel(uuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertChroma(chroma: Chroma) {
        TODO("Not yet implemented")
    }

    override suspend fun getChromasForSkin(skinUuid: String): List<Chroma> {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllChromaForSkin(skinUuid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeChroma(uuid: String) {
        TODO("Not yet implemented")
    }


}