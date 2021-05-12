package com.example.tech.UsuarioBasico.ui.perfil;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tech.Account.CrearCuentaActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GalleryFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;

    TextView tvUserNombre, tvUserApellido, tvUserDescripcion, tvUserEmail, tvUserTelefono, tvUserFecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        tvUserNombre = (TextView) root.findViewById(R.id.tvUserNombre);
        tvUserApellido = (TextView) root.findViewById(R.id.tvUserApellido);
        tvUserDescripcion = (TextView) root.findViewById(R.id.tvUserDescripcion);
        tvUserEmail = (TextView) root.findViewById(R.id.tvUserEmail);
        tvUserTelefono = (TextView) root.findViewById(R.id.tvUserTelefono);
        tvUserFecha = (TextView) root.findViewById(R.id.tvUserFecha);

        UsuarioMainActivity usuarioMainActivity = (UsuarioMainActivity) getActivity();
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
            }
        });
        //Toast.makeText(getContext(), usuarioMainActivity.getUsuarioId(), Toast.LENGTH_LONG).show();
        return root;
    }
}