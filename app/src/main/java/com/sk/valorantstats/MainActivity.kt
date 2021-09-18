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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.core.util.Logger
import com.sk.ui.WeaponList
import com.sk.ui.WeaponListState
import com.sk.valorantstats.ui.theme.ValorantStatsTheme
import com.sk.viewmodels.WeaponListViewModel
import com.sk.weapon_domain.Weapon
import com.sk.weapon_interactors.WeaponInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //Todo - Ui logic will later be managed by a viewmodel

    private lateinit var imageLoader : ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()

        setContent {
            ValorantStatsTheme {
                val viewModel : WeaponListViewModel =  hiltViewModel()
                // A surface container using the 'background' color from the theme
                Surface() {
                    WeaponList(viewModel.state.value,imageLoader)
                }
            }
        }
    }
}

