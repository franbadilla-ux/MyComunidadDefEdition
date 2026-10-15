package com.example.mycomunidaddefedition.ui.oportunidad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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

            // Priority.
            OutlinedTextField(
                value = formState.priority,
                onValueChange = viewModel::onPriorityChange,
                label = {
                    Text("Prioridad: Baja, Media o Alta")
                },
                isError = formState.priorityError != null,
                supportingText = {
                    formState.priorityError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Commercial potential.
            OutlinedTextField(
                value = formState.potential,
                onValueChange = viewModel::onPotentialChange,
                label = {
                    Text("Potencial: Bajo, Medio o Alto")
                },
                isError = formState.potentialError != null,
                supportingText = {
                    formState.potentialError?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

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