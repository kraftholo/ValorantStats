package com.sk.weapon_datasource.cache

import com.sk.weapon_domain.Weapon
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


}