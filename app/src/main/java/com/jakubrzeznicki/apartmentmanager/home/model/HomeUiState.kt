package com.jakubrzeznicki.apartmentmanager.home.model

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin

/**
 * Created by jrzeznicki on 14/04/2023.
 */
sealed interface HomeUiState {
    val status: HomeStatus

    data class HasPins(
        override val status: HomeStatus,
        val deletePinData: DeletePinData,
        val pins: List<Pin>
    ) : HomeUiState

    data class NoData(override val status: HomeStatus) : HomeUiState
}