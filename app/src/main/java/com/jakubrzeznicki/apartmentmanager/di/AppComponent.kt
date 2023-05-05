package com.jakubrzeznicki.apartmentmanager.di

import android.content.Context
import com.jakubrzeznicki.apartmentmanager.MainActivity
import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 04/05/2023.
 */
@Singleton
@Component(modules = [RealmDatabaseModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    val homeViewModel: HomeViewModel
    val createPinViewModel: CreatePinViewModel
}