package com.jakubrzeznicki.apartmentmanager.createpin.viewmodel

import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinStatus
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinUiState
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin

/**
 * Created by jrzeznicki on 15/04/2023.
 */
data class CreatePinViewModelState(
    val status: CreatePinStatus = CreatePinStatus.NoStatus,
    val name: String = "",
    val code: String = "",
    val savedPins: List<Pin> = listOf()
) {
    fun toUiState(): CreatePinUiState = CreatePinUiState(status, name, code, savedPins)
}