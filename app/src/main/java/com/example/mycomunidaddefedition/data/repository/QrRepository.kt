
package com.example.mycomunidaddefedition.data.repository

import com.example.mycomunidaddefedition.data.model.QrResult

class QrRepository {

    // Formato esperado: oportunidad:123
    fun processQrContent(content: String): QrResult {

        val normalized = content.trim()

        val pattern = Regex(
            pattern = "^oportunidad:([0-9]+)$",
            option = RegexOption.IGNORE_CASE
        )

        val match = pattern.matchEntire(normalized)

        val id = match
            ?.groupValues
            ?.get(1)
            ?.toLongOrNull()
            ?.takeIf { it > 0L }

        return QrResult(
            content = normalized,
            opportunityId = id
        )
    }
}
