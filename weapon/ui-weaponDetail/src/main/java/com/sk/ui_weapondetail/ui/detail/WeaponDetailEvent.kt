package com.sk.ui_weapondetail.ui.detail

sealed class WeaponDetailEvent {

    data class GetCachedWeapon(val weaponUUID: String) : WeaponDetailEvent()

    data class GetSkins(val weaponUUID: String, val weaponName: String) : WeaponDetailEvent()

}