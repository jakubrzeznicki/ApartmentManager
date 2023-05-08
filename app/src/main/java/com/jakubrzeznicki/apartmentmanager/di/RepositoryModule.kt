package com.jakubrzeznicki.apartmentmanager.di

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinRepository
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocal
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocalDataSource
import dagger.Binds
import dagger.Module

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideApartmentPinLocal(local: ApartmentPinLocal): ApartmentPinLocalDataSource

    @Binds
    abstract fun provideApartmentPinRepository(repository: ApartmentPinRepository): ApartmentPinDataSource
}

//@Module
//object RepositoryModule {
//
//    @Singleton
//    @Provides
//    fun provideApartmentPinLocal(realm: Realm) : ApartmentPinLocalDataSource {
//        return ApartmentPinLocal(realm)
//    }
//
//    @Singleton
//    @Provides
//    fun provideApartmentPinRepository(local: ApartmentPinLocalDataSource): ApartmentPinDataSource {
//        return ApartmentPinRepository(local)
//    }
//}