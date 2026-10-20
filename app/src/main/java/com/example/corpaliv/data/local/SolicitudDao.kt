package com.example.corpaliv.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Query("SELECT * FROM solicitudes ORDER BY fechaRegistro DESC")
    fun observarSolicitudes(): Flow<List<SolicitudEntity>>
    @Insert suspend fun insertarSolicitud(s: SolicitudEntity)
    @Delete suspend fun eliminarSolicitud(s: SolicitudEntity)
    @Query("SELECT * FROM actividades ORDER BY fecha DESC")
    fun observarActividades(): Flow<List<ActividadEntity>>
    @Insert suspend fun insertarActividad(a: ActividadEntity)
    @Delete suspend fun eliminarActividad(a: ActividadEntity)
}
