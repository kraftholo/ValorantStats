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
    suspend fun insertWeapons(weapons: List<Weapon>)
    suspend fun insertWeapon(weapon: Weapon)
    suspend fun getWeaponsByCategory(category: String): List<Weapon>
    suspend fun getWeaponByName(displayName: String): Weapon
    suspend fun getWeaponByUUID(uuid: String): Weapon?
    suspend fun removeWeaponByUUID(uuid: String)

    suspend fun insertSkins(weaponUuid:String,skins: List<Skin>)

    suspend fun getSkinsForWeapon(weaponUuid: String): List<Skin>?
    suspend fun getLevelsForSkin(skinUuid: String): List<Level>
    suspend fun getChromasForSkin(skinUuid: String): List<Chroma>

}