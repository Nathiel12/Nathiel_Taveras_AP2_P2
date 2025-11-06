package edu.ucne.nathiel_taveras_ap2_p2.Presentation.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos

@Composable
fun ListGastosScreen(
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: ListGastosViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GastosListBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ListGastosUiEvent.Edit -> onNavigateToEdit(event.id)
                is ListGastosUiEvent.CreateNew -> onNavigateToCreate()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun GastosListBody(
    state: ListGastosUiState,
    onEvent: (ListGastosUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Row {
                FloatingActionButton(onClick = { onEvent(ListGastosUiEvent.CreateNew) }) {
                    Text("+")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.gastos) { gasto ->
                    GastosCard(
                        gastos = gasto,
                        onClick = { onEvent(ListGastosUiEvent.Edit(gasto.gastoId)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun GastosCard(
    gastos: Gastos,
    onClick: (Gastos) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(gastos) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(gastos.suplidor, style = MaterialTheme.typography.titleMedium)
                Text("Monto: ${gastos.monto}")
            }
        }
    }
}
