package com.example.ucp2pam.ui.view.dokter

import AppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.viewmodel.DokterViewModel
import com.example.ucp2pam.ui.viewmodel.FormErrorStateJd
import com.example.ucp2pam.ui.viewmodel.JadwalEvent
import com.example.ucp2pam.ui.viewmodel.JadwalViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertJadwalView(
    onBack: () -> Unit,
    onNavigateJadwal: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessageJd()
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AppBar(
                title = "Tambah Jadwal",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                        text = "Data Jadwal",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    FormJd(
                        jadwalEvent = uiState.jadwalEvent,
                        onValueChange = { viewModel.updateState(it) },
                        errorState = uiState.isEntryValid,
                        onNavigateJadwal = { },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.saveDataJd()
                            onNavigateJadwal()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Simpan Data")
                    }
                }
            }
        }
    }
}

@Composable
fun FormJd(
    jadwalEvent: JadwalEvent,
    onValueChange: (JadwalEvent) -> Unit,
    onNavigateJadwal: () -> Unit,
    errorState: FormErrorStateJd,
    modifier: Modifier = Modifier,
    dokterViewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val dokterUiState by dokterViewModel.dokterUiState.collectAsState()
    val dokterList = dokterUiState.listDr.map { it.nama }
    val statusList = listOf("Emergency", "Not Emergency")

    Column(modifier = modifier.fillMaxWidth().padding(20.dp)) {
        OutlinedTextField(
            value = jadwalEvent.idAntrian,
            onValueChange = { onValueChange(jadwalEvent.copy(idAntrian = it)) },
            label = { Text("ID ANTRIAN") },
            isError = errorState.idAntrian != null,
            placeholder = { Text("Masukkan ID ANTRIAN") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = errorState.idAntrian ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = jadwalEvent.namaPasien,
            onValueChange = { onValueChange(jadwalEvent.copy(namaPasien = it)) },
            label = { Text("Nama Pasien") },
            isError = errorState.namaPasien != null,
            placeholder = { Text("Masukkan Nama Pasien") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(15.dp))
        DropdownMenuField(
            label = "STATUS",
            options = statusList,
            selectedOption = jadwalEvent.status,
            onOptionSelected = { onValueChange(jadwalEvent.copy(status = it)) }
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = jadwalEvent.tanggal,
            onValueChange = { onValueChange(jadwalEvent.copy(tanggal = it)) },
            label = { Text("tanggal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.tanggal != null,
            placeholder = { Text("Masukkan Tanggal") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = errorState.tanggal ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = jadwalEvent.noHp,
            onValueChange = { onValueChange(jadwalEvent.copy(noHp = it)) },
            label = { Text("NO TELP") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan NO TELP") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(15.dp))
        DropdownMenuField(
            label = "DOKTER",
            options = dokterList,
            selectedOption = jadwalEvent.namaDokter,
            onOptionSelected = { onValueChange(jadwalEvent.copy(namaDokter = it)) }
        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}