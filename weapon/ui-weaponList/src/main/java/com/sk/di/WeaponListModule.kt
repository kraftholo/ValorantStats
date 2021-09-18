package com.sk.di

import com.sk.weapon_interactors.GetWeapons
import com.sk.weapon_interactors.WeaponInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeaponListModule {

    @Provides
    fun provideGetWeapons(
        interactors: WeaponInteractors
    ): GetWeapons {
        return interactors.getWeapons
    }




}