package com.example.tech.Empresa.ui.perfil;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PerfilEmpresaFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private ImageView ivFotoEmpresa;
    private TextView tvEmpresaNombre, tvEmpresaDescripcion, tvEmpresaEmail, tvEmpresaTelefono, tvEmpresaEmpleados, tvEmpresaSede;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil_empresa, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        ivFotoEmpresa = (ImageView) root.findViewById(R.id.ivFotoEmpresa);
        tvEmpresaNombre = (TextView) root.findViewById(R.id.tvEmpresaNombre);
        tvEmpresaDescripcion = (TextView) root.findViewById(R.id.tvEmpresaDescripcion);
        tvEmpresaEmail = (TextView) root.findViewById(R.id.tvEmpresaEmail);
        tvEmpresaTelefono = (TextView) root.findViewById(R.id.tvEmpresaTelefono);
        tvEmpresaEmpleados = (TextView) root.findViewById(R.id.tvEmpresaEmpleados);
        tvEmpresaSede = (TextView) root.findViewById(R.id.tvEmpresaSede);

        EmpresaMainActivity empresaMainActivity = (EmpresaMainActivity) getActivity();

        StorageReference profileRef = storageReference.child("empresas/" + empresaMainActivity.getEmpresaId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).into(ivFotoEmpresa);
            }
        });

        final DocumentReference documentReference = firebaseFirestore.collection("Empresas").document(empresaMainActivity.getEmpresaId());
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvEmpresaNombre.setText(value.getString("nombre"));
                tvEmpresaDescripcion.setText(value.getString("descripcion"));
                tvEmpresaEmail.setText(value.getString("email"));
                tvEmpresaTelefono.setText(value.getString("telefono"));
                tvEmpresaEmpleados.setText(value.getString("nroEmpleados"));
                tvEmpresaSede.setText(value.getString("sede"));
            }
        });

        return root;
    }
}