package edu.ucne.nathiel_taveras_ap2_p2.Presentation.Edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditGastosScreen(
    gastoId: Int,
    onDismiss: () -> Unit,
    viewModel: EditGastosViewModel = hiltViewModel()
) {
    LaunchedEffect(gastoId) {
        viewModel.onEvent(EditGastosUiEvent.Load(gastoId))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        EditGastosBody(state, viewModel::onEvent, onDismiss)
    }
}

@Composable
private fun EditGastosBody(
    state: EditGastosUiState,
    onEvent: (EditGastosUiEvent) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .navigationBarsPadding()
            .imePadding()
    ) {
        Text(
            text = if (state.isNew) "Nuevo Gasto" else "Edita Gasto",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.fecha,
            onValueChange = { onEvent(EditGastosUiEvent.FechaChanged(it)) },
            label = { Text("Fecha") },
            isError = state.fechaError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.fechaError != null) {
            Text(
                state.fechaError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.suplidor,
            onValueChange = { onEvent(EditGastosUiEvent.SuplidorChanged(it)) },
            label = { Text("Suplidor") },
            isError = state.suplidorError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.suplidorError != null) {
            Text(
                state.suplidorError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.ncf,
            onValueChange = { onEvent(EditGastosUiEvent.NcfChanged(it)) },
            label = { Text("Ncf") },
            isError = state.ncfError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.ncfError != null) {
            Text(
                state.ncfError,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.itbis.toString(),
            onValueChange = { onEvent(EditGastosUiEvent.ItbisChanged(it)) },
            label = { Text("Itbis") },
            isError = state.itbisError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.itbisError != null) {
            Text(
                state.itbisError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.monto.toString(),
            onValueChange = { onEvent(EditGastosUiEvent.NcfChanged(it)) },
            label = { Text("Monto") },
            isError = state.montoError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.montoError != null) {
            Text(
                state.montoError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { onEvent(EditGastosUiEvent.Save) },
                enabled = !state.isSaving
            ) {
                Text("Guardar")
            }
        }
    }
}