package edu.ucne.nathiel_taveras_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.nathiel_taveras_ap2_p2.Presentation.Edit.EditGastosScreen
import edu.ucne.nathiel_taveras_ap2_p2.Presentation.List.ListGastosScreen
import edu.ucne.nathiel_taveras_ap2_p2.ui.theme.Nathiel_Taveras_AP2_P2Theme
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nathiel_Taveras_AP2_P2Theme {
                val navController = rememberNavController()

                var showEditGastos by remember { mutableStateOf(false) }
                var selectedGastoId by remember { mutableIntStateOf(1) }

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            DrawerContent(navController = navController) {
                                scope.launch { drawerState.close() }
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Registro de Gastos") },

                            )
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "home"
                            ) {
                                composable("home") {
                                    HomeScreen(navController = navController)
                                }

                                composable("gastoList") {
                                    ListGastosScreen(
                                        onNavigateToEdit = { id ->
                                            selectedGastoId = id
                                            showEditGastos = true
                                        },
                                        onNavigateToCreate = {
                                            selectedGastoId = id
                                            showEditGastos = true
                                        }
                                    )
                                }
                            }

                            if (showEditGastos) {
                                EditGastosScreen(
                                    gastoId = selectedGastoId,
                                    onDismiss = { showEditGastos = false }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Menú Principal",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Divider()

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "home",
            onClick = {
                navController.navigate("home")
                onItemClick()
            }
        )

        NavigationDrawerItem(
            label = { Text("Gastos") },
            selected = navController.currentDestination?.route == "gastoList",
            onClick = {
                navController.navigate("gastoList")
                onItemClick()
            }
        )
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("gastoList") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Registro de Gastos")
            }
        }
    }
}