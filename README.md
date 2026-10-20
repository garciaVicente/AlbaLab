# CORPALIV – Alba Lab | Evaluación 2 DSY1105

Proyecto Android académico en **Kotlin + Jetpack Compose + Material 3**.

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


## Alcance y privacidad

- MVP para evaluación, no producto comercial.
- No usa servidor, sincronización, envío de mensajes, pagos ni cuentas de usuario.
- Guardar localmente **no significa que CORPALIV reciba la solicitud**: el equipo de vinculación no ve registros almacenados en otro dispositivo.
- No ingresar datos personales reales ni archivos sensibles. Historias y productos del catálogo son ilustrativos.
- Para producción haría falta consentimiento y protección de datos, backend con autenticación y publicación autorizada de contenidos.
- El switch de seguimiento de actividad **no programa notificaciones**; solo conserva un indicador en la tabla.
- GitHub público y nombres del equipo deben añadirse por los estudiantes.



