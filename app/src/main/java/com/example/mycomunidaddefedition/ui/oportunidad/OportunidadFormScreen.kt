package com.example.mycomunidaddefedition.ui.oportunidad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OportunidadFormScreen(
    viewModel: OportunidadViewModel,
    oportunidadId: Long? = null,
    onBack: () -> Unit
) {
    val formState by viewModel.state.collectAsState()

    LaunchedEffect(oportunidadId) {
        if (oportunidadId != null && oportunidadId > 0L) {
            viewModel.loadOpportunity(oportunidadId)
        } else {
            viewModel.newOpportunity()
        }
    }

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

            if (formState.isLoading) {
                CircularProgressIndicator()
                Text("Cargando oportunidad...")
            }

            // Opportunity name.
            OutlinedTextField(
                value = formState.name,
                onValueChange = viewModel::onNameChange,
                label = {
                    Text("Nombre de la oportunidad")
                },
                isError = formState.nameError != null,
                supportingText = {
                    formState.nameError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Opportunity type.
            OutlinedTextField(
                value = formState.type,
                onValueChange = viewModel::onTypeChange,
                label = {
                    Text("Tipo de oportunidad")
                },
                isError = formState.typeError != null,
                supportingText = {
                    formState.typeError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Description.
            OutlinedTextField(
                value = formState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = {
                    Text("Descripción")
                },
                isError = formState.descriptionError != null,
                supportingText = {
                    formState.descriptionError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Information source.
            OutlinedTextField(
                value = formState.source,
                onValueChange = viewModel::onSourceChange,
                label = {
                    Text("Fuente de información")
                },
                isError = formState.sourceError != null,
                supportingText = {
                    formState.sourceError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Valores controlados para evitar errores de escritura.
            Text("Prioridad", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Baja", "Media", "Alta").forEach { option ->
                    FilterChip(
                        selected = formState.priority == option,
                        onClick = { viewModel.onPriorityChange(option) },
                        label = { Text(option) },
                        enabled = !formState.isLoading && !formState.isSaving
                    )
                }
            }
            formState.priorityError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Text("Potencial comercial", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Bajo", "Medio", "Alto").forEach { option ->
                    FilterChip(
                        selected = formState.potential == option,
                        onClick = { viewModel.onPotentialChange(option) },
                        label = { Text(option) },
                        enabled = !formState.isLoading && !formState.isSaving
                    )
                }
            }
            formState.potentialError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Opportunity status.
            OutlinedTextField(
                value = formState.status,
                onValueChange = viewModel::onStatusChange,
                label = {
                    Text("Estado de gestión")
                },
                isError = formState.statusError != null,
                supportingText = {
                    formState.statusError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Additional observations.
            OutlinedTextField(
                value = formState.observations,
                onValueChange = viewModel::onObservationsChange,
                label = {
                    Text("Observaciones")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            // General error message.
            formState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Save or update the opportunity.
            Button(
                onClick = {
                    viewModel.saveOpportunity(onBack)
                },
                enabled = !formState.isSaving && !formState.isLoading &&
                    (oportunidadId == null || oportunidadId == 0L || formState.id == oportunidadId),
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

            // Return to the previous screen.
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