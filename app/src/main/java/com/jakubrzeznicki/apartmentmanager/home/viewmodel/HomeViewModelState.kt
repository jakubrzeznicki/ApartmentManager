package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import com.jakubrzeznicki.apartmentmanager.home.model.HomeError
import com.jakubrzeznicki.apartmentmanager.home.model.HomeUiState

/**
 * Created by jrzeznicki on 14/04/2023.
 */
data class HomeViewModelState(
    val error: HomeError = HomeError.NoError,
    val pins: List<Pin> = emptyList()
) {
    fun toUiState(): HomeUiState = if (pins.isEmpty()) {
        HomeUiState.NoData(error)
    } else {
        HomeUiState.HasPins(error, pins)
    }
}