package com.jlino.zoodiacochino.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jlino.zoodiacochino.FormViewModel

@Composable
fun FeedbackScreen(
    navController: NavHostController,  // Permite volver al inicio
    viewModel: FormViewModel           // Contiene preguntas y respuestas
) {
    // Traemos las preguntas y respuestas del ViewModel
    val preguntas = viewModel.preguntas
    val respuestas = viewModel.respuestasUsuario

    // Contenedor principal en columna
    Column(
        modifier = Modifier
            .fillMaxSize()             // Ocupa toda la pantalla
            .padding(16.dp)            // Espaciado interno
    ) {
        // Título principal
        Text("Retroalimentación", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Lista scrollable de preguntas con feedback visual
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(preguntas) { index, pregunta ->
                val respuestaUsuario = respuestas.getOrNull(index)      // La respuesta seleccionada
                val correcta = pregunta.respuestaCorrecta               // Índice correcto
                val esCorrecta = respuestaUsuario == correcta           // ¿La respuesta fue correcta?

                // Tarjeta para cada pregunta con fondo verde o rojo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (esCorrecta) Color(0xFFDFF0D8) else Color(0xFFF2DEDE)) // Verde si acierta, rojo si falla
                        .padding(12.dp)
                ) {
                    // Muestra el enunciado de la pregunta
                    Text("${index + 1}. ${pregunta.enunciado}")

                    // Muestra cada opción con un icono según sea la correcta, elegida, etc.
                    pregunta.opciones.forEachIndexed { i, opcion ->
                        val marcado = when {
                            i == respuestaUsuario && i == correcta -> "✅" // Correcta y seleccionada
                            i == respuestaUsuario && i != correcta -> "❌" // Seleccionada pero incorrecta
                            i == correcta -> "✔"                          // Correcta no seleccionada
                            else -> ""                                    // Ningún ícono
                        }
                        Text("$marcado $opcion")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para volver a la pantalla inicial
        Button(
            onClick = {
                navController.navigate(Screens.Formulario) {
                    popUpTo(0) // Limpia el back stack para evitar volver atrás
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Volver al inicio")
        }
    }
}