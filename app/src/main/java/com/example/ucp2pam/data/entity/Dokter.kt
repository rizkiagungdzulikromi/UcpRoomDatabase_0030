package com.example.ucp2pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Dokter")
data class Dokter(
    @PrimaryKey
    val idDr: String,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val noHp: String,
    val JamKerja: String
)



