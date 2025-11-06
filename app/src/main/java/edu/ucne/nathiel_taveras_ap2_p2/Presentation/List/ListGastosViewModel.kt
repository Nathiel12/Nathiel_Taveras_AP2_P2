package edu.ucne.nathiel_taveras_ap2_p2.Presentation.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.nathiel_taveras_ap2_p2.Domain.UseCases.GetAllGastosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListGastosViewModel @Inject constructor(
    private val getGastosUseCase: GetAllGastosUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ListGastosUiState(isLoading = true))
    val state: StateFlow<ListGastosUiState> = _state.asStateFlow()

    init {
        onEvent(ListGastosUiEvent.Load)
    }

    fun onEvent(event: ListGastosUiEvent) {
        when (event) {
            ListGastosUiEvent.Load -> loadGastos()
            is ListGastosUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            else -> { }
        }
    }

    private fun loadGastos() {
        viewModelScope.launch {
            try {
                val gastos = getGastosUseCase()
                _state.update {
                    it.copy(
                        isLoading = false,
                        gastos = gastos,
                        message = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = "Error al cargar: ${e.message}"
                    )
                }
            }
        }
    }


    fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}