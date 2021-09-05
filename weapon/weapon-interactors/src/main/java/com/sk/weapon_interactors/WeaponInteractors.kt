package com.sk.weapon_interactors

import com.sk.weapon_datasource.network.WeaponService

class WeaponInteractors(
    val getWeapons: GetWeapons
) {

    // viewmodel will not know how it is actually implemented inside
    companion object Factory {
        fun build() : WeaponInteractors {
            val service = WeaponService.build()
            return WeaponInteractors(
                getWeapons = GetWeapons(service = service)
            )
        }
    }

}