package com.sk.weapon_datasource.network.responses

import com.sk.weapon_datasource.network.model.WeaponDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetWeaponsResponse(

    @SerialName("status")
    val status : Int,

    @SerialName("data")
    val data : List<WeaponDto>
) {
}