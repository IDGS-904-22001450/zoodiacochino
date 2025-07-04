package com.jlino.zoodiacochino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jlino.zoodiacochino.ui.theme.ZoodiacochinoTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.jlino.zoodiacochino.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoodiacochinoTheme {
                val navController = rememberNavController()
                val formViewModel: FormViewModel = viewModel() // ViewModel compartido
                NavigationGraph(navController, formViewModel)

            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    formViewModel: FormViewModel
) {
    NavHost(navController = navController, startDestination = Screens.Formulario) {
        composable(Screens.Formulario) {
            FormularioScreen(navController, formViewModel)
        }
        composable(Screens.Examen) {
            ExamenScreen(navController, formViewModel)
        }
        composable(Screens.Resultado) { backStackEntry ->
            val calificacion = backStackEntry.arguments?.getString("calificacion")?.toIntOrNull() ?: 0
            ResultadoScreen(navController, calificacion, formViewModel)
        }

        composable(Screens.Feedback) {
            FeedbackScreen(navController, formViewModel)
        }
    }
}