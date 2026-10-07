package com.example.mycomunidaddefedition.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mycomunidaddefedition.data.model.UsuarioErrores
import com.example.mycomunidaddefedition.data.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()


    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(
                    nombre = null
                )
            )
        }
    }


    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(
                    correo = null
                )
            )
        }
    }


    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errores = it.errores.copy(
                    clave = null
                )
            )
        }
    }


    fun onDireccionChange(valor: String) {
        _estado.update {
            it.copy(
                direccion = valor,
                errores = it.errores.copy(
                    direccion = null
                )
            )
        }
    }


    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(
                aceptarTerminos = valor
            )
        }
    }


    fun validarFormulario(): Boolean {

        val actual = _estado.value

        val errores = UsuarioErrores(

            nombre = if (actual.nombre.isBlank()) {
                "El nombre es obligatorio"
            } else {
                null
            },

            correo = if (actual.correo.isBlank()) {
                "El correo es obligatorio"
            } else {
                null
            },

            clave = if (actual.clave.isBlank()) {
                "La clave es obligatoria"
            } else {
                null
            },

            direccion = if (actual.direccion.isBlank()) {
                "La dirección es obligatoria"
            } else {
                null
            }
        )

        _estado.update {
            it.copy(
                errores = errores
            )
        }

        return errores.nombre == null &&
                errores.correo == null &&
                errores.clave == null &&
                errores.direccion == null &&
                actual.aceptarTerminos
    }
}