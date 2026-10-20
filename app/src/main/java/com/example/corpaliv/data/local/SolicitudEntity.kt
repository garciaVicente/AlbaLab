package com.example.corpaliv.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String, val correo: String, val organizacion: String,
    val tipo: String, val mensaje: String, val archivoUri: String,
    val archivoNombre: String, val fechaRegistro: Long = System.currentTimeMillis()
)

@Entity(tableName = "actividades")
data class ActividadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String, val fecha: String, val participantes: Int,
    val descripcion: String, val realizada: Boolean, val recordatorio: Boolean
)
