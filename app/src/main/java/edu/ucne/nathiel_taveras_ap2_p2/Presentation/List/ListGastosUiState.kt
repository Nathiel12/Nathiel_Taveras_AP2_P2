package edu.ucne.nathiel_taveras_ap2_p2.Presentation.List

import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos

data class ListGastosUiState(
    val isLoading: Boolean = false,
    val gastos: List<Gastos> = emptyList(),
    val message: String? = null
)