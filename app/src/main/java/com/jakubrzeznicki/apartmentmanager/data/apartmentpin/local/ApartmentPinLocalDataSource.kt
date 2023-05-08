package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import io.reactivex.Flowable

/**
 * Created by jrzeznicki on 14/04/2023.
 */
interface ApartmentPinLocalDataSource {
    fun getPins(): List<PinEntity>
    fun getLivePins(): Flowable<List<PinEntity>>
    fun createPin(pin: PinEntity)
    fun deletePin(code: String)
}