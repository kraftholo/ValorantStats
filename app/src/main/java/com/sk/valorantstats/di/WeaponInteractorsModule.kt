package com.sk.valorantstats.di

import android.app.Application
import com.sk.weapon_interactors.WeaponInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeaponInteractorsModule {

    @Provides
    @Singleton
    @Named("androidSqlDriver")
    fun provideAndroidSqlDriver(app: Application)
            : SqlDriver {
        return AndroidSqliteDriver(
            WeaponInteractors.schema,
            app,
            WeaponInteractors.dbName
        )
    }

    @Provides
    @Singleton
    fun provideWeaponInteractors(
        @Named("androidSqlDriver") sqlDriver: SqlDriver
    ): WeaponInteractors {

        return WeaponInteractors.build(sqlDriver)
    }
}