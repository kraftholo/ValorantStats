package com.sk.weapon_datasource.network.responses

import com.sk.weapon_datasource.network.model.skin.SkinDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSkinsResponse(

    @SerialName("status")
    val status : Int,

    @SerialName("data")
    val data: List<SkinDto>
) {
}