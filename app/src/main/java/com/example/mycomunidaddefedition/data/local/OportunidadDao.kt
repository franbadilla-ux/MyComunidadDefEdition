package com.example.mycomunidaddefedition.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OportunidadDao {

    // Insertar una nueva oportunidad
    @Insert
    suspend fun insertar(
        oportunidad: OportunidadEntity
    ): Long

    // Consultar todas las oportunidades
    @Query("SELECT * FROM oportunidades ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<OportunidadEntity>>

    // Buscar una oportunidad por ID
    @Query("SELECT * FROM oportunidades WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(
        id: Long
    ): OportunidadEntity?

    // Modificar una oportunidad existente
    @Update
    suspend fun actualizar(
        oportunidad: OportunidadEntity
    )
}