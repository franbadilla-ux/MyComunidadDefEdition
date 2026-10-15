package com.example.mycomunidaddefedition.ui.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DrawerMenu(
    username : String,
    navController : NavController
){//Inicio drawer
    Column(modifier = Modifier.fillMaxSize())
    {//Inicio columna
        Box(//inicio Box
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)

        )//fin Box

        {//Inicio contenido
            Text(//inicio texto
                text = "Categorias Usuario: $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )//fin texto

        }//Fin contenido

        //Items (cosas de menu)
        LazyColumn(modifier = Modifier.weight(1f)){
            item{//Item 1
                NavigationDrawerItem(
                    label = {Text("Bienvenido al clan de los pyros")},
                    selected = false,
                    onClick = {/* ACCION FUTURA*/},
                    icon = {Icon(Icons.Default.LocalFireDepartment, contentDescription= "Hub de pyros")}

                )// Fin Navigation

            }//fin item 1

            item{//Item 2
                NavigationDrawerItem(
                    label = {Text("Como hacer spychecking")},
                    selected = false,
                    onClick = {/* ACCION FUTURA*/},
                    icon = {Icon(Icons.Default.Search, contentDescription= "tutorial anti espias")}

                )// Fin Navigation

            }//fin item 2
            item{//Item 3
                NavigationDrawerItem(
                    label = {Text("Como no hacer w+right")},
                    selected = false,
                    onClick = {/* ACCION FUTURA*/},
                    icon = {Icon(Icons.Default.LocalFireDepartment, contentDescription= " Tutorial")}

                )// Fin Navigation

            }//fin item 3

            item {
                NavigationDrawerItem(
                    label = {
                        Text("Oportunidades")
                    },
                    selected = false,
                    onClick = {
                        navController.navigate("oportunidades")
                    },
                    icon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Oportunidades"
                        )
                    }
                )
            }

        }//Fin Lazy

    }//fin inicio columna

}//fin Inicio drawer

@Preview(showBackground = true)
@Composable

fun DrawerMenuPreview(){
    val navController = rememberNavController()
    DrawerMenu(username = "Usuario prueba",navController = navController)

}