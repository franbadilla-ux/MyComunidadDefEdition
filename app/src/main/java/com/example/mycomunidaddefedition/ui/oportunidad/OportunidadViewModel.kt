
package com.example.mycomunidaddefedition.ui.oportunidad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomunidaddefedition.data.local.OportunidadEntity
import com.example.mycomunidaddefedition.data.local.UsuarioDatabase
import com.example.mycomunidaddefedition.data.repository.OportunidadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OportunidadViewModel(
    app: Application
) : AndroidViewModel(app) {

    // Connect to Room through the DAO and repository.
    private val dao =
        UsuarioDatabase.getDatabase(app).oportunidadDao()

    private val repository = OportunidadRepository(dao)

    // Private form state.
    private val _state = MutableStateFlow(OportunidadUiState())

    // Public state observed by the UI.
    val state: StateFlow<OportunidadUiState> = _state.asStateFlow()

    // Reactive list of stored opportunities.
    val opportunities: StateFlow<List<OportunidadEntity>> =
        repository.getAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Handle form field changes.
    fun onNameChange(value: String) {
        _state.update {
            it.copy(
                name = value,
                nameError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onTypeChange(value: String) {
        _state.update {
            it.copy(
                type = value,
                typeError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onDescriptionChange(value: String) {
        _state.update {
            it.copy(
                description = value,
                descriptionError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onSourceChange(value: String) {
        _state.update {
            it.copy(
                source = value,
                sourceError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onPriorityChange(value: String) {
        _state.update {
            it.copy(
                priority = value,
                priorityError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onPotentialChange(value: String) {
        _state.update {
            it.copy(
                potential = value,
                potentialError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onStatusChange(value: String) {
        _state.update {
            it.copy(
                status = value,
                statusError = null,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    fun onObservationsChange(value: String) {
        _state.update {
            it.copy(
                observations = value,
                errorMessage = null,
                saveSuccessful = false
            )
        }
    }

    // Validate the form before saving.
    fun validateForm(): Boolean {
        val current = _state.value

        val nameError =
            if (current.name.isBlank()) {
                "El nombre es obligatorio"
            } else {
                null
            }

        val typeError =
            if (current.type.isBlank()) {
                "El tipo de oportunidad es obligatorio"
            } else {
                null
            }

        val descriptionError =
            if (current.description.isBlank()) {
                "La descripción es obligatoria"
            } else {
                null
            }

        val sourceError =
            if (current.source.isBlank()) {
                "La fuente de información es obligatoria"
            } else {
                null
            }

        val priorityError =
            if (current.priority !in listOf("Baja", "Media", "Alta")) {
                "Selecciona una prioridad válida"
            } else {
                null
            }

        val potentialError =
            if (current.potential !in listOf("Bajo", "Medio", "Alto")) {
                "Selecciona un potencial válido"
            } else {
                null
            }

        val statusError =
            if (current.status.isBlank()) {
                "El estado es obligatorio"
            } else {
                null
            }

        _state.update {
            it.copy(
                nameError = nameError,
                typeError = typeError,
                descriptionError = descriptionError,
                sourceError = sourceError,
                priorityError = priorityError,
                potentialError = potentialError,
                statusError = statusError
            )
        }

        return listOf(
            nameError,
            typeError,
            descriptionError,
            sourceError,
            priorityError,
            potentialError,
            statusError
        ).all { it == null }
    }

    // Reset the form for a new opportunity.
    fun newOpportunity() {
        _state.value = OportunidadUiState()
    }

    // Load an existing opportunity for editing.
    fun loadOpportunity(id: Long) {
        viewModelScope.launch {
            try {
                val opportunity = repository.getById(id)

                if (opportunity != null) {
                    _state.value = OportunidadUiState(
                        id = opportunity.id,
                        name = opportunity.nombre,
                        type = opportunity.tipo,
                        description = opportunity.descripcion,
                        source = opportunity.fuente,
                        priority = opportunity.prioridad,
                        potential = opportunity.potencial,
                        status = opportunity.estado,
                        observations = opportunity.observaciones
                    )
                } else {
                    _state.update {
                        it.copy(
                            errorMessage =
                                "No se encontró la oportunidad solicitada"
                        )
                    }
                }
            } catch (exception: Exception) {
                _state.update {
                    it.copy(
                        errorMessage =
                            "No se pudo cargar la oportunidad"
                    )
                }
            }
        }
    }

    // Insert a new opportunity or update an existing one.
    fun saveOpportunity(onComplete: () -> Unit) {
        if (!validateForm()) {
            return
        }

        val current = _state.value

        _state.update {
            it.copy(
                isSaving = true,
                saveSuccessful = false,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val opportunity = OportunidadEntity(
                    id = current.id,
                    nombre = current.name.trim(),
                    tipo = current.type.trim(),
                    descripcion = current.description.trim(),
                    fuente = current.source.trim(),
                    prioridad = current.priority.trim(),
                    potencial = current.potential.trim(),
                    estado = current.status.trim(),
                    observaciones = current.observations.trim()
                )

                if (current.id == 0L) {
                    repository.insert(opportunity)
                } else {
                    repository.update(opportunity)
                }

                _state.update {
                    it.copy(
                        isSaving = false,
                        saveSuccessful = true
                    )
                }

                onComplete()

            } catch (exception: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        saveSuccessful = false,
                        errorMessage =
                            "No se pudo guardar la oportunidad"
                    )
                }
            }
        }
    }
}

