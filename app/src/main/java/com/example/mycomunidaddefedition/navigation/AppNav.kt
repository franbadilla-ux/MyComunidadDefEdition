
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

import com.example.mycomunidaddefedition.ui.oportunidad.OportunidadViewModel
import com.example.mycomunidaddefedition.ui.oportunidad.ListaOportunidadesScreen
import com.example.mycomunidaddefedition.ui.oportunidad.OportunidadFormScreen

import com.example.mycomunidaddefedition.ui.qr.QrScannerScreen
import com.example.mycomunidaddefedition.ui.qr.QrViewModel

@Composable
fun AppNav() {

    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel = viewModel()
    val oportunidadViewModel: OportunidadViewModel = viewModel()
    val qrViewModel: QrViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {

        // Registro de usuario
        composable("registro") {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // Resumen de usuario
        composable("resumen") {
            ResumenScreen(
                viewModel = usuarioViewModel
            )
        }

        // Inicio de sesión
        composable("login") {
            HomeScreen(
                navController = navController
            )
        }

        // Menú principal
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

        // Lista de oportunidades
        composable("oportunidades") {
            ListaOportunidadesScreen(
                viewModel = oportunidadViewModel,
                onAdd = {
                    navController.navigate("oportunidadForm/0")
                },
                onEdit = { id ->
                    navController.navigate("oportunidadForm/$id")
                },
                onScanQr = {
                    navController.navigate("qr")
                }
            )
        }

        // Crear o editar oportunidad
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

            OportunidadFormScreen(
                viewModel = oportunidadViewModel,
                oportunidadId = id,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Lector de códigos QR
        composable("qr") {
            QrScannerScreen(
                viewModel = qrViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onOpenOpportunity = { id ->
                    navController.navigate("oportunidadForm/$id")
                }
            )
        }
    }
}
