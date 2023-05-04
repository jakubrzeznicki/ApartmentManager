package com.jakubrzeznicki.apartmentmanager.createpin.model

/**
 * Created by jrzeznicki on 15/04/2023.
 */
sealed interface CreatePinStatus {
    object EmptyName : CreatePinStatus
    object NameIsNotUnique : CreatePinStatus
    object PinCreated : CreatePinStatus
    object NoStatus : CreatePinStatus
}