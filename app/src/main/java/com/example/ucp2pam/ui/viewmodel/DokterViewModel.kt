package com.example.ucp2pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.repository.RepositoryDr
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DokterViewModel(private val repositoryDr: RepositoryDr) : ViewModel() {

    var uiState by mutableStateOf(DokterUiState())

    fun updateState(dokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = dokterEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            idDr = if (event.idDr.isNotEmpty()) null else "IDDR Tidak Boleh Kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak Boleh Kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Spesialis Tidak Boleh Kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.dokterEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDr.insertDr(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dokterEvent = DokterEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }

    val dokterUiState: StateFlow<DokterUiState> = repositoryDr.getAllDr()
        .filterNotNull()
        .map {
            DokterUiState(
                listDr = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DokterUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                DokterUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DokterUiState(
                isLoading = true,
            )
        )
}

data class DokterUiState(
    val listDr:List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class FormErrorState(
    val idDr: String? = null,
    val nama: String? = null,
    val spesialis: String? = null
) {
    fun isValid(): Boolean {
        return idDr == null && nama == null && spesialis == null
    }
}

fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    idDr = idDr,
    nama = nama,
    spesialis = spesialis,
    JamKerja = Jamkerja,
    klinik = klinik,
    noHp = noHp
)

data class DokterEvent(
    val idDr: String = "",
    val nama: String = "",
    val spesialis: String = "",
    val Jamkerja: String = "",
    val klinik: String = "",
    val noHp: String = ""
)