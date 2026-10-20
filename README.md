# CORPALIV – Alba Lab | Evaluación 2 DSY1105

Proyecto Android académico en **Kotlin + Jetpack Compose + Material 3**, basado en los ejemplos del docente: menú lateral, formularios, Room y Estado/DataStore.

## Qué hace

- Inicio, programas, productos, impacto, y formas de colaborar (contenido de demostración).
- **Formulario 1: Solicitud de colaboración** con nombre, correo, tipo, mensaje, consentimiento y validaciones separadas en `model/Solicitud.kt`.
- **Formulario 2: Registro de actividad** con texto, número de participantes (`Int`), selección de fecha con `DatePickerDialog`, `Checkbox` y `Switch`.
- Registros: consultar y eliminar solicitudes y actividades **almacenadas localmente en Room**.
- Contactos: el usuario elige **un contacto concreto** para completar nombre y, si existe, correo. No se copia la agenda completa.
- Archivos: el usuario elige **un PDF o una imagen** desde el selector de documentos. Solo se guarda la URI con permiso de lectura, **no se copia el contenido**. Puede abrirse desde Registros si sigue disponible.
- Preferencias: `DataStore` conserva el switch de texto grande de demostración.
- Animación simple al aparecer el resultado del guardado.

## Abrir en Android Studio

1. Android Studio > Open > carpeta `CORPALIV_AlbaLab_Local`.
2. Esperar sincronización de Gradle y descargar dependencias si es necesario.
3. Ejecutar en emulador API 26+ o dispositivo Android.
4. En **Colaborar** escoger una opción y abrir **Contacto**.
5. Completar los formularios usando solo datos ficticios; consultar **Registros**.

**No ejecutar Python:** se eliminó `Api.kt` y la carpeta `backend`. Room crea la base `corpaliv_local.db` en el almacenamiento privado de la aplicación.

## Estructura y defensa

- `model/Solicitud.kt`: datos del formulario y funciones de validación.
- `data/local/*`: Entity representa tablas; DAO consulta/inserta/elimina; AppDatabase crea Room.
- `data/SolicitudRepository.kt`: conecta DAO con ViewModel, igual que las notas del ejemplo Room.
- `viewmodel/SolicitudViewModel.kt`: operaciones con `viewModelScope` y datos observados con `StateFlow`.
- `data/EstadoDataStore.kt` y `viewmodel/EstadoViewModel.kt`: preferencia persistente, basado en el ejemplo Estado.
- `ui/App.kt`: menú deslizante, barra inferior y navegación manual simple.
- `ui/screens/*`: pantallas Jetpack Compose.

**Recursos nativos**: selector de contactos (`PickContact`) y selector de documentos (`OpenDocument`). Estas acciones usan los selectores del sistema para acceder únicamente a los elementos seleccionados, sin permisos amplios a contactos o almacenamiento.

## Alcance y privacidad

- MVP para evaluación, no producto comercial.
- No usa servidor, sincronización, envío de mensajes, pagos ni cuentas de usuario.
- Guardar localmente **no significa que CORPALIV reciba la solicitud**: el equipo de vinculación no ve registros almacenados en otro dispositivo.
- No ingresar datos personales reales ni archivos sensibles. Historias y productos del catálogo son ilustrativos.
- Para producción haría falta consentimiento y protección de datos, backend con autenticación y publicación autorizada de contenidos.
- El switch de seguimiento de actividad **no programa notificaciones**; solo conserva un indicador en la tabla.
- GitHub público y nombres del equipo deben añadirse por los estudiantes.

## Pruebas manuales sugeridas

1. Nombre `12345` falla; `María Pérez` pasa.
2. Mensaje `1234567890` falla; mensaje con al menos diez caracteres y letras pasa.
3. Contacto: seleccionarlo e inspeccionar campos (algunos contactos carecen de correo).
4. Archivos: elegir PDF, guardar, abrir desde Registros.
5. Guardar actividad con fecha y participantes; reiniciar la app y verificar registros.
6. Cambiar preferencia y reiniciar app.

**Verificación de compilación:** no se ejecutó Android Studio/Gradle en este entorno; realizar Sync y Run para validación final.


## Invitación a contactos
En **Colaborar**, el botón **Invitar desde mis contactos (SMS)** abre el selector de teléfonos de Android y prepara un mensaje en la aplicación de SMS. El usuario decide si lo envía. **Compartir por otra aplicación** permite enviar el mismo texto por una aplicación elegida por el usuario. No se necesita permiso READ_CONTACTS ni SEND_SMS. El mensaje no contiene un enlace de instalación porque no se ha publicado la app en una tienda; se puede añadir cuando exista una URL real.

## Interfaz
Se retiraron textos de carácter académico y el selector para copiar contactos al formulario. La sección de Impacto contiene descripciones generales, no testimonios ni estadísticas atribuidos a personas reales.
