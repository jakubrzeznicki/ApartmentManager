package com.jakubrzeznicki.apartmentmanager.createpin.viewmodel

import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinStatus
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinUiState

/**
 * Created by jrzeznicki on 15/04/2023.
 */
data class CreatePinViewModelState(
    val status: CreatePinStatus = CreatePinStatus.NoStatus,
    val name: String = "",
    val code: String = ""
) {
    fun toUiState(): CreatePinUiState = CreatePinUiState(status, name, code)
}