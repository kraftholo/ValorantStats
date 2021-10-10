package com.sk.weapon_datasource.network.model.skin

import com.sk.weapon_domain.skin.Level
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LevelDto(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("displayName")
    val displayName: String?,

    @SerialName("displayIcon")
    val displayIcon: String?,

    @SerialName("streamedVideo")
    val streamedVideo: String?,

    ) {
    override fun toString(): String {
        return "LevelDto(uuid='$uuid', displayName='$displayName', displayIcon='$displayIcon', streamedVideo='$streamedVideo')"
    }
}

fun LevelDto.toLevel(): Level {
    return Level(
        uuid,
        displayName,
        displayIcon,
        streamedVideo
    )
}