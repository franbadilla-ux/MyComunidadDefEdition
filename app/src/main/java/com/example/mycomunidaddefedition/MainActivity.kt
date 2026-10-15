package com.example.mycomunidaddefedition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mycomunidaddefedition.navigation.AppNav
import com.example.mycomunidaddefedition.ui.theme.MyComunidadDefEditionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyComunidadDefEditionTheme {
                AppNav()
            }
        }
    }
}