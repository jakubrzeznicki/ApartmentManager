package com.jakubrzeznicki.apartmentmanager.data.apartmentpin

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import io.reactivex.Flowable

/**
 * Created by jrzeznicki on 14/04/2023.
 */
interface ApartmentPinDataSource {
    fun getPins(): List<Pin>
    fun getLivePins(): Flowable<List<Pin>>
    fun createPin(pin: Pin)
    fun deletePin(code: String)
}