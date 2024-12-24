package com.example.ucp2pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryJdw
import com.example.ucp2pam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJdw: RepositoryJdw
) : ViewModel() {

    var updateUiState by mutableStateOf(JdUiState())
        private set

    private val _idAntrian: String = checkNotNull(savedStateHandle[DestinasiUpdate.idAntrian])

    init {
        viewModelScope.launch {
            updateUiState = repositoryJdw.getJdw(_idAntrian)
                .filterNotNull()
                .first()
                .toUiStateMk()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateUiState = updateUiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }

    fun validateFieldsJd(): Boolean {
        val event = updateUiState.jadwalEvent
        val errorState = FormErrorStateJd(
            idAntrian = if (event.idAntrian.isNotEmpty()) null else "ID Tidak Boleh Kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "NAMA DOKTER Tidak Boleh Kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "NAMA PASIEN Tidak Boleh Kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "NO HP Tidak Boleh Kosong",
            tanggal = if (event.tanggal.isNotEmpty()) null else "TANGGAL Tidak Boleh Kosong",
            status = if (event.status.isNotEmpty()) null else "STATUS Tidak Boleh Kosong",
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(
        status: String
    ) {
        val currentEvent = updateUiState.jadwalEvent

        if (validateFieldsJd()) {
            viewModelScope.launch {
                try {
                    // Log untuk memastikan data yang akan diperbarui
                    println("Memperbarui data: $currentEvent")

                    repositoryJdw.updateJdw(currentEvent.toJadwalEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        jadwalEvent = JadwalEvent(), // Reset form
                        isEntryValid = FormErrorStateJd() // Reset error state
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Gagal Diupdate: ${e.message}"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data Tidak Valid. Periksa kembali input Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUiStateMk(): JdUiState = JdUiState(
    jadwalEvent = this.toDetailUiEvent()
)