package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {

    // get all Jadwal
    @Query("SELECT * FROM jadwal")
    fun getAllJd(): Flow<List<Jadwal>>

    // get jadwal
    @Query("SELECT * FROM jadwal WHERE idAntrian = :idAntrian")
    fun getJd(idAntrian: String): Flow<Jadwal>

    // delete jadwal
    @Delete
    suspend fun deleteJd(jadwal: Jadwal)

    // update Jadwal
    @Update
    suspend fun updateJd(jadwal: Jadwal)

    @Insert
    suspend fun insertJd(jadwal: Jadwal)
}

