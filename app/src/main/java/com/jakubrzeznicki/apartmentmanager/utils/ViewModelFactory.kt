package com.jakubrzeznicki.apartmentmanager.utils

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by jrzeznicki on 05/05/2023.
 */

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> ComponentActivity.getViewModel(crossinline factory: () -> T) = T::class.java.let {
    ViewModelProvider(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = factory() as T
    }).get(it)
}

inline fun <reified T : ViewModel> viewModel(activity: ComponentActivity, crossinline initializer: () -> T) =
    lazy(LazyThreadSafetyMode.NONE) { activity.getViewModel { initializer() } }