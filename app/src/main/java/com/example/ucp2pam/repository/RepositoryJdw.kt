package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJdw {

    suspend fun insertJdw(jadwal: Jadwal)

    // getAllJdw
    fun getAllJdw(): Flow<List<Jadwal>>

    // getJdw
    fun getJdw(idAntrian: String): Flow<Jadwal>

    // deleteJdw
    suspend fun deleteJdw(jadwal: Jadwal)

    // updateJdw
    suspend fun updateJdw(jadwal: Jadwal)
}