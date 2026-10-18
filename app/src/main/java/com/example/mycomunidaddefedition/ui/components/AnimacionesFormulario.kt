
package com.example.mycomunidaddefedition.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 1. Animación para mensajes de error
@Composable
fun MensajeErrorAnimado(
    mensaje: String?
) {
    AnimatedVisibility(
        visible = mensaje != null,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = mensaje.orEmpty(),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// 2. Animación de confirmación
@Composable
fun MensajeExitoAnimado(
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Correcto",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Operación completada",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// 3. Cambio animado entre texto y carga
@Composable
fun ContenidoBotonAnimado(
    guardando: Boolean,
    texto: String
) {
    Crossfade(
        targetState = guardando,
        label = "AnimacionGuardado"
    ) { cargando ->

        if (cargando) {
            CircularProgressIndicator()
        } else {
            Text(texto)
        }
    }
}
