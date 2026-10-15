package com.example.mycomunidaddefedition.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.example.mycomunidaddefedition.ui.theme.HomeScreen
import com.example.mycomunidaddefedition.ui.view.DrawerMenu
import com.example.mycomunidaddefedition.ui.screen.RegistroScreen
import com.example.mycomunidaddefedition.ui.screen.ResumenScreen
import com.example.mycomunidaddefedition.viewmodel.UsuarioViewModel

@Composable
fun AppNav() {

    // Un solo controlador de navegación
    val navController = rememberNavController()

    // ViewModel compartido para registro y resumen
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 1. LOGIN
        composable("login") {
            HomeScreen(navController = navController)
        }

        // 2. DRAWER
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val username = backStackEntry.arguments
                ?.getString("username")
                .orEmpty()

            DrawerMenu(
                username = username,
                navController = navController
            )
        }

        // 3. REGISTRO DE USUARIO
        composable("registro") {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // 4. RESUMEN DE USUARIO
        composable("resumen") {
            ResumenScreen(
                viewModel = usuarioViewModel
            )
        }

        // 5. LISTA DE OPORTUNIDADES (TEMPORAL)
        composable("oportunidades") {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Lista de oportunidades")

                Button(
                    onClick = {
                        navController.navigate("oportunidadForm/0")
                    }
                ) {
                    Text("Nueva oportunidad")
                }
            }
        }

        // 6. FORMULARIO DE OPORTUNIDADES (TEMPORAL)
        composable(
            route = "oportunidadForm/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments
                ?.getLong("id") ?: 0L

            Text(
                text = if (id == 0L) {
                    "Formulario de nueva oportunidad"
                } else {
                    "Editar oportunidad ID: $id"
                }
            )
        }
    }
}