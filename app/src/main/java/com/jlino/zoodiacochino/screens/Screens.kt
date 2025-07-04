package com.jlino.zoodiacochino.screens


object Screens {
    // Ruta para la pantalla del formulario
    const val Formulario = "formulario"

    // Ruta para la pantalla del examen
    const val Examen = "examen"

    // Ruta dinámica para la pantalla de resultados
    // Usa un parámetro {calificacion} que se reemplazará en tiempo de ejecución
    const val Resultado = "resultado/{calificacion}"

    // Ruta para la pantalla de retroalimentación (feedback)
    const val Feedback = "feedback"

    // Función de ayuda que construye la ruta con el valor de calificación real
    // Evita errores al concatenar manualmente strings
    fun resultadoRoute(calificacion: Int): String = "resultado/$calificacion"

}
