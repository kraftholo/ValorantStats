package com.sk.ui_weapondetail.di

import com.sk.core.util.Logger
import com.sk.weapon_interactors.GetWeaponFromCache
import com.sk.weapon_interactors.WeaponInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeaponDetailModule {

    @Provides
    @Singleton
    fun provideGetWeaponFromCache(
        interactors: WeaponInteractors
    ): GetWeaponFromCache {
        return interactors.getWeaponsFromCache
    }

//    @Provides
//    fun provideLogger() : Logger {
//        return Logger("WeaponDetailModule")
//    }
}