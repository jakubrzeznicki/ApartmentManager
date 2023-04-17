package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.home.model.HomeStatus
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
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

    fun getPins() {
        viewModelScope.launch {
            apartmentPinRepository.getPins().collect { pins ->
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

    fun showConfirmDeleteDialog(showDialog: Boolean) {
        viewModelState.update { it.copy(shouldShowDeleteDialog = showDialog) }
    }

    fun resetStatus() {
        viewModelState.update { it.copy(status = HomeStatus.NoStatus) }
    }
}