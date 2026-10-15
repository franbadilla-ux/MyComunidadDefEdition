package com.example.mycomunidaddefedition.ui.qr

import androidx.lifecycle.ViewModel
import com.example.mycomunidaddefedition.data.model.QrResult
import com.example.mycomunidaddefedition.data.repository.QrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QrViewModel : ViewModel() {
    private val repository = QrRepository()
    private val _result = MutableStateFlow<QrResult?>(null)
    val result: StateFlow<QrResult?> = _result.asStateFlow()

    fun onQrDetected(content: String) {
        if (_result.value == null) _result.value = repository.processQrContent(content)
    }

    fun clearResult() {
        _result.value = null
    }
}
