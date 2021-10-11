package com.sk.weapon_interactors

import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_datasource.cache.WeaponDatabase
import com.sk.weapon_datasource.network.WeaponService
import com.squareup.sqldelight.db.SqlDriver

class WeaponInteractors(
    val getWeapons: GetWeapons,
    val getWeaponsFromCache: GetWeaponFromCache,
    val getSkins: GetSkins,
    val getSkinFromCache: GetSkinFromCache,
    val getLevelsFromCache: GetLevelsFromCache
) {

    // viewmodel will not know how it is actually implemented inside
    companion object Factory {
        fun build(sqlDriver: SqlDriver): WeaponInteractors {
            val service = WeaponService.build()
            return WeaponInteractors(
                getWeapons = GetWeapons(
                    service = service,
                    cache = WeaponCache.build(sqlDriver)
                ),

                getWeaponsFromCache = GetWeaponFromCache(
                    cache = WeaponCache.build(sqlDriver)
                ),

                getSkins = GetSkins(
                    service = service,
                    cache = WeaponCache.build(sqlDriver)
                ),
                getSkinFromCache = GetSkinFromCache(
                    WeaponCache.build(sqlDriver)
                ),
                getLevelsFromCache = GetLevelsFromCache(
                    WeaponCache.build(sqlDriver)
                )
            )
        }

        //Will be used to make the android sqldriver
        val schema = WeaponDatabase.Schema
        val dbName = "weapons.db"
    }

}