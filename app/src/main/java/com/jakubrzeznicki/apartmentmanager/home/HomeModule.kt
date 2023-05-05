package com.jakubrzeznicki.apartmentmanager.home

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@Module
class HomeModule {

    @Provides
    @ScreenScope
    fun provideViewModel(repository: ApartmentPinDataSource): HomeViewModel {
        return HomeViewModel(repository)
    }
}