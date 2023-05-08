package com.jakubrzeznicki.apartmentmanager.createpin

import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import dagger.Subcomponent

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@ScreenScope
@Subcomponent(modules = [CreatePinModule::class])
interface CreatePinComponent {

//    @Subcomponent.Factory
//    interface Factory {
//        fun create(): CreatePinComponent
//    }

    @Subcomponent.Builder
    interface Builder {
        fun build(): CreatePinComponent
    }

    fun getViewModel(): CreatePinViewModel
}