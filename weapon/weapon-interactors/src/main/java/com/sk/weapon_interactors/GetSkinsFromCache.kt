package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_domain.skin.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSkinsFromCache(
    val cache: WeaponCache
) {

    fun execute(weaponUUID: String): Flow<DataState<List<Skin>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedSkins = cache.getSkinsForWeapon(weaponUUID)
                ?: throw Exception("Weapon Skins not present in Cache!")
            emit(DataState.Data(data = cachedSkins))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Skin>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Caching Error",
                        description = e.message ?: "Unknown"
                    )
                )
            )
        }
    }


}