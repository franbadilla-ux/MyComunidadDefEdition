package com.example.mycomunidaddefedition.ui.oportunidad

data class OportunidadUiState(
    // Opportunity identifier. Zero means a new opportunity.
    val id: Long = 0L,

    // Form fields.
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val source: String = "",
    val priority: String = "",
    val potential: String = "",
    val status: String = "Evento identificado",
    val observations: String = "",

    // Validation errors.
    val nameError: String? = null,
    val typeError: String? = null,
    val descriptionError: String? = null,
    val sourceError: String? = null,
    val priorityError: String? = null,
    val potentialError: String? = null,
    val statusError: String? = null,

    // Save operation state.
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccessful: Boolean = false,
    val errorMessage: String? = null
)