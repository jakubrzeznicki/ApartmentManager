package com.jakubrzeznicki.apartmentmanager.data.apartmentpin

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.ApartmentPinLocalDataSource
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.mapper.toPin
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.mapper.toPinEntity
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Singleton
class ApartmentPinRepository @Inject constructor(private val local: ApartmentPinLocalDataSource) :
    ApartmentPinDataSource {
    override fun getPins(): List<Pin> =
        local.getPins().map { pins -> pins.toPin() }

    override fun getLivePins(): Flowable<List<Pin>> =
        local.getLivePins().map { pins -> pins.map { it.toPin() } }

    override fun createPin(pin: Pin) {
        local.createPin(pin.toPinEntity())
    }

    override fun deletePin(code: String) {
        local.deletePin(code)
    }
}