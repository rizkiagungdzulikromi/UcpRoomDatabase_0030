package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryDr
import com.example.ucp2pam.repository.RepositoryJdw
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repositoryDr: RepositoryDr,
    private val repositoryJdw: RepositoryJdw
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                // Mengumpulkan data dari Flow
                val dokterList = repositoryDr.getAllDr().first()
                val jadwalList = repositoryJdw.getAllJdw().first()

                // Memperbarui state UI
                _uiState.value = _uiState.value.copy(
                    dokterList = dokterList,
                    jadwalList = jadwalList,
                    isLoading = false
                )
            } catch (e: Exception) {
                // Menangani kesalahan
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Terjadi kesalahan saat memuat data"
                )
            }
        }
    }
}

data class HomeUiState(
    val dokterList: List<Dokter> = emptyList(),
    val jadwalList: List<Jadwal> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)