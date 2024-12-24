package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.data.dao.JadwalDao
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJdw(
    private val jadwalDao: JadwalDao
) : RepositoryJdw {

    override suspend fun insertJdw(jadwal: Jadwal) {
        jadwalDao.insertJd(jadwal)
    }

    override fun getAllJdw(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJd()
    }

    override fun getJdw(idAntrian: String): Flow<Jadwal> {
        return jadwalDao.getJd(idAntrian)
    }

    override suspend fun deleteJdw(jadwal: Jadwal) {
        jadwalDao.deleteJd(jadwal)
    }

    override suspend fun updateJdw(jawal: Jadwal) {
        jadwalDao.updateJd(jawal)
    }
}

