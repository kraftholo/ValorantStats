package com.sk.weapon_interactors

import com.sk.core.domain.DataState
import com.sk.core.domain.ProgressBarState
import com.sk.core.domain.UIComponent
import com.sk.weapon_datasource.network.WeaponService
import com.sk.weapon_domain.Weapon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeapons(
    val service: WeaponService
) {

    fun execute(): Flow<DataState<List<Weapon>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val weapons: List<Weapon> = try {
                service.getWeaponStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Weapon>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown"
                        )
                    )
                )
                listOf()
            }

            emit(DataState.Data(weapons))

        } catch (e: Exception) {
            e.printStackTrace();
            emit(
                DataState.Response<List<Weapon>>(
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