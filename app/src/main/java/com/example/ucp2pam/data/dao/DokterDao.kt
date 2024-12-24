package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {


    // get all data
    @Query("SELECT * FROM Dokter")
    fun getAllDokter(): Flow<List<Dokter>>

    // get dokter
    @Query("SELECT * FROM dokter WHERE idDr = :idDr")
    fun getDokter(idDr: String): Flow<Dokter>

    // create dokter
    @Insert
    suspend fun insertDokter(dokter: Dokter)
}

