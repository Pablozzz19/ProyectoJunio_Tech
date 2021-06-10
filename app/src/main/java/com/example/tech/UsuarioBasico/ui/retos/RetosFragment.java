package com.example.tech.UsuarioBasico.ui.retos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tech.Clases.Reto;
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

    private ListView lvUsuarioRetos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_retos, container, false);

        retoArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        lvUsuarioRetos = (ListView) root.findViewById(R.id.lvUsuarioRetos);

        loadDataInListView();

        return root;
    }

    private void loadDataInListView() {
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
                                retoArrayList.add(reto);
                            }

                            AdaptadorRetos adaptadorRetos = new AdaptadorRetos(getContext(), retoArrayList);
                            lvUsuarioRetos.setAdapter(adaptadorRetos);
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