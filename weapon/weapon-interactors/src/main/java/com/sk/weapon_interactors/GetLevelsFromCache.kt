package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_domain.skin.Level
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLevelsFromCache(
    val cache: WeaponCache
) {

    fun execute(weaponSkinUuid: String): Flow<DataState<List<Level>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val cachedLevels = cache.getLevelsForSkin(weaponSkinUuid)
            emit(DataState.Data(data = cachedLevels))

        } catch (e: Exception) {
            DataState.Response<List<Level>>(
                uiComponent = UIComponent.Dialog(
                    title = "Caching Error",
                    description = e.message ?: "Unknown"
                )
            )
        }

    }
}