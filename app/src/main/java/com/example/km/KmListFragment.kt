package com.example.km

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.km.databinding.FragmentKmListBinding

class KmListFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val lista: MutableList<KmRegistro> = mutableListOf(
        KmRegistro(km = 100, dataHora = "2025-07-25", localSaida = "Centro", localChegada = "Bairro A", dia = 1),
        KmRegistro(km = 150, dataHora = "2025-07-26", localSaida = "Bairro B", localChegada = "Centro", dia = 2),
        KmRegistro(km = 200, dataHora = "2025-07-27", localSaida = "Escritório", localChegada = "Casa", dia = 3)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        atualizarLista()
    }

    private fun atualizarLista() {
        val agrupados: List<KmListItem> = lista
            .groupBy { it.dia }
            .toSortedMap()
            .flatMap { (dia, registros) ->
                listOf(DayHeader(dia)) + registros.map { KmItem(it) }
            }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = KmAdapter(agrupados, onDeleteClick = { registro ->
                lista.remove(registro)
                atualizarLista()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
