package com.example.tech.Empresa.ui.usuarios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tech.Clases.Usuario;
import com.example.tech.Empresa.AdaptadorUsuarios;
import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsuariosFragment extends Fragment {

    private ArrayList<Usuario> usuarioArrayList;
    private FirebaseFirestore firebaseFirestore;

    private ListView lvEmpresasUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        usuarioArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        lvEmpresasUsuarios = (ListView) root.findViewById(R.id.lvEmpresasUsuarios);

        loadDataInListView();

        return root;
    }

    private void loadDataInListView() {
        final EmpresaMainActivity empresaMainActivity = (EmpresaMainActivity) getActivity();
        firebaseFirestore.collection("Usuarios")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Log.i("Lista", list.toString());
                            for (DocumentSnapshot documentSnapshot : list) {
                                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                                String aux = documentSnapshot.toObject(Usuario.class).getNombre();
                                Log.i("Nombre", aux);
                                usuarioArrayList.add(usuario);
                            }

                            AdaptadorUsuarios adaptadorUsuarios = new AdaptadorUsuarios(getContext(), usuarioArrayList);
                            lvEmpresasUsuarios.setAdapter(adaptadorUsuarios);
                        } else {
                            Toast.makeText(getContext(), "No data found.", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail.", Toast.LENGTH_LONG).show();
            }
        });
    }
}