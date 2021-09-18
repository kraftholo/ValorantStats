package com.sk.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sk.core.domain.DataState
import com.sk.core.domain.UIComponent
import com.sk.core.util.Logger
import com.sk.ui.WeaponListEvent
import com.sk.ui.WeaponListState
import com.sk.weapon_interactors.GetWeapons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeaponListViewModel
@Inject
constructor
    (
    val getWeapons: GetWeapons,
    val savedStateHandle: SavedStateHandle,
    val logger: Logger
) : ViewModel() {

    val state: MutableState<WeaponListState> = mutableStateOf(WeaponListState())

    init {
        onTriggerEvent(WeaponListEvent.GetWeaponList)
    }

    fun onTriggerEvent(event: WeaponListEvent) {
        when (event) {
            is WeaponListEvent.GetWeaponList -> {
                getWeaponList()
            }
        }
    }

    private fun getWeaponList() {
        getWeapons.execute().onEach { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }

                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }

                is DataState.Data -> {
                    state.value = state.value.copy(weapons = dataState.data ?: listOf())
                }
            }

        }.launchIn(viewModelScope)
    }

}