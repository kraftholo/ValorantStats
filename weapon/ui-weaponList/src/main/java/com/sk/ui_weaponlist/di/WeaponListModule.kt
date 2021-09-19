package com.sk.ui_weaponlist.di

import com.sk.core.util.Logger
import com.sk.weapon_interactors.GetWeapons
import com.sk.weapon_interactors.WeaponInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeaponListModule {

    @Provides
    @Singleton
    fun provideGetWeapons(
        interactors: WeaponInteractors
    ): GetWeapons {
        return interactors.getWeapons
    }

//    @Provides
//    fun provideLogger(): Logger {
//        return Logger("WeaponListModule")
//    }
}