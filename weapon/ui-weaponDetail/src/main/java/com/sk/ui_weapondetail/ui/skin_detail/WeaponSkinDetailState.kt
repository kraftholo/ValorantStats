package com.sk.ui_weapondetail.ui.skin_detail

import com.sk.core.domain.ProgressBarState
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin

data class WeaponSkinDetailState(
    val progressBarState : ProgressBarState = ProgressBarState.Idle,
    val skin : Skin? = null,
    val levels: List<Level>? = null
) {
}