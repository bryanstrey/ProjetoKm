package com.example.km

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.km.databinding.FragmentKmListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FirstFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KmViewModel by viewModels {
        KmViewModelFactory(requireActivity().application)
    }

    private lateinit var adapter: KmAdapter

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var diaInicioSelecionado: Int? = null
    private var diaFimSelecionado: Int? = null
    private var dataInicioSelecionada: String? = null
    private var dataFimSelecionada: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = KmAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        // Dias da semana (segunda a sexta)
        val diasSemana = listOf("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diasSemana)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDiaInicio.adapter = spinnerAdapter
        binding.spinnerDiaFim.adapter = spinnerAdapter

        // Inicializa seleções padrão
        binding.spinnerDiaInicio.setSelection(0)
        binding.spinnerDiaFim.setSelection(diasSemana.size - 1)
        diaInicioSelecionado = 1 // Segunda-feira
        diaFimSelecionado = 5    // Sexta-feira

        binding.spinnerDiaInicio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                diaInicioSelecionado = position + 1
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerDiaFim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                diaFimSelecionado = position + 1
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.tvDataInicioFiltro.setOnClickListener {
            showDatePicker(isDataInicio = true)
        }

        binding.tvDataFimFiltro.setOnClickListener {
            showDatePicker(isDataInicio = false)
        }

        binding.btnFiltrarDias.setOnClickListener {
            aplicarFiltros()
        }

        carregarDadosSemFiltro()

        binding.fabAddKm.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    private fun carregarDadosSemFiltro() {
        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                val listaFormatada = formatarListaComHeaders(lista)
                adapter.updateData(listaFormatada)
            }
        }
    }

    private fun aplicarFiltros() {
        val diaInicio = diaInicioSelecionado ?: 1
        val diaFim = diaFimSelecionado ?: 5

        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                var filtrados = lista.filter { it.dia in diaInicio..diaFim }

                if (dataInicioSelecionada != null && dataFimSelecionada != null) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val dataInicio = sdf.parse(dataInicioSelecionada!!)
                    val dataFim = sdf.parse(dataFimSelecionada!!)

                    filtrados = filtrados.filter {
                        val dataRegistro = sdf.parse(it.dataHora)
                        dataRegistro != null &&
                                !dataRegistro.before(dataInicio) &&
                                !dataRegistro.after(dataFim)
                    }
                }

                val listaFormatada = formatarListaComHeaders(filtrados)
                adapter.updateData(listaFormatada)
            }
        }
    }

    private fun formatarListaComHeaders(lista: List<KmRegistro>): List<KmListItem> {
        val totalKm = lista.sumOf { it.km }
        val totalItem = TotalKmItem(totalKm)

        val diasSemanaMap = mapOf(
            1 to "Segunda-feira",
            2 to "Terça-feira",
            3 to "Quarta-feira",
            4 to "Quinta-feira",
            5 to "Sexta-feira"
        )

        return listOf(totalItem) + lista
            .groupBy { it.dia }
            .toSortedMap()
            .flatMap { (dia: Int, registros: List<KmRegistro>) ->
                val nomeDia = diasSemanaMap[dia] ?: "Dia $dia"
                listOf(DayHeaderNome(nomeDia)) + registros.map { KmItem(it) }
            }
    }



    private fun showDatePicker(isDataInicio: Boolean) {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dataFormatada = dateFormat.format(calendar.time)
                if (isDataInicio) {
                    dataInicioSelecionada = dataFormatada
                    binding.tvDataInicioFiltro.text = dataFormatada
                } else {
                    dataFimSelecionada = dataFormatada
                    binding.tvDataFimFiltro.text = dataFormatada
                }
                aplicarFiltros()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
