package com.jakubrzeznicki.apartmentmanager.data.apartmentpin

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocalDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.mapper.toPin
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.mapper.toPinEntity
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by jrzeznicki on 14/04/2023.
 */
class ApartmentPinRepository(private val local: ApartmentPinLocalDataSource) :
    ApartmentPinDataSource {
    override suspend fun getPins(): List<Pin> =
        local.getPins().map { pins -> pins.toPin() }

    override fun getLivePins(): Flow<List<Pin>> =
        local.getLivePins().map { pins -> pins.map { it.toPin() } }

    override suspend fun createPin(pin: Pin) {
        local.createPin(pin.toPinEntity())
    }

    override suspend fun deletePin(code: String): RepositoryResult {
        return local.deletePin(code)
    }
}