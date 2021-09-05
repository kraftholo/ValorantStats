package com.sk.weapon_datasource.network.model

import com.sk.weapon_domain.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponStatsDto(

    @SerialName("fireRate")
    val fireRate: Float,

    @SerialName("magazineSize")
    val magazineSize: Int,

    @SerialName("runSpeedMultiplier")
    val runSpeedMultiplier: Float,

    @SerialName("equipTimeSeconds")
    val equipTimeSeconds: Float,

    @SerialName("reloadTimeSeconds")
    val reloadTimeSeconds: Float,

    @SerialName("firstBulletAccuracy")
    val firstBulletAccuracy: Float,

    @SerialName("shotgunPelletCount")
    val shotgunPelletCount: Int,

    @SerialName("wallPenetration")
    val wallPenetration: String,

    @SerialName("feature")
    val feature: String?,

    @SerialName("fireMode")
    val fireMode: String?,

    @SerialName("altFireType")
    val altFireType: String?,

    @SerialName("adsStats")
    val adsStats: ADSStatsDto?,
) {
}

fun WeaponStatsDto.toWeaponStats(): WeaponStats {
    return WeaponStats(
        fireRate = fireRate,
        magazineSize = magazineSize,
        runSpeedMultiplier = runSpeedMultiplier,
        equipTimeSeconds = equipTimeSeconds,
        reloadTimeSeconds = reloadTimeSeconds,
        firstBulletAccuracy = firstBulletAccuracy,
        shotgunPelletCount = shotgunPelletCount,
        wallPenetration = getWeaponPenetrationTypeFromApiValue(wallPenetration),
        feature = getWeaponStatsFeatureFromApiValue(feature),
        fireMode = getWeaponFireModeTypeFromApiValue(fireMode),
        altFireType = getWeaponAltFireTypeFromApiValue(altFireType),
        adsStats = adsStats?.toADSStats()
    )
}