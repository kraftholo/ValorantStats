package com.sk.weapon_datasource.network.model

import com.sk.weapon_datasource.network.model.skin.SkinDto
import com.sk.weapon_datasource.network.model.skin.toChromas
import com.sk.weapon_datasource.network.model.skin.toLevels
import com.sk.weapon_datasource.network.model.stats.WeaponStatsDto
import com.sk.weapon_datasource.network.model.stats.toWeaponStats
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.getWeaponCategoryFromApiValue
import com.sk.weapon_domain.skin.Skin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.logging.Logger

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
    val weaponStats: WeaponStatsDto?,

    @SerialName("shopData")
    val shopData: ShopDataDto?,

    @SerialName("skins")
    val skins: List<SkinDto>
) {
    override fun toString(): String {
        return "WeaponDto(UUID='$UUID', displayName='$displayName', category='$category', defaultSkinUUID='$defaultSkinUUID', displayIcon='$displayIcon', killStreamIcon='$killStreamIcon', weaponStats=$weaponStats, shopData=$shopData, skins=$skins)"
    }

}

fun WeaponDto.getSkins(): List<Skin> {
    return skins.map { skinDto ->
        Skin(
            uuid = skinDto.uuid,
            displayName = skinDto.displayName,
            themeUuid = skinDto.themeUuid,
            contentTierUuid = skinDto.contentTierUuid,
            displayIcon = skinDto.displayIcon,
            chromas = skinDto.toChromas(),
            levels = skinDto.toLevels()
        )
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
        weaponStats = weaponStats?.toWeaponStats(),
        shopData = shopData?.toWeaponShopData(),
        skins = getSkins()
    )
}