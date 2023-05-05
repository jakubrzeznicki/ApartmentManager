package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.home.model.DeletePinData
import com.jakubrzeznicki.apartmentmanager.home.model.HomeStatus
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by jrzeznicki on 14/04/2023.
 */

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

    fun getPins() {
        viewModelScope.launch {
            apartmentPinRepository.getLivePins().collect { pins ->
                viewModelState.update {
                    it.copy(pins = pins)
                }
            }
        }
    }

    fun deletePin(code: String) {
        viewModelScope.launch {
            val status = when (val result = apartmentPinRepository.deletePin(code)) {
                RepositoryResult.Success -> HomeStatus.PinDeleted
                is RepositoryResult.Error -> HomeStatus.PinDeleteError(result.errorMessage)
            }
            viewModelState.update { it.copy(status = status) }
        }
    }

    fun showConfirmDeleteDialog(deletePinData: DeletePinData) {
        viewModelState.update { it.copy(deletePinData = deletePinData) }
    }

    fun resetStatus() {
        viewModelState.update { it.copy(status = HomeStatus.NoStatus) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST_LOG", "onCleared HomeViewModel")
    }
}