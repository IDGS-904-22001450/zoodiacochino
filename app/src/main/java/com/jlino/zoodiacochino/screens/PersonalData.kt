package com.jlino.zoodiacochino.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jlino.zoodiacochino.FormViewModel
import com.jlino.zoodiacochino.models.Persona
import java.util.Calendar


// Función composable que representa la pantalla del formulario
@Composable
fun FormularioScreen(
    navController: NavHostController, // Controlador para navegar entre pantallas
    viewModel: FormViewModel // ViewModel que guarda la información del usuario
) {
    // Estados que guardan los valores ingresados por el usuario
    var nombre by remember { mutableStateOf("") }
    var apePaterno by remember { mutableStateOf("") }
    var apeMaterno by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Masculino") } // Valor por defecto

    // Estado para mostrar mensajes de error en el formulario
    var mensajeError by remember { mutableStateOf<String?>(null) }

    // Estructura visual principal de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .padding(20.dp), // Margen interno alrededor del contenido
        verticalArrangement = Arrangement.Center, // Centrado vertical del contenido
        horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
    ) {
        // Título del formulario
        Text("Formulario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical

        // Campos de texto para nombre y apellidos
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        TextField(value = apePaterno, onValueChange = { apePaterno = it }, label = { Text("Apellido Paterno") })
        TextField(value = apeMaterno, onValueChange = { apeMaterno = it }, label = { Text("Apellido Materno") })

        Spacer(modifier = Modifier.height(8.dp))

        // Fila que contiene los campos numéricos de la fecha de nacimiento
        Row {
            TextField(
                value = dia,
                onValueChange = { dia = it.filter { c -> c.isDigit() } }, // Solo permite números
                label = { Text("Día") },
                modifier = Modifier.weight(1f) // Distribución equitativa del espacio
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = mes,
                onValueChange = { mes = it.filter { c -> c.isDigit() } },
                label = { Text("Mes") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = anio,
                onValueChange = { anio = it.filter { c -> c.isDigit() } },
                label = { Text("Año") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selección de sexo con radio buttons
        Text("Sexo:")
        Row {
            RadioButton(selected = sexo == "Masculino", onClick = { sexo = "Masculino" })
            Text("Masculino", modifier = Modifier.padding(end = 16.dp))
            RadioButton(selected = sexo == "Femenino", onClick = { sexo = "Femenino" })
            Text("Femenino")
        }

        // Si hay un mensaje de error, lo mostramos debajo del formulario
        mensajeError?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error) // Estilo en rojo
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botones de limpiar y siguiente
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            // Botón que limpia todos los campos del formulario
            Button(onClick = {
                nombre = ""
                apePaterno = ""
                apeMaterno = ""
                dia = ""
                mes = ""
                anio = ""
                sexo = "Masculino"
                mensajeError = null
            }) {
                Text("Limpiar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Botón que valida los campos y navega si todo está bien
            Button(onClick = {
                // Convertimos las fechas a enteros para validación
                val diaInt = dia.toIntOrNull()
                val mesInt = mes.toIntOrNull()
                val anioInt = anio.toIntOrNull()

                // Bloques de validación con mensajes claros
                if (nombre.isBlank() || apePaterno.isBlank() || apeMaterno.isBlank()) {
                    mensajeError = "Todos los nombres y apellidos son obligatorios."
                } else if (diaInt == null || diaInt !in 1..31) {
                    mensajeError = "El día debe ser un número entre 1 y 31."
                } else if (mesInt == null || mesInt !in 1..12) {
                    mensajeError = "El mes debe ser un número entre 1 y 12."
                } else if (anioInt == null || anioInt !in 1900..Calendar.getInstance().get(Calendar.YEAR)) {
                    mensajeError = "El año debe ser válido y no mayor al actual."
                } else {
                    // Si todo es válido, limpiamos error, creamos objeto Persona y navegamos
                    mensajeError = null
                    val persona = Persona(
                        nombre = nombre,
                        apePaterno = apePaterno,
                        apeMaterno = apeMaterno,
                        fechaNacimiento = "$dia/$mes/$anio",
                        sexo = sexo
                    )
                    viewModel.persona = persona // Guardamos en ViewModel
                    navController.navigate(Screens.Examen) // Navegamos al examen
                }
            }) {
                Text("Siguiente")
            }
        }
    }
}
