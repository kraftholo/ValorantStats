package com.sk.weapon_datasource.network

import com.sk.weapon_datasource.network.model.toWeapon
import com.sk.weapon_datasource.network.responses.GetWeaponsResponse
import com.sk.weapon_domain.Weapon
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
}