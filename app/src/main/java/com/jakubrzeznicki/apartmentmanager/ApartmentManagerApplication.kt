package com.jakubrzeznicki.apartmentmanager

import android.app.Application
import com.jakubrzeznicki.apartmentmanager.di.AppComponent
import com.jakubrzeznicki.apartmentmanager.di.DaggerAppComponent
import io.realm.Realm

/**
 * Created by jrzeznicki on 14/04/2023.
 */
class ApartmentManagerApplication : Application() {
    val appComponent: AppComponent by lazy {
        initDagger()
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    private fun initDagger(): AppComponent {
        return DaggerAppComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }
}