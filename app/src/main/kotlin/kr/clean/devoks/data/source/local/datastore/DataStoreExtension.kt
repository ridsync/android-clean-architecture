package kr.clean.devoks.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.clean.devoks.core.context.ConstData
import java.io.IOException
import java.lang.Exception

/**
 * Created by devoks
 * Description : DataStore 확장 기능 정의
 */

// since @Singleton scope is used, dataStore will have the same instance every time
val Context.prefAppSetting: DataStore<Preferences> by preferencesDataStore(name = ConstData.PREF_NAME_APP_SETTINGS)
// since @Singleton scope is used, dataStore will have the same instance every time
val Context.prefUserConfig: DataStore<Preferences> by preferencesDataStore(name = ConstData.PREF_NAME_USER_CONFIGS)
//val Context.protoSampleInfo: DataStore<Preferences> by dataStore(
//    fileName = "sample.pb",
//    serializer = SampleSerializer
//)

/***
 * handy function to save key-value pairs in Preference. Sets or updates the value in Preference
 * @param key used to identify the preference
 * @param value the value to be saved in the preference
 */
suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>,
    value: T
) {
    try {
        this.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

/***
 * handy function to return Preference value based on the Preference key
 * @param key  used to identify the preference
 * @param defaultValue value in case the Preference does not exists
 * @throws Exception if there is some error in getting the value
 * @return [Flow] of [T]
 */
fun <T> DataStore<Preferences>.getValueAsFlow(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return this.data.catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            // we try again to store the value in the map operator
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        // return the default value if it doesn't exist in the storage
        preferences[key] ?: defaultValue
    }
}