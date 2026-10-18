
package com.example.mycomunidaddefedition.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.mycomunidaddefedition.ui.theme.HomeScreen
import com.example.mycomunidaddefedition.ui.view.DrawerMenu
import com.example.mycomunidaddefedition.ui.screen.RegistroScreen
import com.example.mycomunidaddefedition.ui.screen.ResumenScreen
import com.example.mycomunidaddefedition.viewmodel.UsuarioViewModel

@Composable
fun AppNav() {

    // 1. Controlador de navegación
    val navController = rememberNavController()

    // 2. ViewModel compartido entre registro y resumen
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // 3. Navegación principal
    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {

        // PANTALLA 1: REGISTRO DE USUARIO
        composable("registro") {

            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // PANTALLA 2: RESUMEN DEL REGISTRO
        composable("resumen") {

            ResumenScreen(
                viewModel = usuarioViewModel
            )
        }

        // PANTALLA 3: INICIO DE SESIÓN
        composable("login") {

            HomeScreen(
                navController = navController
            )
        }

        // PANTALLA 4: MENÚ PRINCIPAL
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
    }
}
