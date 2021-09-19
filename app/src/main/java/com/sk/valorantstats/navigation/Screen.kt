package com.sk.valorantstats.navigation

import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object WeaponList : Screen(
        route = "weapon_list",
        arguments = emptyList()
    )

    object WeaponDetail : Screen(
        route = "weapon_detail",
        arguments = listOf(navArgument("weaponUUID"){
            type = NavType.StringType
        })
    )

}