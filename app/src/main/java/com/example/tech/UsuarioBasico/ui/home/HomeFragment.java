package com.example.tech.UsuarioBasico.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tech.R;

public class HomeFragment extends Fragment {

    TextView tvPrueba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvPrueba = (TextView) root.findViewById(R.id.tvPrueba);
        tvPrueba.setText("AAAAAA");

        return root;
    }
}