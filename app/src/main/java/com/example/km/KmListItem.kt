package com.example.km

// Interface base
sealed interface KmListItem

// Cabeçalho de cada dia
data class DayHeader(val dia: Int) : KmListItem

// Item com os dados reais
data class KmItem(val registro: KmRegistro) : KmListItem
