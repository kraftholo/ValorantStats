package com.sk.ui_weapondetail.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.core.domain.DataState
import com.sk.core.util.Logger
import com.sk.ui_weapondetail.ui.WeaponDetailEvent
import com.sk.ui_weapondetail.ui.WeaponDetailState
import com.sk.weapon_interactors.GetSkinsFromCache
import com.sk.weapon_interactors.GetWeaponFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeaponDetailViewModel
@Inject
constructor(
    val getWeaponFromCache: GetWeaponFromCache,
    val getSkinsFromCache: GetSkinsFromCache,
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
                onTriggerEvent(WeaponDetailEvent.GetCachedSkins(it))
            }

    }

    fun onTriggerEvent(event: WeaponDetailEvent) {
        when (event) {
            is WeaponDetailEvent.GetCachedWeapon -> {
                getWeaponFromCache(event.weaponUUID)
            }

            is WeaponDetailEvent.GetCachedSkins -> {
                getSkinsFromCache(event.weaponUUID)
            }
        }
    }

    private fun getSkinsFromCache(weaponUuid : String){
        //logger.log("getSkinsFromCache() : weaponUUID = $weaponUUID")
        getSkinsFromCache.execute(weaponUuid).onEach { dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Data -> {
                    state.value = state.value.copy(skins = dataState.data)
                    Log.d("getSkinsFromCache()","skins = ${state.value.skins}")
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
                    Log.d("getWeaponFromCache()","weapon = ${state.value.weapon}")
                }

                is DataState.Response -> {
                    //Todo - Handle Errors
                }
            }
        }.launchIn(viewModelScope)
    }

}