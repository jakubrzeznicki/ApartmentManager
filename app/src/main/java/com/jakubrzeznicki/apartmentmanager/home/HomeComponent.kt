package com.jakubrzeznicki.apartmentmanager.home

import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import dagger.Subcomponent

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@ScreenScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun getViewModel(): HomeViewModel
}