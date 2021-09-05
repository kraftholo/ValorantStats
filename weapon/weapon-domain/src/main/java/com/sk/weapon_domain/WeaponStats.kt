package com.sk.weapon_domain


data class WeaponStats(
    val fireRate: Float,
    val magazineSize: Int,
    val runSpeedMultiplier: Float,
    val equipTimeSeconds: Float,
    val reloadTimeSeconds: Float,
    val firstBulletAccuracy: Float,
    val shotgunPelletCount: Int,
    val wallPenetration: WeaponPenetrationType,
    val feature: WeaponStatsFeature,
    val fireMode: WeaponFireModeType,
    val altFireType: WeaponAltFireType,
    val adsStats: WeaponADSStats?,
) {
    override fun toString(): String {
        return "WeaponStats(fireRate=$fireRate, magazineSize=$magazineSize, runSpeedMultiplier=$runSpeedMultiplier, equipTimeSeconds=$equipTimeSeconds, reloadTimeSeconds=$reloadTimeSeconds, firstBulletAccuracy=$firstBulletAccuracy, shotgunPelletCount=$shotgunPelletCount, wallPenetration=$wallPenetration, feature=$feature, fireMode=$fireMode, altFireType=$altFireType, adsStats=$adsStats)"
    }
}