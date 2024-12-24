package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import kotlin.concurrent.Volatile

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class KlinikDatabase: RoomDatabase() {
    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var Instance: KlinikDatabase? = null

        fun getDatabase(context: Context): KlinikDatabase {
            return(Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    KlinikDatabase::class.java,
                    name ="KlinikDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
