package com.sk.ui_weapondetail.ui.skin_detail

sealed class WeaponSkinDetailEvent {

    data class GetCachedSkin(val weaponSkinUUID : String) : WeaponSkinDetailEvent()
    data class GetCachedLevels(val weaponSkinUUID : String) : WeaponSkinDetailEvent()
}