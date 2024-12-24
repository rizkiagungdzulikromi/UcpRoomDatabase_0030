package com.example.ucp2pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryJdw
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JadwalViewModel(private val repositoryJdw: RepositoryJdw) : ViewModel() {

    var uiState by mutableStateOf(JdUiState())

    fun updateState(jadwalEvent: JadwalEvent) {
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    val jdUiState: StateFlow<JdUiState> = repositoryJdw.getAllJdw()
        .filterNotNull()
        .map {
            JdUiState(
                listJd = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(JdUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                JdUiState(
                    isLoading =false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = JdUiState(
                isLoading = true,
            )
        )

    private fun validateFieldsJd(): Boolean {
        val event = uiState.jadwalEvent
        val errorState = FormErrorStateJd(
            idAntrian = if (event.idAntrian.isNotEmpty()) null else "ID Tidak Boleh Kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama DOKTER Tidak Boleh Kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "NAMA PASIEN TIDAK Boleh Kosong",
            noHp= if (event.noHp.isNotEmpty()) null else "NO HP Tidak Boleh Kosong",
            tanggal = if (event.tanggal.isNotEmpty()) null else "TANGGAL Tidak Boleh Kosong",
            status = if (event.status.isNotEmpty()) null else "STATUS Tidak Boleh Kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveDataJd() {
        val currentEvent = uiState.jadwalEvent
        if (validateFieldsJd()) {
            viewModelScope.launch {
                try {
                    repositoryJdw.insertJdw(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJd()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input Tidak Valid. Periksa Kembali Data Anda"
            )
        }
    }

    fun resetSnackbarMessageJd() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class JdUiState(
    val listJd:List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJd = FormErrorStateJd(),
    val snackBarMessage: String? = null
)

data class FormErrorStateJd(
    val idAntrian: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggal: String? = null,
    val status: String? = null
) {
    fun isValid(): Boolean {
        return idAntrian == null && namaDokter == null && namaPasien == null && noHp == null
                && tanggal == null && status == null
    }
}

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    idAntrian = idAntrian,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggal = tanggal,
    status = status
)

data class JadwalEvent(
    val idAntrian: String = "",
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggal: String = "",
    val status: String = ""
)