package com.jakubrzeznicki.apartmentmanager.home

import com.jakubrzeznicki.apartmentmanager.MainActivity
import com.jakubrzeznicki.apartmentmanager.di.ViewModelScope
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import dagger.Subcomponent

/**
 * Created by jrzeznicki on 05/05/2023.
 */
@ViewModelScope
@Subcomponent
interface HomeComponent {
    val homeViewModel: HomeViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(activity: MainActivity)
}