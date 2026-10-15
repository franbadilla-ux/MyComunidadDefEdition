package com.example.mycomunidaddefedition.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { hasPermission = it }
    val result by viewModel.result.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Lector de QR") }) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Escanea un código con el formato oportunidad:123, donde 123 es el ID guardado en Room.")
            when {
                !hasPermission -> {
                    Text("Se necesita permiso de cámara para leer códigos QR.")
                    Button(onClick = {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }) { Text("Solicitar permiso") }
                }
                result == null -> {
                    QrCameraPreview(
                        onQrScanned = viewModel::onQrDetected,
                        modifier = Modifier.fillMaxWidth().height(330.dp)
                    )
                }
                else -> {
                    val scanned = result!!
                    Text("Contenido leído: ${scanned.content}")
                    if (scanned.opportunityId != null) {
                        Button(onClick = {
                            onOpenOpportunity(scanned.opportunityId)
                        }) { Text("Abrir oportunidad #${scanned.opportunityId}") }
                    } else {
                        Text("Este QR no contiene un ID de oportunidad válido.")
                    }
                    Button(onClick = viewModel::clearResult) {
                        Text("Escanear otro")
                    }
                }
            }
            OutlinedButton(onClick = onBack) { Text("Volver a oportunidades") }
        }
    }
}
