package com.example.mycomunidaddefedition.ui.qr

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun QrCameraPreview(onQrScanned: (String) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val callback = rememberUpdatedState(onQrScanned)
    val previewView = remember(context) {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }
    AndroidView(factory = { previewView }, modifier = modifier)

    DisposableEffect(context, lifecycleOwner, previewView) {
        val executor = Executors.newSingleThreadExecutor()
        val scanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
        )
        val disposed = AtomicBoolean(false)
        val detected = AtomicBoolean(false)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }
        val analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        analysis.setAnalyzer(executor) { proxy ->
            val media = proxy.image
            if (disposed.get() || detected.get() || media == null) {
                proxy.close()
            } else {
                try {
                    val image = InputImage.fromMediaImage(
                        media, proxy.imageInfo.rotationDegrees
                    )
                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->
                            val content = barcodes.firstOrNull {
                                it.format == Barcode.FORMAT_QR_CODE &&
                                    !it.rawValue.isNullOrBlank()
                            }?.rawValue
                            if (content != null && !disposed.get() &&
                                detected.compareAndSet(false, true)) {
                                analysis.clearAnalyzer()
                                ContextCompat.getMainExecutor(context).execute {
                                    if (!disposed.get()) callback.value(content)
                                }
                            }
                        }
                        .addOnFailureListener { e -> Log.e("QrCameraPreview", "Error QR", e) }
                        .addOnCompleteListener { proxy.close() }
                } catch (e: Exception) {
                    Log.e("QrCameraPreview", "Error analizando imagen", e)
                    proxy.close()
                }
            }
        }

        val providerFuture = ProcessCameraProvider.getInstance(context)
        var provider: ProcessCameraProvider? = null
        providerFuture.addListener({
            if (!disposed.get()) {
                try {
                    val activeProvider = providerFuture.get()
                    if (!disposed.get()) {
                        activeProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            analysis
                        )
                        provider = activeProvider
                    }
                } catch (e: Exception) {
                    Log.e("QrCameraPreview", "No se pudo iniciar la cámara", e)
                }
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            disposed.set(true)
            analysis.clearAnalyzer()
            provider?.unbind(preview, analysis)
            scanner.close()
            executor.shutdown()
        }
    }
}
