package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by jrzeznicki on 14/04/2023.
 */
interface ApartmentPinLocalDataSource {
    fun getPins(): Flow<List<PinEntity>>
    suspend fun createPin(pin: PinEntity)
    suspend fun deletePin(code: String): RepositoryResult
}