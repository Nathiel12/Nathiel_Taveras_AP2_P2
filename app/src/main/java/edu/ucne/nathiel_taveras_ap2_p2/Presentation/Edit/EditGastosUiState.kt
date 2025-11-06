package edu.ucne.nathiel_taveras_ap2_p2.Presentation.Edit

data class EditGastosUiState(
    val gastoId: Int? = null,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: Double? = null,
    val monto: Double? = null,
    val fechaError: String? = null,
    val suplidorError: String? = null,
    val ncfError: String? = null,
    val itbisError: String? = null,
    val montoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
)
