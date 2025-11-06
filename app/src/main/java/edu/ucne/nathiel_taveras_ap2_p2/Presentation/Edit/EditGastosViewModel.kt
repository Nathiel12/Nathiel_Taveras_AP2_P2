package edu.ucne.nathiel_taveras_ap2_p2.Presentation.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos
import edu.ucne.nathiel_taveras_ap2_p2.Domain.UseCases.GetGastosUseCase
import edu.ucne.nathiel_taveras_ap2_p2.Domain.UseCases.PostGastosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditGastosViewModel @Inject constructor(
    private val getGastosUseCase: GetGastosUseCase,
    private val createGastosUseCase: PostGastosUseCase,
) : ViewModel(){
    private val _state = MutableStateFlow(EditGastosUiState())
    val state: StateFlow<EditGastosUiState> = _state.asStateFlow()

    fun onEvent(event: EditGastosUiEvent){
        when(event){
            is EditGastosUiEvent.Load -> onLoad(event.id)
            is EditGastosUiEvent.FechaChanged ->{
                _state.update {
                    it.copy(
                        fecha = event.value,
                        fechaError = null
                    )
                }
            }
            is EditGastosUiEvent.SuplidorChanged ->{
                _state.update {
                    it.copy(
                        suplidor = event.value,
                        suplidorError = null
                    )
                }
            }
            is EditGastosUiEvent.NcfChanged ->{
                _state.update {
                    it.copy(
                        ncf = event.value,
                        ncfError = null
                    )
                }
            }
            is EditGastosUiEvent.ItbisChanged ->{
                val itbisInt = event.value.toDoubleOrNull()
                _state.update {
                    it.copy(
                        itbis = itbisInt,
                        itbisError = null
                    )
                }
            }
            is EditGastosUiEvent.MontoChanged ->{
                val montoInt = event.value.toDoubleOrNull()
                _state.update {
                    it.copy(
                        monto = montoInt,
                        montoError = null
                    )
                }
            }
            EditGastosUiEvent.Save -> onSave()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.update { it.copy(isNew = true, gastoId = null) }
            return
        }
        viewModelScope.launch {
            val gastos = getGastosUseCase(id)
            if (gastos != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        gastoId = gastos.gastoId,
                        fecha = gastos.fecha,
                        suplidor = gastos.suplidor,
                        ncf = gastos.ncf,
                        itbis = gastos.itbis,
                        monto = gastos.monto
                    )
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val fecha = _state.value.fecha
        val suplidor = _state.value.suplidor
        val ncf = _state.value.ncf
        val itbis = _state.value.itbis
        val monto = _state.value.monto

        var isValid = true

        if (fecha.isBlank()) {
            _state.update { it.copy(fechaError = "La fecha es requerida") }
            isValid = false
        }
        if (suplidor.isBlank()) {
            _state.update { it.copy(suplidorError = "El suplidor es requerido") }
            isValid = false
        }
        if (ncf.isBlank()) {
            _state.update { it.copy(ncfError = "El ncf es requerida") }
            isValid = false
        }

        if (itbis == null) {
            _state.update { it.copy(itbisError = "El itbis es requerido") }
            isValid = false
        } else if (itbis < 0) {
            _state.update { it.copy(itbisError = "El itbis no puede ser negativo") }
            isValid = false
        }
        if (monto == null) {
            _state.update { it.copy(montoError = "El monto es requerido") }
            isValid = false
        } else if (monto < 0) {
            _state.update { it.copy(montoError = "El monto no puede ser negativo") }
            isValid = false
        }

        return isValid
    }

    private fun onSave() {
        viewModelScope.launch {
            if (!validateFields()) {
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            try {
                val gasto = Gastos(
                    gastoId = _state.value.gastoId ?: 0,
                    fecha = _state.value.fecha,
                    suplidor = _state.value.suplidor,
                    ncf = _state.value.ncf,
                    itbis = _state.value.itbis,
                    monto = _state.value.monto
                )

                if (_state.value.isNew) {
                    createGastosUseCase(gasto)
                }

                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        fechaError = null,
                        suplidorError = null,
                        ncfError = null,
                        itbisError = null,
                        montoError = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        fechaError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }

}