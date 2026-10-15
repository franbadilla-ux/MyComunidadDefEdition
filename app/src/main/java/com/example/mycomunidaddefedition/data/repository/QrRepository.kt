package com.example.mycomunidaddefedition.data.repository

import com.example.mycomunidaddefedition.data.model.QrResult

class QrRepository {
    // QR de prueba: "oportunidad:123" identifica el registro local 123.
    fun processQrContent(content: String): QrResult {
        val normalized = content.trim()
        val id = if (normalized.startsWith("oportunidad:", ignoreCase = true)) {
            normalized.substringAfter(":").trim().toLongOrNull()?.takeIf { it > 0L }
        } else {
            null
        }
        return QrResult(normalized, id)
    }
}
