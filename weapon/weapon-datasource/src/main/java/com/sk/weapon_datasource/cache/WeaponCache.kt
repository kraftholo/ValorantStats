package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin
import com.squareup.sqldelight.db.SqlDriver

interface WeaponCache {

    companion object Factory {

        //not AndroidDriver, keeping this a pure kotlin library
        fun build(sqlDriver: SqlDriver): WeaponCache {
            return WeaponCacheImpl(WeaponDatabase(sqlDriver))
        }
    }

    suspend fun getAllWeapons(): List<Weapon>
    suspend fun insert(weapons: List<Weapon>)
    suspend fun insertWeapon(weapon: Weapon)
    suspend fun getWeaponsByCategory(category: String): List<Weapon>
    suspend fun getWeaponByName(displayName: String): Weapon
    suspend fun getWeaponByUUID(uuid: String): Weapon?
    suspend fun removeWeaponByUUID(uuid: String)

    suspend fun insertSkin(skin: Skin)
    suspend fun getSkinsForWeapon(weaponUuid: String): List<Skin>
    suspend fun removeAllSkinsForWeapon(weaponUuid: String)
    suspend fun removeSkin(uuid: String)

    suspend fun insertLevel(level: Level)
    suspend fun getLevelsForSkin(skinUuid: String): List<Level>
    suspend fun removeAllLevelsForSkin(skinUuid: String)
    suspend fun removeLevel(uuid: String)

    suspend fun insertChroma(chroma: Chroma)
    suspend fun getChromasForSkin(skinUuid: String): List<Chroma>
    suspend fun removeAllChromaForSkin(skinUuid: String)
    suspend fun removeChroma(uuid: String)


}