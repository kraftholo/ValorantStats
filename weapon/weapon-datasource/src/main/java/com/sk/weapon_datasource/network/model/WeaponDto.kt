package com.sk.weapon_datasource.network.model

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.getWeaponCategoryFromApiValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponDto(

    @SerialName("uuid")
    val UUID: String,

    @SerialName("displayName")
    val displayName: String,

    @SerialName("category")
    val category: String,

    @SerialName("defaultSkinUuid")
    val defaultSkinUUID: String,

    @SerialName("displayIcon")
    val displayIcon: String,

    @SerialName("killStreamIcon")
    val killStreamIcon: String,

    @SerialName("weaponStats")
    val weaponStats: WeaponStatsDto?
) {
    override fun toString(): String {
        return "WeaponDto(UUID='$UUID', displayName='$displayName', category='$category', defaultSkinUUID='$defaultSkinUUID', displayIcon='$displayIcon', killStreamIcon='$killStreamIcon', weaponStats=$weaponStats)"
    }
}

fun WeaponDto.toWeapon(): Weapon {
    return Weapon(
        UUID = UUID,
        displayName = displayName,
        category = getWeaponCategoryFromApiValue(category),
        defaultSkinUUID = defaultSkinUUID,
        displayIcon = displayIcon,
        killStreamIcon = killStreamIcon,
        weaponStats = weaponStats?.toWeaponStats()
    )
}