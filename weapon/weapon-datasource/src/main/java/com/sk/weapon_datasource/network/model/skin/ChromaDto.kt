package com.sk.weapon_datasource.network.model.skin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChromaDto(
    @SerialName("uuid")
    val uuid: String,

    @SerialName("displayName")
    val displayName: String,

    @SerialName("displayIcon")
    val displayIcon: String?,

    @SerialName("fullRender")
    val fullRender: String,

    @SerialName("swatch")
    val swatch: String?,

    @SerialName("streamedVideo")
    val streamedVideo: String?,
) {
    override fun toString(): String {
        return "ChromaDto(uuid='$uuid', displayName='$displayName', displayIcon='$displayIcon', fullRender='$fullRender', swatch='$swatch', streamedVideo='$streamedVideo')"
    }
}