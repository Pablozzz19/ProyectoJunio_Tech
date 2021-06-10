package com.example.tech.UsuarioBasico.ui.empresas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tech.Clases.Empresa;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.AdaptadorEmpresas;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmpresasFragment extends Fragment {

    private ArrayList<Empresa> empresaArrayList;
    private FirebaseFirestore firebaseFirestore;

    private ListView lvAgenda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_empresas, container, false);

        empresaArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        lvAgenda = (ListView) root.findViewById(R.id.lvAgenda);

        loadDataInListView();

        return root;
    }

    private void loadDataInListView() {
        firebaseFirestore.collection("Empresas")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Log.i("Lista", list.toString());
                            for (DocumentSnapshot documentSnapshot : list) {
                                Empresa empresa = documentSnapshot.toObject(Empresa.class);
                                String aux = documentSnapshot.toObject(Empresa.class).getNombre();
                                Log.i("Nombre", aux);
                                Log.i("UsuarioDocument",empresa.getNombre() + " " + empresa.getDescripcion());
                                empresaArrayList.add(empresa);
                            }

                            AdaptadorEmpresas adaptadorEmpresas = new AdaptadorEmpresas(getContext(), empresaArrayList);
                            lvAgenda.setAdapter(adaptadorEmpresas);
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