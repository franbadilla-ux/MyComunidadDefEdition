package com.example.mycomunidaddefedition.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class UbicacionRepository(private val context: Context) {

    private val cliente =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(
        resultado: (Result<Pair<Double, Double>>) -> Unit
    ) {
        val permiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!permiso) {
            resultado(Result.failure(SecurityException("Sin permiso GPS")))
            return
        }

        try {
            cliente.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { ubicacion ->
                if (ubicacion != null) {
                    resultado(
                        Result.success(
                            Pair(ubicacion.latitude, ubicacion.longitude)
                        )
                    )
                } else {
                    resultado(Result.failure(Exception("Ubicación no disponible")))
                }
            }.addOnFailureListener {
                resultado(Result.failure(it))
            }
        } catch (e: Exception) {
            resultado(Result.failure(e))
        }
    }
}