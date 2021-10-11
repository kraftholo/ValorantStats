package com.sk.valorantstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.sk.ui_weaponlist.ui.WeaponList
import com.sk.ui_weapondetail.ui.detail.WeaponDetail
import com.sk.ui_weapondetail.ui.skin_detail.WeaponSkinDetail
import com.sk.ui_weapondetail.viewmodels.WeaponDetailViewModel
import com.sk.ui_weapondetail.viewmodels.WeaponSkinDetailViewModel
import com.sk.valorantstats.navigation.Screen
import com.sk.valorantstats.ui.theme.ValorantStatsTheme
import com.sk.viewmodels.WeaponListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ValorantStatsTheme {
                // navigation setup
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Screen.WeaponList.route,
                    builder = {
                        //This will add the two destinations to my navigation graph
                        addWeaponDetail(
                            route = Screen.WeaponDetail.route + "/{weaponUUID}",
                            Screen.WeaponDetail.arguments,
                            navController = navController
                        )

                        addWeaponList(
                            route = Screen.WeaponList.route,
                            arguments = emptyList(),
                            navController = navController
                        )

                        addWeaponSkinDetail(
                            route = Screen.WeaponSkinDetail.route + "/{weaponSkinUUID}",
                            Screen.WeaponSkinDetail.arguments,
                        )
                    }
                )


            }
        }
    }


    //Helper Extension functions to add destinations into the navigation graph
    private fun NavGraphBuilder.addWeaponDetail(
        route: String,
        arguments: List<NamedNavArgument>,
        navController: NavController
    ) {
        return composable(
            route = route,                     //look at the Screen.WeaponDetail object
            arguments = arguments
        ) {
            val viewModel: WeaponDetailViewModel = hiltViewModel()
            WeaponDetail(viewModel.state.value, imageLoader, onSelectSkin = { weaponSkinUuid ->
                navController.navigate("${Screen.WeaponSkinDetail.route}/$weaponSkinUuid")
            })
        }
    }

    private fun NavGraphBuilder.addWeaponList(
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

    private fun NavGraphBuilder.addWeaponSkinDetail(
        route: String,
        arguments: List<NamedNavArgument>
    ) {
        return composable(
            route = route,
            arguments = arguments
        ) {
            val viewModel: WeaponSkinDetailViewModel = hiltViewModel()
            WeaponSkinDetail(state = viewModel.state.value, imageLoader = imageLoader)
        }
    }


}

