package com.sk.weapon_domain.skin

data class Chroma(
    val uuid: String,
    val displayName: String,
    val displayIcon: String?,
    val fullRender: String,
    val swatch: String?,
    val streamedVideo: String?
) {
    override fun toString(): String {
        return "Chroma(uuid='$uuid', displayName='$displayName', displayIcon='$displayIcon', fullRender='$fullRender', swatch=$swatch, streamedVideo=$streamedVideo)"
    }
}