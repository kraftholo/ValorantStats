package com.sk.weapon_datasource.network

import com.sk.weapon_domain.Weapon
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class WeaponServiceImpl(
    private val httpClient: HttpClient
) : WeaponService {

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


    override suspend fun getWeaponStats(): List<Weapon> {
        return httpClient.get<List<WeaponDto>> {
            url(EndPoints.WEAPONS)
        }.map {
            it.toWeapon()                  //convert Dtos to Domain Models
        }
    }
}