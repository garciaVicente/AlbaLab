package com.example.corpaliv.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv.viewmodel.EstadoViewModel

@Composable
fun PreferenciasScreen(vm: EstadoViewModel) {
    val grande by vm.textoGrande.collectAsState()
    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Preferencias", style = MaterialTheme.typography.headlineSmall)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Texto grande", modifier = Modifier.weight(1f))
            Switch(checked = grande, onCheckedChange = vm::actualizar)
        }
        Text("Inclusión y oportunidades", style = if (grande) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.bodyLarge)
    }
}
