package com.sk.ui_weapondetail.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.core.domain.DataState
import com.sk.ui_weapondetail.ui.skin_detail.WeaponSkinDetailEvent
import com.sk.ui_weapondetail.ui.skin_detail.WeaponSkinDetailState
import com.sk.weapon_interactors.GetLevelsFromCache
import com.sk.weapon_interactors.GetSkinFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeaponSkinDetailViewModel
@Inject
constructor(
    val getLevelsFromCache: GetLevelsFromCache,
    val getSkinFromCache: GetSkinFromCache,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<WeaponSkinDetailState> = mutableStateOf(WeaponSkinDetailState())


    //passed through compose navigation
    init {
        savedStateHandle.get<String>("weaponSkinUUID")?.let {
            onTriggerEvent(WeaponSkinDetailEvent.GetCachedSkin(it))
        }
    }

    fun onTriggerEvent(event: WeaponSkinDetailEvent) {
        when (event) {
            is WeaponSkinDetailEvent.GetCachedSkin -> {
                getSkinFromCache(event.weaponSkinUUID)
            }
            is WeaponSkinDetailEvent.GetCachedLevels -> {
                getLevelsFromCache(event.weaponSkinUUID)
            }
        }
    }


    private fun getSkinFromCache(weaponSkinUuid: String) {
        getSkinFromCache.execute(weaponSkinUuid).onEach { dataState ->

            when (dataState) {

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(skin = dataState.data)
                    Log.d("getSkinFromCache()", "skin = ${state.value.skin}")

                    state.value.skin?.let {
                        onTriggerEvent(WeaponSkinDetailEvent.GetCachedLevels(weaponSkinUuid))
                    }
                }

                is DataState.Response -> {
                    //Todo - Handle Errors
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLevelsFromCache(weaponSkinUuid: String) {
        getLevelsFromCache.execute(weaponSkinUuid).onEach { dataState ->
            when (dataState) {

                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(levels = dataState.data)
                    Log.d("getLevelsFromCache()", "levels = ${state.value.levels}")
                }

                is DataState.Response -> {
                    //Todo - Handle Errors
                }
            }
        }.launchIn(viewModelScope)
    }
}