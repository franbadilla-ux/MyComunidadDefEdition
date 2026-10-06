package com.example.mycomunidaddefedition.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mycomunidaddefedition.data.repository.AuthRepository

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(
            username = value,
            error = null
        )
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(
            password = value,
            error = null
        )
    }

    fun submit(onSuccess: (String) -> Unit) {

        uiState = uiState.copy(
            isLoading = true,
            error = null
        )

        val ok = repo.login(
            username = uiState.username.trim(),
            password = uiState.password
        )

        uiState = uiState.copy(
            isLoading = false
        )

        if (ok) {
            onSuccess(uiState.username.trim())
        } else {
            uiState = uiState.copy(
                error = "Credenciales invalidas"
            )
        }
    }
}