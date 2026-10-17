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
import com.example.mycomunidaddefedition.ui.oportunidad.OportunidadViewModel
import com.example.mycomunidaddefedition.ui.oportunidad.ListaOportunidadesScreen
import com.example.mycomunidaddefedition.ui.oportunidad.OportunidadFormScreen
import com.example.mycomunidaddefedition.ui.qr.QrScannerScreen
import com.example.mycomunidaddefedition.ui.qr.QrViewModel
import com.example.mycomunidaddefedition.viewmodel.UsuarioViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val oportunidadViewModel: OportunidadViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            HomeScreen(navController = navController)
        }

        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            DrawerMenu(
                username = entry.arguments?.getString("username").orEmpty(),
                navController = navController
            )
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        composable("resumen") {
            ResumenScreen(usuarioViewModel)
        }

        composable("oportunidades") {
            ListaOportunidadesScreen(
                viewModel = oportunidadViewModel,
                onAdd = { navController.navigate("oportunidadForm/0") },
                onEdit = { id -> navController.navigate("oportunidadForm/$id") },
                onScanQr = { navController.navigate("qr") }
            )
        }

        composable(
            route = "oportunidadForm/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            OportunidadFormScreen(
                viewModel = oportunidadViewModel,
                oportunidadId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable("qr") {
            val qrViewModel: QrViewModel = viewModel()
            QrScannerScreen(
                viewModel = qrViewModel,
                onBack = { navController.popBackStack() },
                onOpenOpportunity = { id ->
                    navController.navigate("oportunidadForm/$id") {
                        popUpTo("qr") { inclusive = true }
                    }
                }
            )
        }
    }
}
