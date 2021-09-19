package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_domain.Weapon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeaponFromCache(
    val cache: WeaponCache
) {

    fun execute(uuid: String): Flow<DataState<Weapon>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedWeapon = cache.getWeaponByUUID(uuid)
                ?: throw Exception("Weapon was not present in cache!")

            emit(DataState.Data(data = cachedWeapon))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Weapon>(
                    uiComponent = UIComponent.Dialog(
                        title = "Caching Error",
                        description = e.message ?: "Unknown"
                    )
                )
            )

        }
    }

}