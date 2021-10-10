package com.sk.weapon_datasource.network

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Skin
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json

interface WeaponService {

    //Purpose of factory
    //When we want this service built inside interactors, we dont want the interactors to know how it is being built inside the datasource ( using ktor or not etc.)
    companion object Factory {
        fun build(): WeaponService {
            return WeaponServiceImpl(httpClient = HttpClient(Android) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    })
                }
            })
        }
    }

    //Returning a List of Weapons and not WeaponDtos ( although we will get WeaponDtos from request first)
    suspend fun getWeaponStats(): List<Weapon>
    suspend fun getSkins(weaponName:String): List<Skin>
}