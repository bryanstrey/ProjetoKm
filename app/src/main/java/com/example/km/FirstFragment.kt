import android.os.Bundle
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
import com.example.km.KmAdapter
import com.example.km.KmViewModel
import com.example.km.R
import com.example.km.KmRegistro


class FirstFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KmViewModel by viewModels()

    private lateinit var adapter: KmAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = KmAdapter(emptyList<KmRegistro>())

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.registros.collectLatest { lista ->
                adapter.updateData(lista)
            }
        }

        binding.fabAddKm.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
