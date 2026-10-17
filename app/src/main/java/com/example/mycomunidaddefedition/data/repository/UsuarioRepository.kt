package com.example.mycomunidaddefedition.data.repository

import com.example.mycomunidaddefedition.data.local.UsuarioDao
import com.example.mycomunidaddefedition.data.local.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(
    private val dao: UsuarioDao
) {

    suspend fun insertar(usuario: UsuarioEntity) {
        dao.insertar(usuario)
    }

    fun obtenerUltimo(): Flow<UsuarioEntity?> {
        return dao.obtenerUltimo()
    }
}