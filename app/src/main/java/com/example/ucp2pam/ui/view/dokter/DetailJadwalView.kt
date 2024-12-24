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

