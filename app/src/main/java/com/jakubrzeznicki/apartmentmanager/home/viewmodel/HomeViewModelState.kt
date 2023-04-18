package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import com.jakubrzeznicki.apartmentmanager.home.model.DeletePinData
import com.jakubrzeznicki.apartmentmanager.home.model.HomeStatus
import com.jakubrzeznicki.apartmentmanager.home.model.HomeUiState

/**
 * Created by jrzeznicki on 14/04/2023.
 */
data class HomeViewModelState(
    val status: HomeStatus = HomeStatus.NoStatus,
    val deletePinData: DeletePinData = DeletePinData(),
    val pins: List<Pin> = emptyList()
) {
    fun toUiState(): HomeUiState = if (pins.isEmpty()) {
        HomeUiState.NoData(status)
    } else {
        HomeUiState.HasPins(status, deletePinData, pins)
    }
}