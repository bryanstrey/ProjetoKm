package com.example.km

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.km.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val kmText = binding.editTextKm.text.toString()
            val dataHoraText = binding.editTextDataHora.text.toString()
            val quantidadeText = binding.editTextQuantidade.text.toString()

            if (kmText.isBlank() || dataHoraText.isBlank() || quantidadeText.isBlank()) {
                Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val km = kmText.toIntOrNull()
            val quantidade = quantidadeText.toIntOrNull()

            if (km == null || quantidade == null) {
                Toast.makeText(context, "KM e Quantidade devem ser números válidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novoRegistro = KmRegistro(km, dataHoraText, quantidade)

            // Aqui você pode salvar o registro, enviar para outra tela, etc.
            // Por enquanto, só vamos voltar para a tela anterior
            Toast.makeText(context, "Registro salvo: $novoRegistro", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
