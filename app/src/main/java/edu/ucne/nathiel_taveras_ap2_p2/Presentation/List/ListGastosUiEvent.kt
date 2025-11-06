package edu.ucne.nathiel_taveras_ap2_p2.Presentation.List

sealed interface ListGastosUiEvent{
    data object Load: ListGastosUiEvent
    data object CreateNew: ListGastosUiEvent
    data class Edit(val id: Int) : ListGastosUiEvent
    data class ShowMessage(val message: String) : ListGastosUiEvent
}