package com.example.corpaliv.model

data class Contenido(val titulo: String, val descripcion: String, val detalle: String = "")

object DatosContenido {
    val programas = listOf(
        Contenido("Escuela Especial Jan Van Dijk", "Educación y desarrollo de autonomía.", "Programas educativos y de acompañamiento. Consulte con la organización para conocer su oferta vigente."),
        Contenido("Alba Lab", "Formación y preparación para el empleo.", "Iniciativas de capacitación y desarrollo de habilidades laborales."),
        Contenido("Inclusión sociolaboral", "Vínculo entre participantes y empresas.", "Oportunidades de colaboración con empresas interesadas en inclusión laboral.")
    )
    val productos = listOf(
        Contenido("Productos elaborados en talleres", "Consultar productos disponibles", "Productos asociados a iniciativas formativas y productivas. Sin precios ni stock oficiales."),
        Contenido("Servicios y talleres", "Solicitar información", "La disponibilidad y condiciones deben confirmarse directamente con el equipo."),
        Contenido("Capacitaciones y charlas", "Participación empresarial", "Actividades de sensibilización y vinculación. Su existencia concreta se confirma con la organización.")
    )
    val historias = listOf(
        Contenido("Formación para el trabajo", "Desarrollo de habilidades", "Los programas de formación contribuyen a generar oportunidades de participación laboral."),
        Contenido("Colaboración con empresas", "Vinculación e inclusión", "Las alianzas pueden abrir espacios de aprendizaje y participación en el mundo laboral.")
    )
    val colaboraciones = listOf(
        Contenido("Donaciones", "Consultar mecanismos y destino de los aportes", "Los aportes pueden apoyar programas educativos, formativos y de inclusión. El destino específico debe ser informado y validado por CORPALIV."),
        Contenido("Alianzas con empresas", "Iniciativas de responsabilidad social", "Solicite una reunión para revisar posibilidades de cooperación y vinculación."),
        Contenido("Voluntariado", "Manifestar interés en colaborar", "El equipo puede informar necesidades, condiciones y oportunidades vigentes."),
        Contenido("Productos y servicios", "Consultar oferta y disponibilidad", "Solicite información sobre productos y servicios de Alba Lab."),
        Contenido("Capacitaciones", "Consultar actividades formativas", "Contacte al equipo para evaluar actividades adecuadas para su organización.")
    )
}
