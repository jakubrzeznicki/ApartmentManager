package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity.Companion.CODE
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity.Companion.NAME
import com.jakubrzeznicki.apartmentmanager.utils.RepositoryResult
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by jrzeznicki on 14/04/2023.
 */
class ApartmentPinLocal(private val realm: Realm) : ApartmentPinLocalDataSource {
    override fun getPins(): List<PinEntity> {
        return realm.query<PinEntity>().sort(NAME).find()
    }

    override fun getLivePins(): Flow<List<PinEntity>> {
        return realm.query<PinEntity>().sort(NAME).asFlow().map { it.list }
    }

    override suspend fun createPin(pin: PinEntity) {
        realm.write { copyToRealm(pin) }
    }

    override suspend fun deletePin(code: String): RepositoryResult {
        return realm.write {
            val pin = query<PinEntity>(query = "$CODE == $FIRST_PARAMETER", code).first().find()
            try {
                pin?.let { delete(it) }
                RepositoryResult.Success
            } catch (e: Exception) {
                RepositoryResult.Error(e.message.orEmpty())
            }
        }
    }

    private companion object {
        const val FIRST_PARAMETER = "$0"
    }
}