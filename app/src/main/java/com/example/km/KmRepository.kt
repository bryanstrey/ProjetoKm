package com.example.km

import com.example.km.KmRegistro
import com.example.km.KmRegistroDao


class KmRepository(private val dao: KmRegistroDao) {

    val registros = dao.getAll()

    suspend fun adicionar(registro: KmRegistro) {
        dao.insert(registro)
    }

    suspend fun excluir(registro: KmRegistro) {
        dao.excluir(registro)
    }

    suspend fun excluirPorIds(ids: List<Long>) {
        dao.deleteByIds(ids)
    }
}

