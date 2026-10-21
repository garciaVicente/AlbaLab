package com.example.corpaliv.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corpaliv.viewmodel.SolicitudViewModel

@Composable
fun RegistrosScreen(vm: SolicitudViewModel) {
    val solicitudes by vm.solicitudes.collectAsState()
    val actividades by vm.actividades.collectAsState()
    val context = LocalContext.current
    var busqueda by remember { mutableStateOf("") }
    val solicitudesFiltradas = solicitudes.filter {
        it.nombre.contains(busqueda, ignoreCase = true)
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Registros locales", style = MaterialTheme.typography.headlineSmall)
        Text("Solo se muestran en este celular, no se envían a CORPALIV.")
        Text("Solicitudes (${solicitudesFiltradas.size})",
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar por nombre") },
            placeholder = { Text("Ingrese un nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (solicitudesFiltradas.isEmpty()) {
            Text("No se encontraron solicitudes")
        }

        solicitudesFiltradas.forEach { s ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(s.nombre, style = MaterialTheme.typography.titleMedium)
                    Text("${s.tipo} · ${s.correo}")
                    Text(s.mensaje)
                    if (s.archivoUri.isNotEmpty()) {
                        Text("Archivo: ${s.archivoNombre}")
                        TextButton(onClick = {
                            runCatching {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(s.archivoUri)).apply {
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                })
                            }
                        }) { Text("Abrir archivo") }
                    }
                    TextButton(onClick = { vm.eliminar(s.id) }) { Text("Eliminar solicitud") }
                }
            }
        }
        Text("Actividades (${actividades.size})", style = MaterialTheme.typography.titleLarge)
        actividades.forEach { a ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(a.titulo, style = MaterialTheme.typography.titleMedium)
                    Text("Fecha: ${a.fecha} · Participantes: ${a.participantes}")
                    Text(a.descripcion)
                    Text(if (a.realizada) "Realizada" else "Pendiente")
                    Text(if (a.recordatorio) "Marcada para seguimiento" else "Sin seguimiento")
                    TextButton(onClick = { vm.eliminarActividad(a.id) }) { Text("Eliminar actividad") }
                }
            }
        }
    }
}
