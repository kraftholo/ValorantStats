package com.sk.valorantstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.core.util.Logger
import com.sk.ui.WeaponList
import com.sk.ui.WeaponListState
import com.sk.valorantstats.ui.theme.ValorantStatsTheme
import com.sk.weapon_domain.Weapon
import com.sk.weapon_interactors.WeaponInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    //Todo - Ui logic will later be managed by a viewmodel
    private val state : MutableState<WeaponListState>  = mutableStateOf(WeaponListState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val androidDriver = AndroidSqliteDriver(
            WeaponInteractors.schema,
            applicationContext,
            WeaponInteractors.dbName
        )
        val getWeapons = WeaponInteractors.build(androidDriver).getWeapons
        val logger = Logger("GetWeaponsTest")

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

        }.launchIn(CoroutineScope(IO))

        setContent {
            ValorantStatsTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    WeaponList(state.value)
                }
            }
        }
    }
}

