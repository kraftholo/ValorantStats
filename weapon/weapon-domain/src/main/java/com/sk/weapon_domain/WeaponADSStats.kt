package com.sk.weapon_domain

data class WeaponADSStats(
    val zoomMultiplier: Float?,
    val fireRate: Float?,
    val runSpeedMultiplier: Float?,
    val burstCount: Int?,
    val firstBulletAccuracy: Float?
) {
    override fun toString(): String {
        return "WeaponADSStats(zoomMultiplier=$zoomMultiplier, fireRate=$fireRate, runSpeedMultiplier=$runSpeedMultiplier, burstCount=$burstCount, firstBulletAccuracy=$firstBulletAccuracy)"
    }
}