package com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local

import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.local.model.PinEntity
import io.reactivex.Flowable
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jrzeznicki on 14/04/2023.
 */
@Singleton
class ApartmentPinLocal @Inject constructor(private val realm: Realm) :
    ApartmentPinLocalDataSource {
    override fun getPins(): List<PinEntity> {
        return realm
            .where(PinEntity::class.java)
            .findAllAsync()
        //return realm.query<PinEntity>().sort(NAME).find()
    }

    override fun getLivePins(): Flowable<List<PinEntity>> {
//        return Flowable.defer {
//            Flowable.just(realm.query<PinEntity>().sort(NAME).find())
//        }
        return realm
            .where(PinEntity::class.java)
            .findAllAsync()
            .asFlowable()
            .distinctUntilChanged()
            .map { it }
    }

    override fun createPin(pin: PinEntity) {
        realm.executeTransactionAsync { it.insertOrUpdate(pin) }
    }

    override fun deletePin(code: String) {
        realm.executeTransactionAsync {
            it.where(PinEntity::class.java)
                .equalTo(CODE, code)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    private companion object {
        const val FIRST_PARAMETER = "$0"
        const val CODE = "code"
    }
}