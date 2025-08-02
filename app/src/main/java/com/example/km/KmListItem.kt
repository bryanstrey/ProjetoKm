package com.example.km

sealed interface KmListItem

data class TotalKmItem(val totalKm: Int) : KmListItem

data class DayHeader(val dia: Int) : KmListItem

data class KmItem(val registro: KmRegistro) : KmListItem
