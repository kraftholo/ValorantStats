package com.sk.weapon_datasource.network

import com.sk.weapon_domain.Weapon

interface WeaponService {

    //Returning a List of Weapons and not WeaponDtos ( although we will get WeaponDtos from request first)
    suspend fun getWeaponStats(): List<Weapon>
}