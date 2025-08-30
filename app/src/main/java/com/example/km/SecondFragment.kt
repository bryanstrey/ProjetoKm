package com.example.km

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))


    private val diasSemana = listOf(
        "Segunda-feira",
        "Terça-feira",
        "Quarta-feira",
        "Quinta-feira",
        "Sexta-feira",
        "Sábado",
        "Domingo"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, diasSemana)
        binding.autoCompleteDiaSemana.setAdapter(adapter)

        binding.autoCompleteDiaSemana.setText(diasSemana[0], false)

        binding.autoCompleteDiaSemana.setOnClickListener {
            binding.autoCompleteDiaSemana.showDropDown()
        }

        binding.editTextDataHora.setOnClickListener {
            showDatePicker()
        }

        binding.buttonSave.setOnClickListener {
            salvarRegistro()
        }
    }

    private fun salvarRegistro() {
        val kmText = binding.editTextKm.text.toString()
        val dataText = binding.editTextDataHora.text.toString()
        val localSaidaText = binding.editTextLocalSaida.text.toString()
        val localChegadaText = binding.editTextLocalChegada.text.toString()
        val diaSelecionado = binding.autoCompleteDiaSemana.text.toString()

        if (kmText.isBlank() || dataText.isBlank() || localSaidaText.isBlank() || localChegadaText.isBlank() || diaSelecionado.isBlank()) {
            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val km = kmText.toIntOrNull()
        if (km == null) {
            Toast.makeText(context, "KM deve ser um número válido", Toast.LENGTH_SHORT).show()
            return
        }

        val dia = diasSemana.indexOf(diaSelecionado) + 1
        if (dia == 0) {
            Toast.makeText(context, "Informe um dia válido da semana", Toast.LENGTH_SHORT).show()
            return
        }

        val novoRegistro = KmRegistro(
            km = km,
            dataHora = dataText,
            localSaida = localSaidaText,
            localChegada = localChegadaText,
            dia = dia
        )

        viewModel.adicionarRegistro(novoRegistro)

        Toast.makeText(context, "Registro salvo", Toast.LENGTH_SHORT).show()

        findNavController().navigateUp()
    }

    private fun showDatePicker() {
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            binding.editTextDataHora.setText(dateFormat.format(calendar.time))
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
