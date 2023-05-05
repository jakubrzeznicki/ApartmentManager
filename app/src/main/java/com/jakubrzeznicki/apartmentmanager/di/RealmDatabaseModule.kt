package com.jakubrzeznicki.apartmentmanager.di

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import dagger.Module
import dagger.Provides
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Module
object RealmDatabaseModule {

    @Singleton
    @Provides
    fun provideRealmDatabase(): Realm {
        val configuration = RealmConfiguration.Builder(schema = setOf(PinEntity::class))
            .compactOnLaunch()
            .build()
        return Realm.open(configuration)
    }
}