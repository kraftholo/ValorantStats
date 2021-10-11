package com.sk.ui_weapondetail.di

import com.sk.core.util.Logger
import com.sk.weapon_interactors.*
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

    @Provides
    @Singleton
    fun provideGetSkins(
        interactors: WeaponInteractors
    ): GetSkins {
        return interactors.getSkins
    }

    @Provides
    @Singleton
    fun provideGetSkinFromCache(
        interactors: WeaponInteractors
    ): GetSkinFromCache {
        return interactors.getSkinFromCache
    }

    @Provides
    @Singleton
    fun provideGetLevelsFromCache(
        interactors: WeaponInteractors
    ): GetLevelsFromCache {
        return interactors.getLevelsFromCache
    }

//    @Provides
//    fun provideLogger() : Logger {
//        return Logger("WeaponDetailModule")
//    }
}