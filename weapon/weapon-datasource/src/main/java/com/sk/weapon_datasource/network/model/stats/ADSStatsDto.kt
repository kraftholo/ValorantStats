package com.sk.weapon_datasource.network.model.stats

import com.sk.weapon_domain.stats.WeaponADSStats
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ADSStatsDto(

    @SerialName("zoomMultiplier")
    val zoomMultiplier: Float,

    @SerialName("fireRate")
    val fireRate: Float,

    @SerialName("runSpeedMultiplier")
    val runSpeedMultiplier: Float,

    @SerialName("burstCount")
    val burstCount: Int,

    @SerialName("firstBulletAccuracy")
    val firstBulletAccuracy: Float
) {
    override fun toString(): String {
        return "ADSStatsDto(zoomMultiplier=$zoomMultiplier, fireRate=$fireRate, runSpeedMultiplier=$runSpeedMultiplier, burstCount=$burstCount, firstBulletAccuracy=$firstBulletAccuracy)"
    }
}

fun ADSStatsDto.toADSStats(): WeaponADSStats {
    return WeaponADSStats(
        zoomMultiplier = zoomMultiplier,
        fireRate = fireRate,
        runSpeedMultiplier = runSpeedMultiplier,
        burstCount = burstCount,
        firstBulletAccuracy = firstBulletAccuracy
    )
}