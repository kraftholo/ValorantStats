package com.sk.weapon_datasource.network.model.skin

import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.LevelItemType
import com.sk.weapon_domain.skin.getLevelItemTypeFromApiValue
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

    @SerialName("levelItem")
    val levelItemType: String?

) {
    override fun toString(): String {
        return "LevelDto(uuid='$uuid', displayName=$displayName, displayIcon=$displayIcon, streamedVideo=$streamedVideo, levelItemType=$levelItemType)"
    }
}

fun LevelDto.toLevel(): Level {
    return Level(
        uuid,
        displayName,
        displayIcon,
        streamedVideo,
        levelItemType?.let { getLevelItemTypeFromApiValue(levelItemType) } ?: LevelItemType.Unknown
    )
}