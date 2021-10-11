package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_domain.skin.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSkinFromCache(
    val cache: WeaponCache
) {

    fun execute(weaponSkinUUID: String): Flow<DataState<Skin>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedSkins = cache.getSkinByUUID(weaponSkinUUID)
            emit(DataState.Data(data = cachedSkins))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Skin>(
                    uiComponent = UIComponent.Dialog(
                        title = "Caching Error",
                        description = e.message ?: "Unknown"
                    )
                )
            )
        }
    }


}