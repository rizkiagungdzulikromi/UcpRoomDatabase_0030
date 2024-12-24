package com.example.ucp2pam.ui.view.dokter

import AppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.ui.viewmodel.JadwalViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel


@Composable
fun JadwalView(
    onBack: () -> Unit,
    onAddJadwal: () -> Unit = {},
    onClick: () -> Unit,
    onDetail: (String) -> Unit,
    onNavigateJadwal: () -> Unit,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AppBar(
                title = "Jadwal",
                showBackButton = true,
                onBack = onBack,
                modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddJadwal,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(15.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Jadwal",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        val jdUiState by viewModel.jdUiState.collectAsState()

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
            when {
                jdUiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                jdUiState.listJd.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tidak Ada Jadwal.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(jdUiState.listJd) { jd ->
                            CardJd(
                                jd = jd,
                                onClick = {onDetail(jd.idAntrian) }
                            )
                        }
                    }
                }
            }
        }
    }
}

