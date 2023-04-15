package com.jakubrzeznicki.apartmentmanager.home.model

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin

/**
 * Created by jrzeznicki on 14/04/2023.
 */
sealed interface HomeUiState {
    val error: HomeError

    data class HasPins(override val error: HomeError, val pins: List<Pin>) : HomeUiState
    data class NoData(override val error: HomeError) : HomeUiState
}