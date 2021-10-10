package com.sk.weapon_datasource.network

import com.sk.weapon_datasource.network.model.skin.toSkin
import com.sk.weapon_datasource.network.model.toWeapon
import com.sk.weapon_datasource.network.responses.GetSkinsResponse
import com.sk.weapon_datasource.network.responses.GetWeaponsResponse
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Skin
import io.ktor.client.*
import io.ktor.client.request.*


class WeaponServiceImpl(
    private val httpClient: HttpClient
) : WeaponService {

    override suspend fun getWeaponStats(): List<Weapon> {
        return httpClient.get<GetWeaponsResponse> {
            url(EndPoints.WEAPONS)
        }.data.map {
            it.toWeapon()                  //convert Dtos to Domain Models
        }
    }

    //gets all skins and then filtered accordingly
    override suspend fun getSkins(weaponName: String): List<Skin> {
        println("WeaponServiceImpl.getSkins() : weaponName = $weaponName")
        return httpClient.get<GetSkinsResponse> {
            url(EndPoints.SKINS)
        }.data.filter {
            println("WeaponServiceImpl.getSkins() : skin = $it")
            if (weaponName == "Melee") {
                it.chromas[0].assetPath.contains(weaponName)
            } else {
                it.displayName.contains(weaponName)
            }

        }.map { it.toSkin() }
    }
}