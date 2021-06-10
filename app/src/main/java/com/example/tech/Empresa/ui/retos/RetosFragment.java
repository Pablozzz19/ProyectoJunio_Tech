package com.example.tech.Empresa.ui.retos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tech.Clases.Reto;
import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.AdaptadorRetos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RetosFragment extends Fragment {

    private ArrayList<Reto> retoArrayList;
    private FirebaseFirestore firebaseFirestore;

    private ListView lvEmpresasRetos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_retos_empresas, container, false);

        retoArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        lvEmpresasRetos = (ListView) root.findViewById(R.id.lvEmpresasRetos);

        loadDataInListView();

        return root;
    }

    private void loadDataInListView() {
        final EmpresaMainActivity empresaMainActivity = (EmpresaMainActivity) getActivity();
        firebaseFirestore.collection("Retos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Log.i("Lista", list.toString());
                            for (DocumentSnapshot documentSnapshot : list) {
                                Reto reto = documentSnapshot.toObject(Reto.class);
                                String aux = documentSnapshot.toObject(Reto.class).getNombre();
                                Log.i("Nombre", aux);
                                if (reto.getEmpresaId().equalsIgnoreCase(empresaMainActivity.getEmpresaId())) {
                                    retoArrayList.add(reto);
                                }
                            }

                            AdaptadorRetos adaptadorRetos = new AdaptadorRetos(getContext(), retoArrayList);
                            lvEmpresasRetos.setAdapter(adaptadorRetos);
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