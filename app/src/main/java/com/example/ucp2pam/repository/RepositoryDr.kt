package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDr {

    suspend fun insertDr(dokter: Dokter)

    // getAllDokter
    fun getAllDr(): Flow<List<Dokter>>

    // getDokter
    fun getDr(idDr: String): Flow<Dokter>
}