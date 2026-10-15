package com.example.mycomunidaddefedition.data.repository

import com.example.mycomunidaddefedition.data.local.OportunidadDao
import com.example.mycomunidaddefedition.data.local.OportunidadEntity
import kotlinx.coroutines.flow.Flow

class OportunidadRepository(
    private val dao: OportunidadDao
) {

    // 1. Obtener todas las oportunidades
    fun obtenerTodas(): Flow<List<OportunidadEntity>> {
        return dao.obtenerTodas()
    }

    // 2. Registrar una oportunidad nueva
    suspend fun insertar(
        oportunidad: OportunidadEntity
    ): Long {
        return dao.insertar(oportunidad)
    }

    // 3. Buscar una oportunidad por su ID
    suspend fun obtenerPorId(
        id: Long
    ): OportunidadEntity? {
        return dao.obtenerPorId(id)
    }

    // 4. Modificar una oportunidad
    suspend fun actualizar(
        oportunidad: OportunidadEntity
    ) {
        dao.actualizar(oportunidad)
    }
}