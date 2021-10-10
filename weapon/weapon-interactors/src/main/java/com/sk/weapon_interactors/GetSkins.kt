package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.cache.WeaponCache
import com.sk.weapon_datasource.network.WeaponService
import com.sk.weapon_domain.skin.Skin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class GetSkins(
    val service: WeaponService,
    val cache: WeaponCache
) {
    fun execute(weaponUuid: String,weaponName:String): Flow<DataState<List<Skin>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val skins: List<Skin> = try {
                service.getSkins(weaponName)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Skin>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown"
                        )
                    )
                )
                listOf()
            }
            println("GetSkins.execute(): skins = $skins")
            cache.insertSkins(weaponUuid, skins)
            val cachedData = cache.getSkinsForWeapon(weaponUuid)
            emit(DataState.Data(cachedData))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Skin>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message ?: "Unknown"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}