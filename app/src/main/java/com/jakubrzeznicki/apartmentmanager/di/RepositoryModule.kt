package com.jakubrzeznicki.apartmentmanager.di

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinRepository
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideApartmentPinRepository(realm: Realm): ApartmentPinDataSource {
        val local = ApartmentPinLocal(realm)
        return ApartmentPinRepository(local)
    }
}