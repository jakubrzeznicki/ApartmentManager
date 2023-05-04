package com.jakubrzeznicki.apartmentmanager.createpin.model

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin

/**
 * Created by jrzeznicki on 15/04/2023.
 */
data class CreatePinUiState(
    val status: CreatePinStatus,
    val name: String,
    val code: String,
    val savedPins: List<Pin>
)