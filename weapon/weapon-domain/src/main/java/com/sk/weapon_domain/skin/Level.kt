package com.sk.weapon_domain.skin

data class Level(
    val uuid: String,
    val displayName: String?,
    val displayIcon: String?,
    val streamedVideo: String?
) {
    override fun toString(): String {
        return "Level(uuid='$uuid', displayName='$displayName', displayIcon='$displayIcon', streamedVideo=$streamedVideo)"
    }
}