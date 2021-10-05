package com.sk.weapon_domain

data class ShopData(
    val cost: Int
) {
    override fun toString(): String {
        return "ShopData(cost=$cost)"
    }
}