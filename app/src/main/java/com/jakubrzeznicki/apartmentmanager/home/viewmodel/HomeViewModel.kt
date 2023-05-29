package com.jakubrzeznicki.apartmentmanager.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import com.jakubrzeznicki.apartmentmanager.di.ScreenScope
import com.jakubrzeznicki.apartmentmanager.home.model.DeletePinData
import com.jakubrzeznicki.apartmentmanager.home.model.HomeStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by jrzeznicki on 14/04/2023.
 */

@ScreenScope
class HomeViewModel @Inject constructor(
    private val apartmentPinRepository: ApartmentPinDataSource
) : ViewModel() {
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val viewModelState = MutableStateFlow(HomeViewModelState())
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            viewModelState.value.toUiState()
        )

    fun getPins() {
            val disposable = apartmentPinRepository.getLivePins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { pins ->
                    viewModelState.update {
                        it.copy(pins = pins)
                    }
                }
            compositeDisposable.add(disposable)
//            apartmentPinRepository.getLivePins().collect { pins ->
//                viewModelState.update {
//                    it.copy(pins = pins)
//                }
//            }
    }

    fun deletePin(code: String) {
//        viewModelScope.launch {
//            val status = when (val result = apartmentPinRepository.deletePin(code)) {
//                RepositoryResult.Success -> {
//                    //pobrac dane z bazy
//                    HomeStatus.PinDeleted
//                }
//                is RepositoryResult.Error -> HomeStatus.PinDeleteError(result.errorMessage)
//            }
//            viewModelState.update { it.copy(status = status) }
//        }
        apartmentPinRepository.deletePin(code)
        viewModelState.update { it.copy(status = HomeStatus.PinDeleted) }

    }

    fun showConfirmDeleteDialog(deletePinData: DeletePinData) {
        viewModelState.update { it.copy(deletePinData = deletePinData) }
    }

    fun resetStatus() {
        viewModelState.update { it.copy(status = HomeStatus.NoStatus) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Log.d("TEST_LOG", "onCleared HomeViewModel")
    }
}