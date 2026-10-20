package com.example.corpaliv.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.corpaliv.model.*
import com.example.corpaliv.viewmodel.SolicitudViewModel

@Composable
fun FormularioScreen(tipoInicial: String, vm: SolicitudViewModel, onVolver: () -> Unit) {
    var solicitud by remember(tipoInicial) { mutableStateOf(Solicitud(tipo = tipoInicial)) }
    var errores by remember { mutableStateOf(ErroresSolicitud()) }
    var guardando by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf("") }
    var menu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Storage Access Framework: da acceso solamente al documento elegido.
    val archivo = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                var nombre = "Archivo adjunto"
                context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c ->
                    if (c.moveToFirst()) nombre = c.getString(0) ?: nombre
                }
                solicitud = solicitud.copy(archivoUri = uri.toString(), archivoNombre = nombre)
            }.onFailure { resultado = "No fue posible seleccionar el archivo" }
        }
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Solicitud de colaboración", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(solicitud.nombre,
            { solicitud = solicitud.copy(nombre = it) },
            label = { Text("Nombre *") }, isError = errores.nombre != null,
            trailingIcon = {
                if (errores.nombre != null) {Text("⚠\uFE0F️") }},
            supportingText = { errores.nombre?.let { Text(it) } }, singleLine = true,
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(solicitud.correo,
            { solicitud = solicitud.copy(correo = it) },
            label = { Text("Correo *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errores.correo != null,
            trailingIcon = {if (errores.correo != null) {Text("⚠\uFE0F️")}},
            supportingText = { errores.correo?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth())


        OutlinedTextField(solicitud.organizacion, { solicitud = solicitud.copy(organizacion = it) },
            label = { Text("Organización (opcional)") }, modifier = Modifier.fillMaxWidth())
        Box {
            OutlinedButton(onClick = { menu = true }, modifier = Modifier.fillMaxWidth()) { Text("Tipo: ${solicitud.tipo}") }
            DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                listOf("Donaciones", "Alianzas con empresas", "Voluntariado", "Productos y servicios", "Capacitaciones").forEach { tipo ->
                    DropdownMenuItem(text = { Text(tipo) }, onClick = { solicitud = solicitud.copy(tipo = tipo); menu = false })
                }
            }
        }
        OutlinedTextField(solicitud.mensaje, { solicitud = solicitud.copy(mensaje = it) },
            label = { Text("Mensaje *") },
            minLines = 3, modifier = Modifier.fillMaxWidth(),
            isError = errores.mensaje != null,
            trailingIcon = {if (errores.mensaje != null) {Text("⚠️")}},

            supportingText = { errores.mensaje?.let { Text(it) } })
        OutlinedButton(onClick = { archivo.launch(arrayOf("image/*", "application/pdf")) }, modifier = Modifier.fillMaxWidth()) {
            Text("Seleccionar imagen o PDF")
        }
        if (solicitud.archivoNombre.isNotEmpty()) {
            Text("Seleccionado: ${solicitud.archivoNombre}")
            TextButton(onClick = { solicitud = solicitud.copy(archivoUri = "", archivoNombre = "") }) { Text("Quitar adjunto") }
        }
        Row { Checkbox(solicitud.consentimiento, { solicitud = solicitud.copy(consentimiento = it) })
            Text("Autorizo el almacenamiento de esta solicitud en el dispositivo.", Modifier.padding(top = 8.dp)) }
        errores.consentimiento?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(enabled = !guardando, modifier = Modifier.fillMaxWidth(), onClick = {
            errores = validarSolicitud(solicitud)
            resultado = ""
            if (errores.valido()) {
                guardando = true
                vm.guardar(solicitud) { exito ->
                    guardando = false
                    resultado = if (exito) "Solicitud guardada localmente" else "Error al guardar la solicitud"
                    if (exito) solicitud = Solicitud(tipo = tipoInicial)
                }
            }
        }) { Text(if (guardando) "Guardando..." else "Guardar solicitud") }
        androidx.compose.animation.AnimatedVisibility(resultado.isNotBlank()) {
            Text(resultado, color = MaterialTheme.colorScheme.primary)
        }
        TextButton(onClick = onVolver) { Text("Volver") }
    }
}
