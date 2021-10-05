package com.sk.weapon_domain.skin

data class Skin(
    val uuid: String,
    val displayName: String,
    val themeUuid: String,
    val contentTierUuid: String?,
    val displayIcon: String,
    val chromas: List<Chroma>,
    val levels: List<Level>
) {
    override fun toString(): String {
        return "Skin(uuid='$uuid', displayName='$displayName', themeUuid='$themeUuid', contentTierUuid=$contentTierUuid, displayIcon='$displayIcon', chromas=$chromas, levels=$levels)"
    }
}