package com.jakubrzeznicki.apartmentmanager.createpin

import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import dagger.Module
import dagger.Provides

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@Module
class CreatePinModule {

    @Provides
    @ScreenScope
    fun provideViewModel(repository: ApartmentPinDataSource): CreatePinViewModel {
        return CreatePinViewModel(repository)
    }
}