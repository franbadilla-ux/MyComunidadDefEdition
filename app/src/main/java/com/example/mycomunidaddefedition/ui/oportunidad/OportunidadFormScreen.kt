
package com.example.mycomunidaddefedition.ui.oportunidad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OportunidadFormScreen(
    viewModel: OportunidadViewModel,
    oportunidadId: Long? = null,
    onBack: () -> Unit
) {

    val formState by viewModel.state.collectAsState()

    // 1. Cargar oportunidad o preparar nuevo registro
    LaunchedEffect(oportunidadId) {
        if (oportunidadId != null && oportunidadId > 0L) {
            viewModel.loadOpportunity(oportunidadId)
        } else {
            viewModel.newOpportunity()
        }
    }

    // 2. Estructura de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (oportunidadId != null && oportunidadId > 0L) {
                            "Editar oportunidad"
                        } else {
                            "Nueva oportunidad"
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 3. Nombre
            OutlinedTextField(
                value = formState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre de la oportunidad") },
                isError = formState.nameError != null,
                supportingText = {
                    formState.nameError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 4. Tipo
            OutlinedTextField(
                value = formState.type,
                onValueChange = viewModel::onTypeChange,
                label = { Text("Tipo de oportunidad") },
                isError = formState.typeError != null,
                supportingText = {
                    formState.typeError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 5. Descripción
            OutlinedTextField(
                value = formState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descripción") },
                isError = formState.descriptionError != null,
                supportingText = {
                    formState.descriptionError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // 6. Fuente
            OutlinedTextField(
                value = formState.source,
                onValueChange = viewModel::onSourceChange,
                label = { Text("Fuente de información") },
                isError = formState.sourceError != null,
                supportingText = {
                    formState.sourceError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 7. Prioridad
            SelectorOportunidad(
                titulo = "Prioridad",
                valor = formState.priority,
                opciones = listOf("Baja", "Media", "Alta"),
                error = formState.priorityError,
                onSeleccionar = viewModel::onPriorityChange
            )

            // 8. Potencial comercial
            SelectorOportunidad(
                titulo = "Potencial comercial",
                valor = formState.potential,
                opciones = listOf("Bajo", "Medio", "Alto"),
                error = formState.potentialError,
                onSeleccionar = viewModel::onPotentialChange
            )

            // 9. Estado de gestión
            SelectorOportunidad(
                titulo = "Estado de gestión",
                valor = formState.status,
                opciones = listOf(
                    "Evento identificado",
                    "En gestión",
                    "Finalizada"
                ),
                error = formState.statusError,
                onSeleccionar = viewModel::onStatusChange
            )

            // 10. Observaciones
            OutlinedTextField(
                value = formState.observations,
                onValueChange = viewModel::onObservationsChange,
                label = { Text("Observaciones") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            // 11. Mensajes generales
            formState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 12. Guardar
            Button(
                onClick = {
                    viewModel.saveOpportunity(onBack)
                },
                enabled = !formState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (formState.isSaving) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        if (oportunidadId != null && oportunidadId > 0L) {
                            "Actualizar oportunidad"
                        } else {
                            "Guardar oportunidad"
                        }
                    )
                }
            }

            // 13. Volver
            Button(
                onClick = onBack,
                enabled = !formState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

// 14. Selector reutilizable
@Composable
fun SelectorOportunidad(
    titulo: String,
    valor: String,
    opciones: List<String>,
    error: String?,
    onSeleccionar: (String) -> Unit
) {

    var expandido by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text(titulo) },
            isError = error != null,
            supportingText = {
                error?.let {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Mostrar opciones"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Área para abrir las opciones
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expandido = true }
        )

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = {
                expandido = false
            }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccionar(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}
