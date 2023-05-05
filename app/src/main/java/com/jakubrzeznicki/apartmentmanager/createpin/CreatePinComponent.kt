package com.jakubrzeznicki.apartmentmanager.createpin

import com.jakubrzeznicki.apartmentmanager.MainActivity
import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.di.ViewModelScope
import dagger.Subcomponent

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@ViewModelScope
@Subcomponent
interface CreatePinComponent {
    val createPinViewModel: CreatePinViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): CreatePinComponent
    }

    fun inject(activity: MainActivity)
}