package com.example.km

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.km.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.*

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KmViewModel by viewModels {
        KmViewModelFactory(requireActivity().application)
    }

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Abre o DatePicker ao clicar no campo de data
        binding.editTextDataHora.setOnClickListener {
            showDatePicker()
        }

        binding.buttonSave.setOnClickListener {
            val kmText = binding.editTextKm.text.toString()
            val dataText = binding.editTextDataHora.text.toString()
            val quantidadeText = binding.editTextQuantidade.text.toString()

            if (kmText.isBlank() || dataText.isBlank() || quantidadeText.isBlank()) {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val km = kmText.toIntOrNull()
            val quantidade = quantidadeText.toIntOrNull()

            if (km == null || quantidade == null) {
                Toast.makeText(context, "KM e Quantidade devem ser números válidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Salva somente a data no formato yyyy-MM-dd
            val novoRegistro = KmRegistro(km = km, dataHora = dataText, quantidade = quantidade)

            viewModel.adicionarRegistro(novoRegistro)

            Toast.makeText(context, "Registro salvo", Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        }
    }

    private fun showDatePicker() {
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Atualiza o campo com a data formatada
            binding.editTextDataHora.setText(dateFormat.format(calendar.time))
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
