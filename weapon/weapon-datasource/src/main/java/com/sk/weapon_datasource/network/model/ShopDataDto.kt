package com.sk.weapon_datasource.network.model

import com.sk.weapon_domain.ShopData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopDataDto(

    @SerialName("cost")
    val cost: Int
) {
    override fun toString(): String {
        return "ShopDataDto(cost=$cost)"
    }
}

fun ShopDataDto.toWeaponShopData(): ShopData {
    return ShopData(
        cost = cost
    )
}