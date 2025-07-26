package com.example.km

import com.example.km.KmRegistro
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KmViewModel : ViewModel() {
    private val _registros = MutableStateFlow<List<KmRegistro>>(emptyList())
    val registros: StateFlow<List<KmRegistro>> = _registros

    fun adicionarRegistro(registro: KmRegistro) {
        _registros.value = _registros.value + registro
    }
}
