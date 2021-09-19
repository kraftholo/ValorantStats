package com.sk.ui_weapondetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WeaponDetail(
    weaponUUID : String
){
    Text("WeaponUUID is => ${weaponUUID}")
}
