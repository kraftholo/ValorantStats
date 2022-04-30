package com.sk.valorantstats.navigation

import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

// All the possible composables of the screens that you can visit in the application
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
        arguments = listOf(navArgument("weaponUUID") {
            type = NavType.StringType
        })
    )

    object WeaponSkinDetail : Screen(
        route = "weapon_skin",
        arguments = listOf(navArgument("weaponSkinUUID") {
            type = NavType.StringType
        })
    )

}