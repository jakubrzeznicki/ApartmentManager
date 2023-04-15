package com.jakubrzeznicki.apartmentmanager.home.model

/**
 * Created by jrzeznicki on 14/04/2023.
 */
sealed interface HomeError {
    object NoError : HomeError
    data class DeleteError(val errorMessage: String) : HomeError
}