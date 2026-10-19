
package com.example.mycomunidaddefedition.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    viewModel: QrViewModel,
    onBack: () -> Unit,
    onOpenOpportunity: (Long) -> Unit
) {

    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
        }

    val result by viewModel.result.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val procesando by viewModel.procesando.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lector de QR")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Text(
                "Escanea un código con el formato " + "oportunidad:123. El ID debe existir en Room."
            )

            when {

                // 1. Solicitar permiso
                !hasPermission -> {

                    Text(
                        "Se necesita permiso de cámara."
                    )

                    Button(
                        onClick = {
                            permissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                    ) {
                        Text("Solicitar permiso")
                    }
                }

                // 2. Cámara activa
                result == null -> {

                    QrCameraPreview(
                        onQrScanned = viewModel::onQrDetected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp)
                    )

                    if (procesando) {
                        CircularProgressIndicator()
                        Text("Verificando oportunidad...")
                    }
                }

                // 3. Resultado del QR
                else -> {

                    val scanned = result!!

                    Text(
                        "Contenido leído: ${scanned.content}"
                    )

                    mensaje?.let {
                        Text(it)
                    }

                    // Solo aparece si existe en Room
                    scanned.opportunityId?.let { id ->

                        Button(
                            onClick = {
                                onOpenOpportunity(id)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Abrir oportunidad #$id")
                        }
                    }

                    Button(
                        onClick = viewModel::clearResult,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Escanear otro")
                    }
                }
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver a oportunidades")
            }
        }
    }
}
