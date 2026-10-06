package com.example.mycomunidaddefedition.data.model

data class UsuarioUiState(val nombre : String = "",
    val correo : String = "",
    val clave : String = "",
    val direccion : String = "",
    val aceptarTerminos : Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
    )

