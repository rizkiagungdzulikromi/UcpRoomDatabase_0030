package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDr(
    private val dokterDao: DokterDao
) : RepositoryDr {

    override suspend fun insertDr(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDr(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDr(idDr: String): Flow<Dokter> {
        return dokterDao.getDokter(idDr)
    }
}

