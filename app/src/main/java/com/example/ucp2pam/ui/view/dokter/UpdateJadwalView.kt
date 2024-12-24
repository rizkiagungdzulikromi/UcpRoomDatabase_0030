package com.example.ucp2pam.ui.view.dokter

import AppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.viewmodel.DokterViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.UpdateJadwalViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateJadwalView(
    onBack: () -> Unit,
    onNavigateJadwal: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    dokterViewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.updateUiState
    val dokterUiState by dokterViewModel.dokterUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var selectedDokter by remember { mutableStateOf("") }

    LaunchedEffect(dokterUiState.listDr) {
        if (dokterUiState.listDr.isNotEmpty()) {
            selectedDokter = dokterUiState.listDr.first().nama
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBar(
                title = "EDIT JADWAL",
                showBackButton = true,
                onBack = onBack
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1B000000),
                            Color(0xFFFFFFFF),
                            Color(0xFFFF0000)
                        )
                    )
                )
        ) {
            Card(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Edit Jadwal",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    FormJd(
                        jadwalEvent = uiState.jadwalEvent,
                        onValueChange = viewModel::updateState,
                        errorState = uiState.isEntryValid,
                        onNavigateJadwal = { },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.validateFieldsJd()) {
                                    viewModel.updateData(status = selectedDokter)
                                    onNavigateJadwal()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Update Data")
                    }
                }
            }
        }
    }
}