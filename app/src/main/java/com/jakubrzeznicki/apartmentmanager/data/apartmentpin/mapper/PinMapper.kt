package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.mapper

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.model.Pin

/**
 * Created by jrzeznicki on 14/04/2023.
 */
fun PinEntity.toPin(): Pin = Pin(name = name, code = code)

fun Pin.toPinEntity(): PinEntity =
    PinEntity().also {
        it.name = name
        it.code = code
    }