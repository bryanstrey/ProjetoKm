package com.example.km

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.km.databinding.FragmentKmListBinding
import java.text.SimpleDateFormat
import java.util.*

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
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter

        // Exibe todos os registros inicialmente
        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                adapter.updateData(lista)
            }
        }

        // Botão flutuante para adicionar novo item
        binding.fabAddKm.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // Botão de filtro por data
        binding.btnFiltrarData.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = format.format(selectedDate.time)

                filtrarPorData(dateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    private fun filtrarPorData(dataSelecionada: String) {
        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                val filtrados = lista.filter {
                    it.dataHora == dataSelecionada
                }
                adapter.updateData(filtrados)
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
