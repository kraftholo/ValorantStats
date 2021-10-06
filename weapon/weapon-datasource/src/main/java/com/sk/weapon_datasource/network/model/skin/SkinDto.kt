package com.sk.weapon_datasource.network.model.skin

import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkinDto(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("displayName")
    val displayName: String,

    @SerialName("themeUuid")
    val themeUuid: String,

    @SerialName("contentTierUuid")
    val contentTierUuid: String?,

    @SerialName("displayIcon")
    val displayIcon: String?,

    @SerialName("chromas")
    val chromas: List<ChromaDto>,

    @SerialName("levels")
    val levels: List<LevelDto>,

    ) {
    override fun toString(): String {
        return "SkinDto(uuid='$uuid', displayName='$displayName', themeUuid='$themeUuid', contentTierUuid='$contentTierUuid', displayIcon='$displayIcon', chromas=$chromas, levels=$levels)"
    }
}

fun SkinDto.toChromas(): List<Chroma> {
    return chromas.map { chromaDto ->
        Chroma(
            uuid = chromaDto.uuid,
            displayName = chromaDto.displayName,
            displayIcon = chromaDto.displayIcon,
            fullRender = chromaDto.fullRender,
            swatch = chromaDto.swatch,
            streamedVideo = chromaDto.streamedVideo
        )
    }

}

fun SkinDto.toLevels(): List<Level> {
    return levels.map { levelDto ->
        Level(
            uuid = levelDto.uuid,
            displayName = levelDto.displayName,
            displayIcon = levelDto.displayIcon,
            streamedVideo = levelDto.streamedVideo
        )
    }
}