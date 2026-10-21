package com.example.corpaliv.ui

import android.content.Intent
import androidx.core.net.toUri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.corpaliv.model.*
import com.example.corpaliv.ui.screens.FormularioScreen
import com.example.corpaliv.ui.screens.ActividadScreen
import com.example.corpaliv.ui.screens.RegistrosScreen
import com.example.corpaliv.ui.screens.InvitarContactos
import com.example.corpaliv.viewmodel.SolicitudViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.animation.Crossfade
private val secciones = listOf("Inicio", "Programas", "Productos", "Impacto", "Colaborar", "Actividad", "Registros")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val vm: SolicitudViewModel = viewModel()
    var actual by remember { mutableStateOf("Inicio") }
    var tipo by remember { mutableStateOf("Donaciones") }
    val estadoDrawer = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = estadoDrawer, drawerContent = {
        ModalDrawerSheet {
            Text("CORPALIV · Alba Lab", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(20.dp))
            secciones.forEach { opcion ->
                NavigationDrawerItem(label = { Text(opcion) }, selected = actual == opcion,
                    onClick = { actual = opcion; scope.launch { estadoDrawer.close() } },
                    modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
    }) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("CORPALIV · $actual") }, navigationIcon = {
                TextButton(onClick = { scope.launch { estadoDrawer.open() } }) { Text("☰ Menú") }
            })
        }, bottomBar = {
            NavigationBar {
                listOf("Inicio", "Programas", "Colaborar").forEach { item ->
                    NavigationBarItem(selected = actual == item, onClick = { actual = item },
                        icon = { Text(when (item) { "Inicio" -> "\uD83C\uDFE0"; "Programas" -> "\uD83D\uDCCB"; else -> "❤\uFE0F" }) },
                        label = { Text(item) })
                }
            }
        }) { padding ->
            Crossfade(
                targetState = actual,
                label = "pantallas",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) { pantalla ->
                when (pantalla) {
                    "Inicio" -> Inicio(onIr = { actual = it })
                    "Programas" -> Listado("Nuestros programas", DatosContenido.programas)
                    "Productos" -> Listado("Productos y servicios", DatosContenido.productos)
                    "Impacto" -> Impacto()
                    "Colaborar" -> Colaborar(onSeleccion = { tipo = it; actual = "Contacto" })
                    "Contacto" -> FormularioScreen(
                        tipoInicial = tipo,
                        vm = vm,
                        onVolver = { actual = "Colaborar" }
                    )
                    "Actividad" -> ActividadScreen(vm)
                    "Registros" -> RegistrosScreen(vm)
                }
            }
        }
    }
}

@Composable
private fun Contenedor(contenido: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp), content = contenido)
}

@Composable
private fun Bloque(titulo: String, texto: String, extra: String = "", onClick: (() -> Unit)? = null) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(texto)
            if (extra.isNotEmpty()) Text(extra, style = MaterialTheme.typography.bodySmall)
            onClick?.let { TextButton(onClick = it) { Text("Solicitar información ") } }
        }
    }
}

@Composable
private fun Inicio(onIr: (String) -> Unit) = Contenedor {
    Text("Inclusión, autonomía y oportunidades", style = MaterialTheme.typography.headlineMedium)
    Text("Conoce el propósito de CORPALIV y su red sociolaboral Alba Lab.")
    Bloque("¿Quiénes somos?", "CORPALIV es una organización sin fines de lucro que impulsa la autonomía, participación e inclusión social y laboral de personas con discapacidad múltiple.")
    Bloque("Escuela Especial Jan Van Dijk", "Iniciativas educativas y de desarrollo de habilidades.")
    Bloque("Red Sociolaboral Alba Lab", "Formación, preparación para el empleo y vinculación con empresas y colaboradores.")
    Button(onClick = { onIr("Colaborar") }, modifier = Modifier.fillMaxWidth()) { Text("Quiero colaborar") }
    OutlinedButton(onClick = { onIr("Programas") }, modifier = Modifier.fillMaxWidth()) { Text("Ver programas") }
}

@Composable
private fun Listado(titulo: String, datos: List<Contenido>) = Contenedor {
    Text(titulo, style = MaterialTheme.typography.headlineSmall)
    datos.forEach { Bloque(it.titulo, it.descripcion, it.detalle) }
}

@Composable
private fun Impacto() {
    val contexto = LocalContext.current
    Contenedor {
        Text("Historias e impacto social", style = MaterialTheme.typography.headlineSmall)
        DatosContenido.historias.forEach { Bloque(it.titulo, it.descripcion, it.detalle) }
        Bloque("Indicadores", "Los indicadores de resultados deberán incorporarse solo con cifras autorizadas y verificadas. No se publican cifras inventadas.")
        Bloque("Contenido multimedia", "La aplicación puede abrir material audiovisual mediante un enlace.",
            "Encuentra material audiovisual sobre inclusión y accesibilidad.")
        OutlinedButton(onClick = {
            val enlace = "https://www.youtube.com/results?search_query=inclusi%C3%B3n+laboral+accesibilidad".toUri()
            contexto.startActivity(Intent(Intent.ACTION_VIEW, enlace))
        }, modifier = Modifier.fillMaxWidth()) { Text("Explorar videos de inclusión") }
    }
}

@Composable
private fun Colaborar(onSeleccion: (String) -> Unit) = Contenedor {
    Text("Formas de colaborar", style = MaterialTheme.typography.headlineSmall)
    Text("Elige una alternativa y envía una solicitud de contacto.")
    DatosContenido.colaboraciones.forEach { item ->
        Bloque(item.titulo, item.descripcion, item.detalle, onClick = { onSeleccion(item.titulo) })
    }
    HorizontalDivider()
    Text("Invitar contactos", style = MaterialTheme.typography.titleMedium)
    InvitarContactos()
}
