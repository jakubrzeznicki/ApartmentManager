package com.jakubrzeznicki.apartmentmanager.di

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinRepository
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocal
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocalDataSource
import dagger.Module
import dagger.Provides
import io.realm.kotlin.Realm
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideApartmentPinLocal(realm: Realm) : ApartmentPinLocalDataSource {
        return ApartmentPinLocal(realm)
    }

    @Singleton
    @Provides
    fun provideApartmentPinRepository(local: ApartmentPinLocalDataSource): ApartmentPinDataSource {
        return ApartmentPinRepository(local)
    }
}