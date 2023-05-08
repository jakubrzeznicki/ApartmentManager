package com.jakubrzeznicki.apartmentmanager.di

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Module
object RealmDatabaseModule {

    @Singleton
    @Provides
    fun provideRealmDatabase(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(REALM_DB_NAME)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    @Singleton
    @Provides
    fun provideRealm(config: RealmConfiguration): Realm {
        return Realm.getInstance(config)
    }

    private const val REALM_DB_NAME = "app_apartment.realm"
}