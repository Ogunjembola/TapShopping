package com.example.tapshopping.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.protobuf.Any
import com.example.tapshopping.data.local.DataStoreManager.Companion.ADMIN_LOGIN
import com.example.tapshopping.data.local.DataStoreManager.Companion.EMAIL
import com.example.tapshopping.data.local.DataStoreManager.Companion.FULL_NAME
import com.example.tapshopping.data.local.DataStoreManager.Companion.LOGIN_KEY
import com.example.tapshopping.data.local.DataStoreManager.Companion.TOKEN
import com.example.tapshopping.data.local.DataStoreManager.Companion.USER_ID
import com.example.tapshopping.data.local.DataStoreManager.Companion.USER_NAME
import com.example.tapshopping.data.local.DataStoreManager.Companion.USER_TYPE
import com.example.tapshopping.utillz.get
import com.example.tapshopping.utillz.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DataStoreManagerImpl @Inject constructor(
    var prefDataStore: DataStore<Preferences>
) : DataStoreManager {

    class StringPreference(
        private val dataStore: DataStore<Preferences>,
        private val key: String,
        private val defaultValue: String?
    ) : ReadWriteProperty<DataStoreManagerImpl, String> {
        override fun getValue(thisRef: DataStoreManagerImpl, property: KProperty<*>): String =
            dataStore.get(
                key = stringPreferencesKey(key),
                defaultValue = defaultValue ?: ""
            )

        override fun setValue(
            thisRef: DataStoreManagerImpl,
            property: KProperty<*>,
            value: String
        ) = dataStore.set(
            stringPreferencesKey(key),
            value
        )


    }

    class IntPreference(
        private val dataStore: DataStore<Preferences>,
        private val key: String,
        private val defaultValue: Int
    ) : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return runBlocking {
                dataStore.data.first()[intPreferencesKey(key)] ?: defaultValue
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            return runBlocking {
                dataStore.edit {
                    if (value == 0) {
                        it.remove(intPreferencesKey(key))
                    } else {
                        it[intPreferencesKey(key)] = value
                    }
                }
            }
        }
    }

    override val isAdmin: Flow<Boolean> = prefDataStore.data.map {
        it[ADMIN_LOGIN] ?: false
    }
    override val isLoggedIn: Flow<Boolean> = prefDataStore.data.map {
        it[LOGIN_KEY] ?: false
    }

    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        runBlocking {
            prefDataStore.edit {
                it[LOGIN_KEY] = isLoggedIn
            }
        }
    }

    override suspend fun setIsAdmin(isAdmin: Boolean) {
        prefDataStore.edit {
            it[ADMIN_LOGIN] = isAdmin
        }
    }

    override var userName: String by StringPreference(prefDataStore, USER_NAME, "")
    override var email: String by StringPreference(prefDataStore, EMAIL, "")
    override var fullName: String by StringPreference(prefDataStore, FULL_NAME, "")
    override var token: String by StringPreference(prefDataStore, TOKEN, "")
    override var userId: String by StringPreference(prefDataStore, USER_ID, "")
    override var userType: String by StringPreference(prefDataStore, USER_TYPE, "")
}