package com.sk.weapon_datasource.network.model.skin

import com.sk.weapon_domain.skin.Skin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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

    @Transient
    val hasLevels : Boolean = false

    ) {
    override fun toString(): String {
        return "SkinDto(uuid='$uuid', displayName='$displayName', themeUuid='$themeUuid', contentTierUuid=$contentTierUuid, displayIcon=$displayIcon, chromas=$chromas, levels=$levels, hasLevels=$hasLevels)"
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
        levels.map { it.toLevel() },
        hasLevels = levels.size > 1 .apply { println("shobhit: hasLevels = ${levels.size}") }
    )
}
