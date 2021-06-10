package com.example.tech.UsuarioBasico.ui.ajustes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tech.Account.LoginActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;

public class AjustesFragment extends Fragment {

    private LinearLayout llAyuda, llEditarPerfil, llSobreNostros, llCerrarSesion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);

        llAyuda = (LinearLayout) root.findViewById(R.id.llAyuda);
        llEditarPerfil = (LinearLayout) root.findViewById(R.id.llEditarPerfil);
        llSobreNostros = (LinearLayout) root.findViewById(R.id.llSobreNostros);
        llCerrarSesion = (LinearLayout) root.findViewById(R.id.llCerrarSesion);

        final UsuarioMainActivity usuarioMainActivity = (UsuarioMainActivity) getActivity();

        llAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AyudaActivity.class);
                startActivity(intent);
            }
        });

        llEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarCuentaUsuarioActivity.class);
                intent.putExtra("UsuarioId", usuarioMainActivity.getUsuarioId());
                startActivity(intent);
            }
        });

        llSobreNostros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SobreNosotrosActivity.class);
                startActivity(intent);
            }
        });

        llCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}