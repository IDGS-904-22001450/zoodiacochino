package com.jlino.zoodiacochino.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jlino.zoodiacochino.FormViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.jlino.zoodiacochino.R

@Composable
fun ResultadoScreen(
    navController: NavHostController,             // Para navegar entre pantallas
    calificacion: Int,                            // Calificación pasada desde la pantalla del examen
    viewModel: FormViewModel = viewModel()        // ViewModel que contiene los datos del usuario
) {
    val persona = viewModel.persona               // Obtenemos el objeto Persona desde el ViewModel

    // Validación por si no hay datos del usuario
    if (persona == null) {
        Text("No hay datos del usuario")
        return // Detiene la ejecución de la pantalla si no hay datos
    }

    // Construimos el nombre completo y extraemos el año de nacimiento
    val nombreCompleto = "${persona.nombre} ${persona.apePaterno} ${persona.apeMaterno}"
    val anio = persona.fechaNacimiento.split("/").getOrNull(2)?.toIntOrNull() ?: 2000

    // Calculamos edad y signo zodiacal chino
    val edad = calcularEdad(anio)
    val signo = calcularSignoZodiaco(anio)

    // Diseño de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text("Resultados", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar información personal del usuario
        Text("Hola $nombreCompleto")
        Text("Tienes $edad años")
        Text("Tu signo zodiacal chino es: $signo")

        // Imagen asociada al signo zodiacal
        val imagenId = obtenerImagenPorSigno(signo)
        Image(
            painter = painterResource(id = imagenId),
            contentDescription = "Signo zodiacal chino: $signo",
            modifier = Modifier
                .height(150.dp)
                .padding(top = 16.dp)
        )

        // Mostrar calificación del examen
        Text("Calificación: $calificacion")

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para volver al formulario (inicio)
        Button(onClick = {
            navController.navigate(Screens.Formulario) {
                popUpTo(0) // Elimina el historial para que no se pueda regresar
            }
        }) {
            Text("Volver al inicio")
        }

        // Botón para ver pantalla de retroalimentación
        Button(onClick = {
            navController.navigate(Screens.Feedback)
        }) {
            Text("Ver retroalimentación")
        }
    }
}

fun calcularEdad(anioNacimiento: Int): Int {
    val anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    return anioActual - anioNacimiento
}

fun calcularSignoZodiaco(anio: Int): String {
    val signos = listOf(
        "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
        "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
    )
    return signos[anio % 12]
}


fun obtenerImagenPorSigno(signo: String): Int {
    return when (signo.lowercase()) {
        "rata" -> R.drawable.rata
        "buey" -> R.drawable.guey
        "tigre" -> R.drawable.tigre
        "conejo" -> R.drawable.conejo
        "dragón" -> R.drawable.dragon
        "serpiente" -> R.drawable.serpiente
        "caballo" -> R.drawable.caballo
        "cabra" -> R.drawable.cabra
        "mono" -> R.drawable.mono
        "gallo" -> R.drawable.gallo
        "perro" -> R.drawable.perro
        "cerdo" -> R.drawable.cerdo
        else -> R.drawable.tigre // agrega una imagen por defecto si quieres
    }
}
