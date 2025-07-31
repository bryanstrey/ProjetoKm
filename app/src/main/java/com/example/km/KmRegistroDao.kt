package com.example.km

import com.example.km.KmRegistro
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface KmRegistroDao {

    @Query("SELECT * FROM km_registro ORDER BY id DESC")
    fun getAll(): Flow<List<KmRegistro>>

    @Insert
    suspend fun insert(kmRegistro: KmRegistro)
}
