package com.sk.ui_weapondetail.ui.detail

import com.sk.core.domain.ProgressBarState
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Skin

data class WeaponDetailState(
    val progressBarState : ProgressBarState = ProgressBarState.Idle,
    val weapon : Weapon ? = null,
    val skins : List<Skin>? = null
)