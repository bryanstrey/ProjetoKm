package com.example.km

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class KmViewModel(private val repository: KmRepository) : ViewModel() {

    val registros: Flow<List<KmRegistro>> = repository.registros

    fun adicionarRegistro(registro: KmRegistro) {
        viewModelScope.launch {
            repository.adicionar(registro)
        }
    }

    fun excluirRegistro(registro: KmRegistro) {
        viewModelScope.launch {
            repository.excluir(registro)
        }
    }

    fun excluirRegistrosPorId(ids: List<Long>) {
        viewModelScope.launch {
            repository.excluirPorIds(ids)
        }
    }


}
