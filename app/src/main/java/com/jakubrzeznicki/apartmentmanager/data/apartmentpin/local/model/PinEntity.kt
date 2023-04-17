package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Created by jrzeznicki on 14/04/2023.
 */
class PinEntity : RealmObject {
    @PrimaryKey
    var code: String = ""
    var name: String = ""

    companion object {
        const val CODE = "code"
        const val NAME = "name"
    }
}