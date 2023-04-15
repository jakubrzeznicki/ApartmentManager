package com.jakubrzeznicki.apartmentmanager.utils

/**
 * Created by jrzeznicki on 14/04/2023.
 */
sealed interface RepositoryResult {
    object Success : RepositoryResult
    class Error(val errorMessage: String) : RepositoryResult
}