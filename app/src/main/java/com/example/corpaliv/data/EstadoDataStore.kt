package com.example.corpaliv.data
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias")
class EstadoDataStore(private val context: Context) {
    private val clave = booleanPreferencesKey("texto_grande")
    val textoGrande = context.dataStore.data.map { it[clave] ?: false }
    suspend fun guardar(valor: Boolean) { context.dataStore.edit { it[clave] = valor } }
}
