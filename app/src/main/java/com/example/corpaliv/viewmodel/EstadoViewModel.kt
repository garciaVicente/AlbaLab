package com.example.corpaliv.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.EstadoDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EstadoViewModel(app: Application) : AndroidViewModel(app) {
    private val estado = EstadoDataStore(app)
    val textoGrande = estado.textoGrande.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    fun actualizar(valor: Boolean) { viewModelScope.launch { estado.guardar(valor) } }
}
