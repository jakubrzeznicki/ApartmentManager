package com.jakubrzeznicki.apartmentmanager.createpin.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.createpin.model.CreatePinStatus
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by jrzeznicki on 15/04/2023.
 */
@ScreenScope
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
        getPins()
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
            val nameIsNotUnique =
                viewModelState.value.savedPins.map { it.name }.contains(viewModelState.value.name)
            if (nameIsNotUnique) {
                viewModelState.update { it.copy(status = CreatePinStatus.NameIsNotUnique) }
                return@launch
            }
            val pin = Pin(viewModelState.value.code, viewModelState.value.name)
            apartmentPinRepository.createPin(pin)
            viewModelState.update { it.copy(status = CreatePinStatus.PinCreated) }
        }
    }

    private fun getPins() {
        viewModelScope.launch {
            val pins = apartmentPinRepository.getPins()
            viewModelState.update { it.copy(savedPins = pins) }
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

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST_LOG", "onCleared CeratePinViewModel")
    }
}