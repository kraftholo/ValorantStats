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

fun SkinDto.toSkin(): Skin {
    return Skin(
        uuid,
        displayName,
        themeUuid,
        contentTierUuid,
        displayIcon,
        chromas.map { it.toChroma() },
        levels.map { it.toLevel() }
    )
}
