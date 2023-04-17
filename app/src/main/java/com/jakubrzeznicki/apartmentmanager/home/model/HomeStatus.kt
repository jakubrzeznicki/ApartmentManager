package com.jakubrzeznicki.apartmentmanager.home.model

/**
 * Created by jrzeznicki on 14/04/2023.
 */
sealed interface HomeStatus {
    object NoStatus : HomeStatus
    object PinDeleted : HomeStatus
    data class PinDeleteError(val errorMessage: String) : HomeStatus
}