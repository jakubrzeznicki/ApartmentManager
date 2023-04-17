package com.jakubrzeznicki.apartmentmanager.createpin.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinStatus
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by jrzeznicki on 15/04/2023.
 */
@HiltViewModel
class CreatePinViewModel @Inject constructor(
    private val apartmentPinRepository: ApartmentPinDataSource
) : ViewModel() {
    private val viewModelState = MutableStateFlow(CreatePinViewModelState())
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            viewModelState.value.toUiState()
        )

    init {
        viewModelState.update {
            val digits = generateRandomDigits()
            it.copy(code = generateCode(digits))
        }
    }

    fun onNameChange(newName: String) {
        viewModelState.update { it.copy(name = newName) }
    }

    fun resetStatus() {
        viewModelState.update { it.copy(status = CreatePinStatus.NoStatus) }
    }

    fun createPin() {
        viewModelScope.launch {
            val name = viewModelState.value.name
            if (name.isBlank()) {
                viewModelState.update { it.copy(status = CreatePinStatus.EmptyName) }
                return@launch
            }
            val pin = Pin(viewModelState.value.code, viewModelState.value.name)
            apartmentPinRepository.createPin(pin)
            viewModelState.update { it.copy(status = CreatePinStatus.PinCreated) }
        }
    }

    @VisibleForTesting
    fun generateCode(digits: List<Int>): String {
        var resultDigits = digits
        while (resultDigits.shouldShuffle()) {
            resultDigits = generateRandomDigits()
        }
        return resultDigits.joinToString(SEPARATOR)
    }

    @VisibleForTesting
    fun generateRandomDigits(): List<Int> {
        val digits = mutableListOf<Int>()
        repeat(6) {
            digits.add((0..9).random())
        }
        return digits
    }

    private fun List<Int>.shouldShuffle(): Boolean = distinct().size <= 3

    private companion object {
        const val SEPARATOR = ""
    }
}