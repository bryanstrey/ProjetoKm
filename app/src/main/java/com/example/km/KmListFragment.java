package com.example.km;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.km.databinding.FragmentKmListBinding;

import java.util.Arrays;
import java.util.List;

public class KmListFragment extends Fragment {

    private FragmentKmListBinding binding;

    // Criando a lista de KmRegistro (supondo que KmRegistro seja uma classe Java com construtor apropriado)
    private final List<KmRegistro> lista = Arrays.asList(
            new KmRegistro(100, "2025-07-25 14:00", 2),
            new KmRegistro(150, "2025-07-26 10:30", 5),
            new KmRegistro(200, "2025-07-27 08:00", 3)
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentKmListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.setAdapter(new KmAdapter(lista));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
