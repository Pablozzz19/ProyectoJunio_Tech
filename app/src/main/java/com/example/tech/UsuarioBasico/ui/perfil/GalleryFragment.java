package com.example.tech.UsuarioBasico.ui.perfil;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tech.Account.CrearCuentaActivity;
import com.example.tech.R;

public class GalleryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Toast.makeText(getContext(), bundle.getString("UserId"), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Mal", Toast.LENGTH_LONG).show();
        }
        return root;
    }
}