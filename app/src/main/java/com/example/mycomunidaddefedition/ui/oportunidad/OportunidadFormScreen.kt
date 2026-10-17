
package com.example.mycomunidaddefedition.ui.oportunidad

import android.Manifest
import android.content.pm.PackageManager

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OportunidadFormScreen(
    viewModel: OportunidadViewModel,
    oportunidadId: Long? = null,
    onBack: () -> Unit
) {

    // Estado del formulario
    val formState by viewModel.state.collectAsState()

    val context = LocalContext.current

    // Solicitar permiso de ubicación
    val solicitarPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->

        if (concedido) {
            viewModel.obtenerUbicacion()
        } else {
            viewModel.permisoUbicacionDenegado()
        }
    }

    // Crear o cargar una oportunidad
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

            // Cargando datos desde Room
            if (formState.isLoading) {
                CircularProgressIndicator()
                Text("Cargando oportunidad...")
            }

            // 1. Nombre
            OutlinedTextField(
                value = formState.name,
                onValueChange = viewModel::onNameChange,
                label = {
                    Text("Nombre de la oportunidad")
                },
                isError = formState.nameError != null,
                supportingText = {
                    formState.nameError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 2. Tipo
            OutlinedTextField(
                value = formState.type,
                onValueChange = viewModel::onTypeChange,
                label = {
                    Text("Tipo de oportunidad")
                },
                isError = formState.typeError != null,
                supportingText = {
                    formState.typeError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 3. Descripción
            OutlinedTextField(
                value = formState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = {
                    Text("Descripción")
                },
                isError = formState.descriptionError != null,
                supportingText = {
                    formState.descriptionError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // 4. Fuente de información
            OutlinedTextField(
                value = formState.source,
                onValueChange = viewModel::onSourceChange,
                label = {
                    Text("Fuente de información")
                },
                isError = formState.sourceError != null,
                supportingText = {
                    formState.sourceError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 5. Prioridad
            Text(
                "Prioridad",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Baja", "Media", "Alta").forEach { opcion ->

                    FilterChip(
                        selected = formState.priority == opcion,
                        onClick = {
                            viewModel.onPriorityChange(opcion)
                        },
                        label = {
                            Text(opcion)
                        },
                        enabled = !formState.isLoading &&
                                !formState.isSaving
                    )
                }
            }

            formState.priorityError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 6. Potencial comercial
            Text(
                "Potencial comercial",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Bajo", "Medio", "Alto").forEach { opcion ->

                    FilterChip(
                        selected = formState.potential == opcion,
                        onClick = {
                            viewModel.onPotentialChange(opcion)
                        },
                        label = {
                            Text(opcion)
                        },
                        enabled = !formState.isLoading &&
                                !formState.isSaving
                    )
                }
            }

            formState.potentialError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 7. Estado de gestión
            OutlinedTextField(
                value = formState.status,
                onValueChange = viewModel::onStatusChange,
                label = {
                    Text("Estado de gestión")
                },
                isError = formState.statusError != null,
                supportingText = {
                    formState.statusError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 8. Observaciones
            OutlinedTextField(
                value = formState.observations,
                onValueChange = viewModel::onObservationsChange,
                label = {
                    Text("Observaciones")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            // 9. Recurso nativo: ubicación GPS
            Text(
                "Referencia geográfica",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedButton(
                onClick = {

                    val permiso = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (permiso) {
                        viewModel.obtenerUbicacion()
                    } else {
                        solicitarPermiso.launch(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    }
                },
                enabled = !formState.buscandoUbicacion &&
                        !formState.isLoading &&
                        !formState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Obtener ubicación")
            }

            // Indicador de carga GPS
            if (formState.buscandoUbicacion) {
                CircularProgressIndicator()
                Text("Buscando ubicación...")
            }

            // Mostrar coordenadas obtenidas
            if (
                formState.latitud != null &&
                formState.longitud != null
            ) {
                Text("Latitud: ${formState.latitud}")
                Text("Longitud: ${formState.longitud}")
            }

            // Mensajes de error GPS
            formState.errorUbicacion?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Errores generales del formulario
            formState.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 10. Guardar o actualizar
            Button(
                onClick = {
                    viewModel.saveOpportunity(onBack)
                },
                enabled = !formState.isSaving &&
                        !formState.isLoading &&
                        (
                                oportunidadId == null ||
                                        oportunidadId == 0L ||
                                        formState.id == oportunidadId
                                ),
                modifier = Modifier.fillMaxWidth()
            ) {

                if (formState.isSaving) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        if (
                            oportunidadId != null &&
                            oportunidadId > 0L
                        ) {
                            "Actualizar oportunidad"
                        } else {
                            "Guardar oportunidad"
                        }
                    )
                }
            }

            // 11. Volver
            OutlinedButton(
                onClick = onBack,
                enabled = !formState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}
