package edu.ucne.nathiel_taveras_ap2_p2.Presentation.Edit

sealed interface EditGastosUiEvent {
    data class Load(val id: Int?) : EditGastosUiEvent
    data class FechaChanged(val value: String) : EditGastosUiEvent
    data class SuplidorChanged(val value: String) : EditGastosUiEvent
    data class NcfChanged(val value: String) : EditGastosUiEvent
    data class ItbisChanged(val value: String) : EditGastosUiEvent
    data class MontoChanged(val value: String) : EditGastosUiEvent
    data object Save : EditGastosUiEvent
}
