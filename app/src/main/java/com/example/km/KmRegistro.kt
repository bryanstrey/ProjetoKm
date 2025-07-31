package com.example.km

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity(tableName = "km_registro")
data class KmRegistro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val km: Int,
    val dataHora: String,
    val quantidade: Int
) {
    @Ignore // IGNORA esse construtor secundário para Room
    constructor(km: Int, dataHora: String, quantidade: Int) : this(0, km, dataHora, quantidade)
}

