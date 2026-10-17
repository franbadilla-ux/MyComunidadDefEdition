
package com.example.mycomunidaddefedition.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycomunidaddefedition.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    viewModel: UsuarioViewModel
) {

    // 1. Datos del último usuario guardado
    val usuario by viewModel.ultimoUsuario.collectAsState(
        initial = null
    )

    // 2. Estado temporal del formulario
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // 3. Título
        Text(
            text = "Resumen del registro",
            style = MaterialTheme.typography.headlineMedium
        )

        // 4. Información almacenada
        if (usuario != null) {

            Text(
                text = "Nombre: ${usuario?.nombre.orEmpty()}"
            )

            Text(
                text = "Correo: ${usuario?.correo.orEmpty()}"
            )

            Text(
                text = "Dirección: ${usuario?.direccion.orEmpty()}"
            )

            // No mostrar información sensible
            Text(
                text = "Contraseña: ********"
            )

            // Esta preferencia aún viene del formulario
            Text(
                text = "Términos: ${
                    if (estado.aceptarTerminos) {
                        "Aceptados"
                    } else {
                        "No aceptados"
                    }
                }"
            )

        } else {

            Text(
                text = "No hay un usuario disponible todavía."
            )
        }
    }
}
