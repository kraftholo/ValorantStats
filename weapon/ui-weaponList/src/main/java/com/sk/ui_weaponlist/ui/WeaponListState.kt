package com.sk.ui_weaponlist.ui

import com.sk.core.domain.ProgressBarState
import com.sk.weapon_domain.Weapon

data class WeaponListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val weapons: List<Weapon> = listOf()
)