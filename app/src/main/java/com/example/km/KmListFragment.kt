package com.example.km

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.km.databinding.FragmentKmListBinding

// Importar as classes separadamente
import com.example.km.DayHeader
import com.example.km.KmItem

class KmListFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val lista: List<KmRegistro> = listOf(
        KmRegistro(km = 100, dataHora = "2025-07-25", quantidade = 2, dia = 1),
        KmRegistro(km = 150, dataHora = "2025-07-26", quantidade = 5, dia = 2),
        KmRegistro(km = 200, dataHora = "2025-07-27", quantidade = 3, dia = 3)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agrupados: List<KmListItem> = lista
            .groupBy { it.dia }
            .toSortedMap()
            .flatMap { (dia, registros) ->
                listOf(DayHeader(dia)) + registros.map { KmItem(it) }
            }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = KmAdapter(agrupados)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
