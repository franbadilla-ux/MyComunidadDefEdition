
package com.example.mycomunidaddefedition.ui.qr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomunidaddefedition.data.local.UsuarioDatabase
import com.example.mycomunidaddefedition.data.model.QrResult
import com.example.mycomunidaddefedition.data.repository.QrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QrViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val qrRepository = QrRepository()

    private val oportunidadDao = UsuarioDatabase
            .getDatabase(application)
            .oportunidadDao()

    private val _result = MutableStateFlow<QrResult?>(null)

    val result: StateFlow<QrResult?> = _result.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)

    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _procesando = MutableStateFlow(false)

    val procesando: StateFlow<Boolean> = _procesando.asStateFlow()

    fun onQrDetected(content: String) {

        if (_result.value != null || _procesando.value) {
            return
        }

        _procesando.value = true

        viewModelScope.launch {

            try {
                val qr = qrRepository.processQrContent(content)
                val id = qr.opportunityId

                if (id == null) {
                    _result.value = qr
                    _mensaje.value = "El QR no contiene un ID válido."
                    return@launch
                }

                val oportunidad =
                    oportunidadDao.obtenerPorId(id)

                if (oportunidad == null) {
                    _result.value = qr.copy(
                        opportunityId = null
                    )
                    _mensaje.value =
                        "La oportunidad #$id no existe en Room."
                } else {
                    _result.value = qr
                    _mensaje.value = "Oportunidad encontrada."
                }

            } catch (e: Exception) {

                _result.value = QrResult(
                    content = content,
                    opportunityId = null
                )

                _mensaje.value = "No se pudo consultar la oportunidad."

            } finally {
                _procesando.value = false
            }
        }
    }

    fun clearResult() {
        _result.value = null
        _mensaje.value = null
        _procesando.value = false
    }
}
