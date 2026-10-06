package com.example.mycomunidaddefedition.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel : UsuarioViewModel
){
    val estado by viewModel.estado.collectAsState()

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        //CAMPO NOMBRE :)
    }
}