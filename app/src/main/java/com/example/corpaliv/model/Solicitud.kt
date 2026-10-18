package com.example.corpaliv.model

import android.util.Patterns

data class Solicitud(
    val nombre: String = "", val correo: String = "", val organizacion: String = "",
    val tipo: String = "Donaciones", val mensaje: String = "", val archivoUri: String = "",
    val archivoNombre: String = "", val consentimiento: Boolean = false
)
data class ErroresSolicitud(
    val nombre: String? = null, val correo: String? = null,
    val mensaje: String? = null, val consentimiento: String? = null
) {
    fun valido() = nombre == null && correo == null && mensaje == null && consentimiento == null
}
fun validarSolicitud(s: Solicitud) = ErroresSolicitud(
    nombre = when {
        s.nombre.trim().length < 2 -> "Nombre: mínimo 2 letras"
        !s.nombre.trim().all { it.isLetter() || it.isWhitespace() } -> "Solo letras y espacios"
        else -> null
    },
    correo = if (!Patterns.EMAIL_ADDRESS.matcher(s.correo.trim()).matches()) "Correo no válido" else null,
    mensaje = when {
        s.mensaje.trim().length < 10 -> "Mensaje: mínimo 10 caracteres"
        !s.mensaje.any { it.isLetter() } -> "El mensaje debe contener letras"
        else -> null
    },
    consentimiento = if (!s.consentimiento) "Debe aceptar el consentimiento" else null
)

data class Actividad(
    val titulo: String = "", val fecha: String = "", val participantes: String = "",
    val descripcion: String = "", val realizada: Boolean = false, val recordatorio: Boolean = false
)
data class ErroresActividad(
    val titulo: String? = null, val fecha: String? = null,
    val participantes: String? = null, val descripcion: String? = null
) {
    fun valido() = titulo == null && fecha == null && participantes == null && descripcion == null
}
fun validarActividad(a: Actividad): ErroresActividad {
    val numero = a.participantes.toIntOrNull()
    return ErroresActividad(
        titulo = if (a.titulo.trim().length < 3 || !a.titulo.any { it.isLetter() }) "Ingrese un título válido" else null,
        fecha = if (!Regex("\\d{4}-\\d{2}-\\d{2}").matches(a.fecha)) "Seleccione una fecha" else null,
        participantes = if (numero == null || numero !in 1..10000) "Ingrese un número entre 1 y 10000" else null,
        descripcion = if (a.descripcion.trim().length < 5 || !a.descripcion.any { it.isLetter() }) "Ingrese una descripción válida" else null
    )
}
