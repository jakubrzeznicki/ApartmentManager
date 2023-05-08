package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@RealmClass
open class PinEntity : RealmModel {
    @PrimaryKey
    @Required
    var code: String = ""

    @Required
    var name: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PinEntity

        if (code != other.code) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

//    companion object {
//        const val CODE = "code"
//        const val NAME = "name"
//    }
}