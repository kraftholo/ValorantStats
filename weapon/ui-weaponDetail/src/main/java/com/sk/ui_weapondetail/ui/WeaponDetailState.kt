package com.sk.ui_weapondetail.ui

import com.sk.core.domain.ProgressBarState
import com.sk.weapon_domain.Weapon

data class WeaponDetailState(
    val progressBarState : ProgressBarState = ProgressBarState.Idle,
    val weapon : Weapon ? = null
)