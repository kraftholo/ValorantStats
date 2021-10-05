package com.sk.weapon_domain

import com.sk.weapon_domain.skin.Skin
import com.sk.weapon_domain.stats.WeaponStats

data class Weapon(

    val UUID: String,
    val displayName: String,
    val category: WeaponCategory,
    val defaultSkinUUID: String,
    val displayIcon: String,
    val killStreamIcon: String,
    val weaponStats: WeaponStats?,
    val shopData : ShopData,
    val skins : List<Skin>

    //altShotgunStats,airBurstStats,damageRanges,skins left

) {
    override fun toString(): String {
        return "Weapon(UUID='$UUID', displayName='$displayName', category=$category, defaultSkinUUID='$defaultSkinUUID', displayIcon='$displayIcon', killStreamIcon='$killStreamIcon', weaponStats=$weaponStats, shopData=$shopData, skins=$skins)"
    }
}