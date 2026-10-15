
package com.example.mycomunidaddefedition.ui.oportunidad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomunidaddefedition.data.local.OportunidadEntity
import com.example.mycomunidaddefedition.data.local.UsuarioDatabase
import com.example.mycomunidaddefedition.data.repository.OportunidadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OportunidadViewModel(
    app: Application
) : AndroidViewModel(app) {

    // 1. Conexión con Room mediante DAO y Repository.
    private val dao =
        UsuarioDatabase.getDatabase(app).oportunidadDao()

    private val repository =
        OportunidadRepository(dao)

    // 2. Estado privado del formulario.
    private val _estado =
        MutableStateFlow(OportunidadUiState())

    // Estado público que observa la interfaz.
    val estado: StateFlow<OportunidadUiState> =
        _estado.asStateFlow()

    // 3. Lista reactiva de oportunidades guardadas.
    val oportunidades: StateFlow<List<OportunidadEntity>> =
        repository.obtenerTodas()
            .let { flujo ->
                kotlinx.coroutines.flow.stateIn(
                    scope = viewModelScope,
                    started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList()
                )
            }

    // 4. Cambios de los campos del formulario.

    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                nombreError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onTipoChange(valor: String) {
        _estado.update {
            it.copy(
                tipo = valor,
                tipoError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onDescripcionChange(valor: String) {
        _estado.update {
            it.copy(
                descripcion = valor,
                descripcionError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onFuenteChange(valor: String) {
        _estado.update {
            it.copy(
                fuente = valor,
                fuenteError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onPrioridadChange(valor: String) {
        _estado.update {
            it.copy(
                prioridad = valor,
                prioridadError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onPotencialChange(valor: String) {
        _estado.update {
            it.copy(
                potencial = valor,
                potencialError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onEstadoChange(valor: String) {
        _estado.update {
            it.copy(
                estado = valor,
                estadoError = null,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    fun onObservacionesChange(valor: String) {
        _estado.update {
            it.copy(
                observaciones = valor,
                mensajeError = null,
                guardadoExitosamente = false
            )
        }
    }

    // 5. Validación de las reglas del formulario.
    fun validarFormulario(): Boolean {
        val actual = _estado.value

        val nombreError =
            if (actual.nombre.isBlank()) {
                "El nombre es obligatorio"
            } else {
                null
            }

        val tipoError =
            if (actual.tipo.isBlank()) {
                "Selecciona el tipo de oportunidad"
            } else {
                null
            }

        val descripcionError =
            if (actual.descripcion.isBlank()) {
                "La descripción es obligatoria"
            } else {
                null
            }

        val fuenteError =
            if (actual.fuente.isBlank()) {
                "La fuente de información es obligatoria"
            } else {
                null
            }

        val prioridadError =
            if (actual.prioridad !in
                listOf("Baja", "Media", "Alta")
            ) {
                "Selecciona una prioridad válida"
            } else {
                null
            }

        val potencialError =
            if (actual.potencial !in
                listOf("Bajo", "Medio", "Alto")
            ) {
                "Selecciona un potencial válido"
            } else {
                null
            }

        val estadoError =
            if (actual.estado.isBlank()) {
                "El estado es obligatorio"
            } else {
                null
            }

        _estado.update {
            it.copy(
                nombreError = nombreError,
                tipoError = tipoError,
                descripcionError = descripcionError,
                fuenteError = fuenteError,
                prioridadError = prioridadError,
                potencialError = potencialError,
                estadoError = estadoError
            )
        }

        return listOf(
            nombreError,
            tipoError,
            descripcionError,
            fuenteError,
            prioridadError,
            potencialError,
            estadoError
        ).all { it == null }
    }

    // 6. Preparar el formulario para una oportunidad nueva.
    fun nuevaOportunidad() {
        _estado.value = OportunidadUiState()
    }

    // 7. Cargar una oportunidad para editarla.
    fun cargarOportunidad(id: Long) {
        viewModelScope.launch {
            try {
                val oportunidad = repository.obtenerPorId(id)

                if (oportunidad != null) {
                    _estado.value = OportunidadUiState(
                        id = oportunidad.id,
                        nombre = oportunidad.nombre,
                        tipo = oportunidad.tipo,
                        descripcion = oportunidad.descripcion,
                        fuente = oportunidad.fuente,
                        prioridad = oportunidad.prioridad,
                        potencial = oportunidad.potencial,
                        estado = oportunidad.estado,
                        observaciones = oportunidad.observaciones
                    )
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        mensajeError =
                            "No se pudo cargar la oportunidad"
                    )
                }
            }
        }
    }

    // 8. Guardar una oportunidad nueva o actualizar una existente.
    fun guardar(onListo: () -> Unit) {
        if (!validarFormulario()) {
            return
        }

        val actual = _estado.value

        _estado.update {
            it.copy(
                guardando = true,
                guardadoExitosamente = false,
                mensajeError = null
            )
        }

        viewModelScope.launch {
            try {
                val oportunidad = OportunidadEntity(
                    id = actual.id,
                    nombre = actual.nombre.trim(),
                    tipo = actual.tipo,
                    descripcion = actual.descripcion.trim(),
                    fuente = actual.fuente.trim(),
                    prioridad = actual.prioridad,
                    potencial = actual.potencial,
                    estado = actual.estado,
                    observaciones = actual.observaciones.trim()
                )

                if (actual.id == 0L) {
                    repository.insertar(oportunidad)
                } else {
                    repository.actualizar(oportunidad)
                }

                _estado.update {
                    it.copy(
                        guardando = false,
                        guardadoExitosamente = true
                    )
                }

                onListo()

            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        guardando = false,
                        guardadoExitosamente = false,
                        mensajeError =
                            "No se pudo guardar la oportunidad"
                    )
                }
            }
        }
    }
}
