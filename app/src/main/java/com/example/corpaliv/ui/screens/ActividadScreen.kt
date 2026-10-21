package com.example.corpaliv.ui.screens

import android.app.DatePickerDialog
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
import java.util.Calendar

@Composable
fun ActividadScreen(vm: SolicitudViewModel) {
    var actividad by remember { mutableStateOf(Actividad()) }
    var errores by remember { mutableStateOf(ErroresActividad()) }
    var mensaje by remember { mutableStateOf("") }
    var guardando by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendario = Calendar.getInstance()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Registrar actividad", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(actividad.titulo,
            { actividad = actividad.copy(titulo = it) },
            label = { Text("Nombre de la actividad *") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (errores.titulo != null) {Text("⚠\uFE0F️") }},
            isError = errores.titulo != null, supportingText = { errores.titulo?.let { Text(it) } })
        OutlinedButton(onClick = {
            DatePickerDialog(context, { _, year, month, day ->
                actividad = actividad.copy(fecha = "%04d-%02d-%02d".format(year, month + 1, day))
            }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show()
        }, modifier = Modifier.fillMaxWidth()) { Text(if (actividad.fecha.isEmpty()) "Seleccionar fecha *" else "Fecha: ${actividad.fecha}") }
        errores.fecha?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        OutlinedTextField(actividad.participantes, { actividad = actividad.copy(participantes = it) },
            label = { Text("Cantidad de participantes *") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (errores.participantes != null) {Text("⚠\uFE0F️") }},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errores.participantes != null,
            supportingText = { errores.participantes?.let { Text(it) } })

        OutlinedTextField(actividad.descripcion, { actividad = actividad.copy(descripcion = it) },
            label = { Text("Descripción *") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (errores.descripcion != null) {Text("⚠\uFE0F️") }},
            isError = errores.descripcion != null,
            supportingText = { errores.descripcion?.let { Text(it) } })
        Row { Checkbox(actividad.realizada, { actividad = actividad.copy(realizada = it) })
            Text("Actividad realizada", Modifier.padding(top = 8.dp)) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Marcar para seguimiento", Modifier.padding(top = 12.dp))
            Switch(actividad.recordatorio, { actividad = actividad.copy(recordatorio = it) })
        }
        Button(enabled = !guardando, onClick = {
            errores = validarActividad(actividad)
            mensaje = ""
            if (errores.valido()) {
                guardando = true
                vm.guardarActividad(actividad) { exito ->
                    guardando = false
                    mensaje = if (exito) "Actividad guardada" else "No se pudo guardar"
                    if (exito) actividad = Actividad()
                }
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Guardar actividad") }
        androidx.compose.animation.AnimatedVisibility(mensaje.isNotEmpty()) { Text(mensaje) }
    }
}
