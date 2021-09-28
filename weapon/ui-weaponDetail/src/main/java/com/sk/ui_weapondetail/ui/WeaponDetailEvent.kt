package com.sk.ui_weapondetail.ui

sealed class WeaponDetailEvent {

    data class GetCachedWeapon(val weaponUUID: String) : WeaponDetailEvent()

}