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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.core.util.Logger
import com.sk.ui_weaponlist.ui.WeaponList
import com.sk.ui_weaponlist.ui.WeaponListState
import com.sk.ui_weapondetail.WeaponDetail
import com.sk.ui_weapondetail.viewmodels.WeaponDetailViewModel
import com.sk.valorantstats.navigation.Screen
import com.sk.valorantstats.ui.theme.ValorantStatsTheme
import com.sk.ui_weaponlist.viewmodels.WeaponListViewModel
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

    private lateinit var imageLoader: ImageLoader

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
                // navigation setup
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Screen.WeaponList.route,
                    builder = {
                        //This will add the two destinations to my navigation graph
                        addWeaponList(
                            route = Screen.WeaponDetail.route + "/{weaponUUID}",
                            Screen.WeaponDetail.arguments
                        )

                        addWeaponDetail(
                            route = Screen.WeaponList.route,
                            arguments = emptyList(),
                            navController = navController
                        )
                    }
                )


            }
        }
    }


    //Helper Extension functions to add destinations into the navigation graph
    private fun NavGraphBuilder.addWeaponList(route: String, arguments: List<NamedNavArgument>) {
        return composable(
            route = route,                     //look at the Screen.WeaponDetail object
            arguments = arguments
        ) {
            val viewModel : WeaponDetailViewModel = hiltViewModel()
            WeaponDetail(viewModel.state.value)
        }
    }

    private fun NavGraphBuilder.addWeaponDetail(
        route: String,
        arguments: List<NamedNavArgument>,
        navController: NavController
    ) {
        return composable(
            route = route
        ) {
            val viewModel: WeaponListViewModel = hiltViewModel()
            WeaponList(viewModel.state.value, imageLoader,
                navigateToDetailScreen = { weaponUUID ->
                    navController.navigate("${Screen.WeaponDetail.route}/$weaponUUID")
                }
            )

        }
    }


}

