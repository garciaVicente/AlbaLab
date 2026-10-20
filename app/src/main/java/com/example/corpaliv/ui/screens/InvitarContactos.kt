package com.example.corpaliv.ui.screens

import android.content.Intent
import android.provider.ContactsContract
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private const val INVITACION = "¡Hola! Te invito a conocer CORPALIV y Alba Lab, " +
        "una iniciativa que promueve la inclusión social y laboral. " +
        "Conoce sus programas y descubre cómo colaborar."

@Composable
fun InvitarContactos() {
    val context = LocalContext.current
    var error by remember { mutableStateOf("") }

    val seleccionarNumero = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        val uri = resultado.data?.data
        if (uri != null) {
            try {
                val numero = context.contentResolver.query(
                    uri,
                    arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER), null, null, null
                )?.use { cursor ->
                    if (cursor.moveToFirst()) cursor.getString(0) else null
                }
                if (numero.isNullOrBlank()) {
                    error = "El contacto seleccionado no tiene número de teléfono."
                } else {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("smsto:${Uri.encode(numero)}")
                        putExtra("sms_body", INVITACION)
                    }
                    context.startActivity(intent)
                    error = ""
                }
            } catch (e: Exception) {
                error = "No se pudo abrir el contacto o la aplicación de mensajes."
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Invita a alguien a conocer nuestra misión.")
        OutlinedButton(onClick = { seleccionarNumero.launch(Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)) }, modifier = Modifier.fillMaxWidth()) {
            Text("Invitar desde mis contactos (SMS)")
        }
        OutlinedButton(onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, INVITACION)
            }
            context.startActivity(Intent.createChooser(intent, "Compartir invitación"))
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Compartir por otra aplicación")
        }
        if (error.isNotEmpty()) Text(error, color = MaterialTheme.colorScheme.error)
    }
}
