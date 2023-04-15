package com.jakubrzeznicki.apartmentmanager.data.apartmentpin

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by jrzeznicki on 14/04/2023.
 */
interface ApartmentPinDataSource {
    fun getPins(): Flow<List<Pin>>
    suspend fun createPin(pin: Pin)
    suspend fun deletePin(code: String): RepositoryResult
}