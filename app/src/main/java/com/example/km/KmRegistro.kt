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
    val localSaida: String,
    val localChegada: String,
    val dia: Int
) {
    @Ignore
    constructor(km: Int, dataHora: String, localSaida: String, localChegada: String, dia: Int) :
            this(0, km, dataHora, localSaida, localChegada, dia)
}
