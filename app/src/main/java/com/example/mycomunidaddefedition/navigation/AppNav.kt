package com.example.mycomunidaddefedition.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycomunidaddefedition.ui.home.MuestraDatosScreen
import com.example.mycomunidaddefedition.ui.theme.HomeScreen
import com.example.mycomunidaddefedition.ui.view.DrawerMenu
import com.example.mycomunidaddefedition.ui.view.DrawerMenu

@Composable
fun AppNav(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            HomeScreen(navController = navController)
        }//fin composable 1

        composable(
            // route = "muestraDatos/{username}",
            route = "DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type = NavType.StringType
                }
            )
        )//fin composable 2
        {//inicio back
                backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()

            // MuestraDatosScreen(username = username, navController = navController)
            DrawerMenu(username = username, navController = navController)

        }


    }//fin NavHost
}//fin AppNav