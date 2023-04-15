package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by jrzeznicki on 14/04/2023.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apartmentPinRepository: ApartmentPinDataSource
) : ViewModel() {
    private val viewModelState = MutableStateFlow(HomeViewModelState())
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            apartmentPinRepository.getPins().collect { pins ->
                viewModelState.update {
                    it.copy(pins = testedPins)
                }
            }
        }
    }

    fun deletePin(code: String) {
        viewModelScope.launch {
            apartmentPinRepository.deletePin(code)
        }
    }

    private companion object {
        val testedPins = listOf<Pin>(
            Pin("3452643", "Do domu"),
            Pin("0204642", "Do pracy"),
            Pin("596532", "Do szkoly"),
        )
    }
}