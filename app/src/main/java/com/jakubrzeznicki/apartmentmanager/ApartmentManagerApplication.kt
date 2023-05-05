package com.jakubrzeznicki.apartmentmanager

import android.app.Application
import com.jakubrzeznicki.apartmentmanager.di.AppComponent
import com.jakubrzeznicki.apartmentmanager.di.DaggerAppComponent

/**
 * Created by jrzeznicki on 14/04/2023.
 */
class ApartmentManagerApplication : Application() {
    val appComponent: AppComponent by lazy {
        initDagger()
    }

    private fun initDagger(): AppComponent {
        return DaggerAppComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }
}