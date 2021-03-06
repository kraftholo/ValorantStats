package com.sk.ui_weaponlist.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.ImageLoader
import com.sk.ui_weaponlist.components.WeaponListItem
import com.sk.core.domain.ProgressBarState

@Composable
fun WeaponList(
    state: WeaponListState,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (String) -> Unit,
) {

    Log.d("WeaponList", "" + state.weapons)
    Column() {

        TopAppBar(
            title = { Text(text = "Weapons") }
        )
        Box(Modifier.fillMaxSize()) {
            LazyColumn {
                items(state.weapons) {
                    WeaponListItem(
                        weapon = it,
                        onSelectWeapon = { weaponUUID ->
                            navigateToDetailScreen(weaponUUID)
                        },
                        imageLoader = imageLoader
                    )
                }
            }

            if (state.progressBarState == ProgressBarState.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

    }
}