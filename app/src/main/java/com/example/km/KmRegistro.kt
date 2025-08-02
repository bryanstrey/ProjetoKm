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
    val quantidade: Int,
    val dia: Int
) {
    @Ignore
    constructor(km: Int, dataHora: String, quantidade: Int, dia: Int) : this(0, km, dataHora, quantidade, dia)
}


