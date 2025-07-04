package com.jlino.zoodiacochino.screens

import com.jlino.zoodiacochino.models.Pregunta
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jlino.zoodiacochino.FormViewModel

// Función que representa la pantalla del examen
@Composable
fun ExamenScreen(
    navController: NavHostController, // Para navegar a la siguiente pantalla
    viewModel: FormViewModel // ViewModel para compartir datos entre pantallas
) {
    // Lista fija de preguntas con sus opciones y el índice de la respuesta correcta
    val preguntas = listOf(
        Pregunta("¿Cuál es la suma de 2 + 2?", listOf("8", "6", "4", "3"), 2),
        Pregunta("¿Capital de Francia?", listOf("Madrid", "París", "Roma", "Berlín"), 1),
        Pregunta("¿Planeta más cercano al Sol?", listOf("Venus", "Marte", "Mercurio", "Tierra"), 2),
        Pregunta("¿Resultado de 5 x 3?", listOf("15", "10", "25", "13"), 0),
        Pregunta("¿Color del cielo?", listOf("Rojo", "Azul", "Verde", "Amarillo"), 1),
        Pregunta("¿Lenguaje para apps Android?", listOf("Python", "C#", "Kotlin", "Swift"), 2)
    )

    // Lista para guardar la respuesta elegida por el usuario para cada pregunta (-1 si no ha respondido)
    var respuestasUsuario by remember { mutableStateOf(List(preguntas.size) { -1 }) }

    // Contenedor principal tipo scroll vertical que carga preguntas y opciones
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Espaciado general
        verticalArrangement = Arrangement.spacedBy(12.dp) // Separación entre elementos
    ) {

        // Título del examen
        item {
            Text("Examen", style = MaterialTheme.typography.headlineMedium)
        }

        // Por cada pregunta en la lista, se muestra con sus opciones
        itemsIndexed(preguntas) { index, pregunta ->
            Column {
                // Muestra el enunciado
                Text(text = "${index + 1}. ${pregunta.enunciado}")

                // Por cada opción, se genera un RadioButton
                pregunta.opciones.forEachIndexed { i, opcion ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = respuestasUsuario[index] == i, // Se marca si fue la opción elegida
                            onClick = {
                                // Actualiza la respuesta seleccionada
                                respuestasUsuario = respuestasUsuario.toMutableList().also {
                                    it[index] = i
                                }
                            }
                        )
                        Text(opcion) // Texto de la opción
                    }
                }
            }
        }

        // Botón al final del formulario
        item {
            Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del botón

            Button(
                onClick = {
                    // Compara las respuestas del usuario con las correctas y cuenta las correctas
                    val correctas = respuestasUsuario.zip(preguntas).count { (resp, preg) ->
                        resp == preg.respuestaCorrecta
                    }

                    // Calcula la calificación sobre 10
                    val calificacion = correctas * 10 / preguntas.size

                    // Guarda las respuestas y preguntas en el ViewModel para usarlas después
                    viewModel.respuestasUsuario = respuestasUsuario
                    viewModel.preguntas = preguntas

                    // Navega a la pantalla de resultados con la calificación como parámetro
                    val ruta = "resultado/$calificacion"
                    navController.navigate(ruta)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Terminar") // Texto del botón
            }
        }
    }
}