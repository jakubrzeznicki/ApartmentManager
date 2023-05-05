package com.jakubrzeznicki.apartmentmanager.di

import android.content.Context
import com.jakubrzeznicki.apartmentmanager.createpin.CreatePinComponent
import com.jakubrzeznicki.apartmentmanager.home.HomeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 04/05/2023.
 */
@Singleton
@Component(modules = [RealmDatabaseModule::class, RepositoryModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): AppComponent
    }

    fun homeComponent(): HomeComponent.Factory
    fun createPinComponent(): CreatePinComponent.Factory
    //fun inject(activity: MainActivity)
}