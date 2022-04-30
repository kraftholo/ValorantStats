package com.sk.ui_weapondetail.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.core.domain.DataState
import com.sk.ui_weapondetail.ui.detail.WeaponDetailEvent
import com.sk.ui_weapondetail.ui.detail.WeaponDetailState
import com.sk.weapon_interactors.GetSkins
import com.sk.weapon_interactors.GetWeaponFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeaponDetailViewModel
@Inject
constructor(
    val getWeaponFromCache: GetWeaponFromCache,       //Much better way than parcelizing the weapon and passing it whole
    val getSkins: GetSkins,
    val savedStateHandle: SavedStateHandle,
    //val logger: Logger
) : ViewModel() {

    val state: MutableState<WeaponDetailState> = mutableStateOf(WeaponDetailState())

    //All the arguments passed to the destination using the backstack navigation entry are in the savedStateHandle
    //Now we can get the arguments in init itself unlike previously we used to get bundle arguments in "onCreate()"
    init {
        savedStateHandle.get<String>("weaponUUID")
            ?.let {
                onTriggerEvent(WeaponDetailEvent.GetCachedWeapon(it))
            }
    }

    fun onTriggerEvent(event: WeaponDetailEvent) {
        when (event) {
            is WeaponDetailEvent.GetCachedWeapon -> {
                getWeaponFromCache(event.weaponUUID)
            }

            is WeaponDetailEvent.GetSkins -> {
                getSkins(event.weaponUUID, event.weaponName)
            }
        }
    }

    private fun getSkins(weaponUuid: String, weaponName: String) {
        //logger.log("getSkins() : weaponUUID = $weaponUUID")
        getSkins.execute(weaponUuid, weaponName).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(skins = dataState.data)
                    Log.d("getSkins()", "skins = ${state.value.skins}")
                }

                is DataState.Response -> {
                    //Todo - Handle Errors
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWeaponFromCache(uuid: String) {
        //logger.log("getWeaponFromCache() : uuid = $uuid")
        getWeaponFromCache.execute(uuid).onEach { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(weapon = dataState.data)
                    Log.d("getWeaponFromCache()", "weapon = ${state.value.weapon}")
                    state.value.weapon?.let {
                        onTriggerEvent(WeaponDetailEvent.GetSkins(it.UUID, it.displayName))
                    }
                }

                is DataState.Response -> {
                    //Todo - Handle Errors
                }
            }
        }.launchIn(viewModelScope)
    }

}