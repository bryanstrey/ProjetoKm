package com.example.km

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KmRegistroDao {

    @Query("SELECT * FROM km_registro ORDER BY id DESC")
    fun getAll(): Flow<List<KmRegistro>>

    @Insert
    suspend fun insert(kmRegistro: KmRegistro)

    @Delete
    suspend fun excluir(km: KmRegistro)

    // Método para excluir múltiplos registros pelo ID
    @Query("DELETE FROM km_registro WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)
}
