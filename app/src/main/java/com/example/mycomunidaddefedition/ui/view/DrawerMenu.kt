
package com.example.mycomunidaddefedition.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {

    // Estado de la ventana informativa
    var mostrarInformacion by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // 1. Encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Categorías Usuario: $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // 2. Opciones del menú
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Oportunidades
            NavigationDrawerItem(
                label = {
                    Text("Oportunidades")
                },
                selected = false,
                onClick = {
                    navController.navigate("oportunidades") {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Oportunidades"
                    )
                }
            )

            // Acerca de
            NavigationDrawerItem(
                label = {
                    Text("Acerca de")
                },
                selected = false,
                onClick = {
                    mostrarInformacion = true
                },
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Información"
                    )
                }
            )

            // Cerrar sesión
            NavigationDrawerItem(
                label = {
                    Text("Cerrar sesión")
                },
                selected = false,
                onClick = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Cerrar sesión"
                    )
                }
            )
        }
    }

    // 3. Ventana de información
    if (mostrarInformacion) {
        AlertDialog(
            onDismissRequest = {
                mostrarInformacion = false
            },
            title = {
                Text("MyComunidadDefEdition")
            },
            text = {
                Text(
                    "Aplicación móvil para la gestión " +
                            "de oportunidades y registros."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarInformacion = false
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
