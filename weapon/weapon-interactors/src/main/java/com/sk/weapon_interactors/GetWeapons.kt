package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_datasource.network.WeaponService
import com.sk.weapon_domain.Weapon
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeapons(
    val service: WeaponService,
    val cache : WeaponCache
) {

    fun execute(): Flow<DataState<List<Weapon>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val weapons: List<Weapon> = try {

                delay(1000)

                service.getWeaponStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Weapon>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown"
                        )
                    )
                )
                listOf()
            }

            //insert into the cache
            //then read the cache and display the results ( single source of truth)
            cache.insert(weapons)
            val cachedData = cache.getAllWeapons()
            emit(DataState.Data(cachedData))

        } catch (e: Exception) {
            e.printStackTrace();
            emit(
                DataState.Response<List<Weapon>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message ?: "Unknown"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}