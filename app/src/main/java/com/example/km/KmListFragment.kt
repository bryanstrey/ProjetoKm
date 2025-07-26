
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.km.KmAdapter
import com.example.km.KmRegistro
import com.example.km.databinding.FragmentKmListBinding


class KmListFragment : Fragment() {

    private var _binding: FragmentKmListBinding? = null
    private val binding get() = _binding!!

    private val lista = listOf(
        KmRegistro(id = 1, km = 100, dataHora = "2025-07-25 14:00", quantidade = 2),
        KmRegistro(id = 2, km = 150, dataHora = "2025-07-26 10:30", quantidade = 5),
        KmRegistro(id = 3, km = 200, dataHora = "2025-07-27 08:00", quantidade = 3)
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = KmAdapter(lista)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
