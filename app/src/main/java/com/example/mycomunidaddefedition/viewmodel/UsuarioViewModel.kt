
package com.example.mycomunidaddefedition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomunidaddefedition.data.local.UsuarioDatabase
import com.example.mycomunidaddefedition.data.local.UsuarioEntity
import com.example.mycomunidaddefedition.data.model.UsuarioErrores
import com.example.mycomunidaddefedition.data.model.UsuarioUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(app: Application) : AndroidViewModel(app) {

    // 1. Acceso a Room
    private val dao = UsuarioDatabase
        .getDatabase(app)
        .usuarioDao()

    // 2. Estado del formulario
    private val _estado = MutableStateFlow(
        UsuarioUiState()
    )

    val estado: StateFlow<UsuarioUiState> =
        _estado.asStateFlow()

    // 3. Último usuario guardado en Room
    val ultimoUsuario: Flow<UsuarioEntity?> =
        dao.obtenerUltimo()

    // 4. Actualizar nombre
    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    // 5. Actualizar correo
    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    // 6. Actualizar contraseña
    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errores = it.errores.copy(clave = null)
            )
        }
    }

    // 7. Actualizar dirección
    fun onDireccionChange(valor: String) {
        _estado.update {
            it.copy(
                direccion = valor,
                errores = it.errores.copy(direccion = null)
            )
        }
    }

    // 8. Términos y condiciones
    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(
                aceptarTerminos = valor
            )
        }
    }

    // 9. Validación del formulario
    fun validarFormulario(): Boolean {

        val actual = _estado.value

        val errores = UsuarioErrores(
            nombre = if (actual.nombre.isBlank()) {
                "Campo obligatorio"
            } else {
                null
            },

            correo = if (!actual.correo.contains("@")) {
                "Correo inválido"
            } else {
                null
            },

            clave = if (actual.clave.length < 6) {
                "Debe tener al menos 6 caracteres"
            } else {
                null
            },

            direccion = if (actual.direccion.isBlank()) {
                "Campo obligatorio"
            } else {
                null
            }
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update {
            it.copy(errores = errores)
        }

        return !hayErrores
    }

    // 10. Guardar en Room
    fun guardar(onListo: () -> Unit) {

        val usuario = _estado.value

        viewModelScope.launch {

            dao.insertar(
                UsuarioEntity(
                    nombre = usuario.nombre,
                    correo = usuario.correo,
                    clave = usuario.clave,
                    direccion = usuario.direccion
                )
            )

            onListo()
        }
    }
}
