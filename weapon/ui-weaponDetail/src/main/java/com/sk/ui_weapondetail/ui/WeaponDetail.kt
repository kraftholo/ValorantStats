package com.sk.ui_weapondetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.sk.ui_weapondetail.ui.WeaponDetailState

@Composable
fun WeaponDetail(
    state: WeaponDetailState
) {
    Text("WeaponUUID is => ${state.weapon?.UUID ?: "Loading...."}")
}
