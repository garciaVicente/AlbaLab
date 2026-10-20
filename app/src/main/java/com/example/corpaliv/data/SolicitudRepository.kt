package com.example.corpaliv.data
import com.example.corpaliv.data.local.*
import com.example.corpaliv.model.*

// Igual que el ejemplo de notas Room: el Repository conversa con el DAO.
class SolicitudRepository(private val dao: SolicitudDao) {
    val solicitudes = dao.observarSolicitudes()
    val actividades = dao.observarActividades()
    suspend fun guardarSolicitud(s: Solicitud) = dao.insertarSolicitud(
        SolicitudEntity(nombre = s.nombre.trim(), correo = s.correo.trim(),
            organizacion = s.organizacion.trim(), tipo = s.tipo, mensaje = s.mensaje.trim(),
            archivoUri = s.archivoUri, archivoNombre = s.archivoNombre)
    )
    suspend fun guardarActividad(a: Actividad) = dao.insertarActividad(
        ActividadEntity(titulo = a.titulo.trim(), fecha = a.fecha,
            participantes = a.participantes.toInt(), descripcion = a.descripcion.trim(),
            realizada = a.realizada, recordatorio = a.recordatorio)
    )
    suspend fun eliminarSolicitud(s: SolicitudEntity) = dao.eliminarSolicitud(s)
    suspend fun eliminarActividad(a: ActividadEntity) = dao.eliminarActividad(a)
}
