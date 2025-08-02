package com.example.km

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.km.databinding.FragmentKmListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KmViewModel by viewModels {
        KmViewModelFactory(requireActivity().application)
    }

    private lateinit var adapter: KmAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = KmAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val dias = (1..5).map { "Dia $it" }
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dias)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDiaInicio.adapter = spinnerAdapter
        binding.spinnerDiaFim.adapter = spinnerAdapter

        // Exibe todos os registros inicialmente com agrupamento por dia
        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                val agrupados = agruparPorDia(lista)
                adapter.updateData(agrupados)
            }
        }

        binding.fabAddKm.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.btnFiltrarDias.setOnClickListener {
            val diaInicio = binding.spinnerDiaInicio.selectedItem.toString().removePrefix("Dia ").toIntOrNull()
            val diaFim = binding.spinnerDiaFim.selectedItem.toString().removePrefix("Dia ").toIntOrNull()

            if (diaInicio == null || diaFim == null || diaInicio > diaFim) {
                return@setOnClickListener
            }

            filtrarPorIntervaloDeDias(diaInicio, diaFim)
        }
    }

    private fun filtrarPorIntervaloDeDias(diaInicio: Int, diaFim: Int) {
        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                val filtrados = lista.filter { it.dia in diaInicio..diaFim }
                val agrupados = agruparPorDia(filtrados)
                adapter.updateData(agrupados)
            }
        }
    }

    private fun agruparPorDia(lista: List<KmRegistro>): List<KmListItem> {
        return lista
            .groupBy { it.dia }
            .toSortedMap() // Ordena por dia
            .flatMap { (dia, registros) ->
                val header = DayHeader(dia)
                val items = registros.map { KmItem(it) }
                listOf<KmListItem>(header) + items
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
