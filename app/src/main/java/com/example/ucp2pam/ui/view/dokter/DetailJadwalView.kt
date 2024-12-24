package com.example.ucp2pam.ui.view.dokter

import AppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.ui.viewmodel.DetailJadwalViewModel
import com.example.ucp2pam.ui.viewmodel.DetailUiState
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.toJadwalEntity

@Composable
fun DetailJadwalView(
    modifier: Modifier = Modifier,
    viewModel: DetailJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { },
    idAntrian: String
) {
    val detailUiState by viewModel.detailUiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = "Detail Jadwal",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    detailUiState.detailUiEvent?.idAntrian?.let(onEditClick)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jadwal"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailJd(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteJdw()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailJd(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            detailUiState.isLoading -> {
                CircularProgressIndicator()
            }

            detailUiState.detailUiEvent != null -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    ItemDetailJd(
                        jadwal = detailUiState.detailUiEvent.toJadwalEntity()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { deleteConfirmationRequired = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Delete", color = Color.White)
                    }
                }
            }

            else -> {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }
        }

        if (deleteConfirmationRequired) {
            DeleteConfDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteClick()
                },
                onDeleteCancel = {
                    deleteConfirmationRequired = false
                }
            )
        }
    }
}

@Composable
fun ItemDetailJd(
    modifier: Modifier = Modifier,
    jadwal: Jadwal
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailJd(judul = "ID ANTRIAN", data = jadwal.idAntrian)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJd(judul = "Nama DR", data = jadwal.namaDokter)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJd(judul = "NAMA PASIEN", data = jadwal.namaPasien)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJd(judul = "NO HP", data = jadwal.noHp)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJd(judul = "TANGGAL", data = jadwal.tanggal)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJd(judul = "STATUS", data = jadwal.status)
        }
    }
}

@Composable
fun ComponentDetailJd(
    modifier: Modifier = Modifier,
    judul: String,
    data: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = judul,
            Modifier.weight(0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = ":",
            Modifier.weight(0.2f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = data,
            Modifier.weight(2f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Composable
private fun DeleteConfDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}