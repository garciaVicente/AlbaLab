package com.example.corpaliv.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.SolicitudRepository
import com.example.corpaliv.data.local.AppDatabase
import com.example.corpaliv.model.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SolicitudViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SolicitudRepository(AppDatabase.obtener(app).solicitudDao())
    val solicitudes = repo.solicitudes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val actividades = repo.actividades.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun guardar(s: Solicitud, terminado: (Boolean) -> Unit) {
        viewModelScope.launch {
            val ok = runCatching { repo.guardarSolicitud(s) }.isSuccess
            terminado(ok)
        }
    }
    fun guardarActividad(a: Actividad, terminado: (Boolean) -> Unit) {
        viewModelScope.launch {
            val ok = runCatching { repo.guardarActividad(a) }.isSuccess
            terminado(ok)
        }
    }
    fun eliminar(id: Int) {
        val solicitud = solicitudes.value.firstOrNull { it.id == id } ?: return
        viewModelScope.launch { repo.eliminarSolicitud(solicitud) }
    }
    fun eliminarActividad(id: Int) {
        val actividad = actividades.value.firstOrNull { it.id == id } ?: return
        viewModelScope.launch { repo.eliminarActividad(actividad) }
    }
}
