package com.example.tech.UsuarioBasico.ui.perfil;


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

import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
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

public class PerfilFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private ImageView ivFotoUser;
    private TextView tvUserNombre, tvUserApellido, tvUserDescripcion, tvUserEmail, tvUserTelefono, tvUserFecha, tvLenguaje;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        ivFotoUser = (ImageView) root.findViewById(R.id.ivFotoUser);
        tvUserNombre = (TextView) root.findViewById(R.id.tvUserNombre);
        tvUserApellido = (TextView) root.findViewById(R.id.tvUserApellido);
        tvUserDescripcion = (TextView) root.findViewById(R.id.tvUserDescripcion);
        tvUserEmail = (TextView) root.findViewById(R.id.tvUserEmail);
        tvUserTelefono = (TextView) root.findViewById(R.id.tvUserTelefono);
        tvUserFecha = (TextView) root.findViewById(R.id.tvUserFecha);
        tvLenguaje = (TextView) root.findViewById(R.id.tvLenguaje);

        UsuarioMainActivity usuarioMainActivity = (UsuarioMainActivity) getActivity();

        StorageReference profileRef = storageReference.child("usuarios/" + usuarioMainActivity.getUsuarioId() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).into(ivFotoUser);
            }
        });

        final DocumentReference documentReference = firebaseFirestore.collection("Usuarios").document(usuarioMainActivity.getUsuarioId());
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvUserNombre.setText(value.getString("nombre"));
                tvUserApellido.setText(value.getString("apellidos"));
                tvUserDescripcion.setText(value.getString("descripcion"));
                tvUserEmail.setText(value.getString("email"));
                tvUserTelefono.setText(value.getString("telefono"));
                tvUserFecha.setText(value.getString("fechaNacimiento"));
                List<String> list = (List<String>) value.get("lenguaje");
                Log.i("lista", list.toString());
                String lenguaje = "";
                for (String s : list) {
                    lenguaje = lenguaje + " " + s;
                }
                tvLenguaje.setText(lenguaje.substring(1));
                //tvLenguaje.setText(value.getString("lenguaje"));
            }
        });
        //Toast.makeText(getContext(), usuarioMainActivity.getUsuarioId(), Toast.LENGTH_LONG).show();
        return root;
    }
}