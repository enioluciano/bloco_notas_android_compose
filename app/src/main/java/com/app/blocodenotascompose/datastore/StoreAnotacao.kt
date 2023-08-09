package com.app.blocodenotascompose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreAnotacao(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "confiracoes")
        val ANOTACAO_KEY = stringPreferencesKey(name = "anotacao")
    }

    val getAnotacao: Flow<String> = context.datastore.data
        .map {preferences->
        preferences[ANOTACAO_KEY] ?: ""
    }

    suspend fun salvarAnotacao(anotacao: String){
        context.datastore.edit {preferences ->
            preferences[ANOTACAO_KEY] = anotacao
        }
    }
}