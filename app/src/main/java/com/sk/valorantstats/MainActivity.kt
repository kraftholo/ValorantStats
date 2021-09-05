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
import com.sk.valorantstats.ui.theme.ValorantStatsTheme
import com.sk.weapon_domain.Weapon
import com.sk.weapon_interactors.WeaponInteractors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weapons: MutableState<List<Weapon>> = mutableStateOf(listOf())
        val progressBarState: MutableState<ProgressBarState> = mutableStateOf(ProgressBarState.Idle)

        val getWeapons = WeaponInteractors.build().getWeapons
        val logger = Logger("GetWeaponsTest")

        getWeapons.execute().onEach { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    progressBarState.value = dataState.progressBarState
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
                    weapons.value = dataState.data ?: listOf()
                }
            }

        }.launchIn(CoroutineScope(IO))

        setContent {
            ValorantStatsTheme {
                // A surface container using the 'background' color from the theme
                Surface() {

                    Box(Modifier.fillMaxSize()) {
                        LazyColumn {
                            items(weapons.value) {
                                Text(it.displayName)
                            }
                        }

                        if (progressBarState.value == ProgressBarState.Loading) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    }

                }
            }
        }
    }
}

