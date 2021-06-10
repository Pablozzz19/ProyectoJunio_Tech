package com.example.tech.UsuarioBasico.ui.ajustes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tech.Account.PrimerLoginUsuarioActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditarCuentaUsuarioActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private List<CheckBox> items;
    private List<String> list;
    private String id;

    private Button btnEditarUsuario;
    private EditText etEditarNombre, etEditarApellidos, etEditarTelefono, etEditarDescripcion;
    private ImageView ivEditarFotoUser;
    private LinearLayout llEditarAux, llEditarAux2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta_usuario);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("UsuarioId");

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        items = new ArrayList<>();

        btnEditarUsuario = (Button) findViewById(R.id.btnEditarUsuario);
        etEditarNombre = (EditText) findViewById(R.id.etEditarNombre);
        etEditarApellidos = (EditText) findViewById(R.id.etEditarApellidos);
        etEditarTelefono = (EditText) findViewById(R.id.etEditarTelefono);
        etEditarDescripcion = (EditText) findViewById(R.id.etEditarDescripcion);
        ivEditarFotoUser = (ImageView) findViewById(R.id.ivEditarFotoUser);
        llEditarAux = (LinearLayout) findViewById(R.id.llEditarAux);
        llEditarAux2 = (LinearLayout) findViewById(R.id.llEditarAux2);

        StorageReference profileRef = storageReference.child("usuarios/" + id + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext()).load(uri).into(ivEditarFotoUser);
            }
        });

        final DocumentReference documentReference = firebaseFirestore.collection("Usuarios").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                etEditarNombre.setText(value.getString("nombre"));
                etEditarApellidos.setText(value.getString("apellidos"));
                etEditarTelefono.setText(value.getString("telefono"));
                etEditarDescripcion.setText(value.getString("descripcion"));
                list = (List<String>) value.get("lenguaje");
            }
        });

        // CheckBox dinámicos.
        firebaseFirestore.collection("LenguajeProgramacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int contador = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i("CheckBox", document.get("nombre").toString());
                            CheckBox cb = new CheckBox(getApplicationContext());
                            cb.setText(document.get("nombre").toString());
                            items.add(cb);
                            for (String s : list) {
                                if (s.equalsIgnoreCase(document.get("nombre").toString())) {
                                    cb.setChecked(true);
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#569DC7")));
                            }
                            if (contador < 3) {
                                llEditarAux.addView(cb);
                                contador++;
                            } else {
                                llEditarAux2.addView(cb);
                                contador++;
                            }
                        }
                    }
                });

        btnEditarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("EditarUsuarioId", id);
                updateUsuario();
            }
        });
    }

    // Método para actualizar los campos pedidos.
    private void updateUsuario() {
        // Comprobar que los campos no están vacíos.
        String nombre = etEditarNombre.getText().toString().trim();
        String apellidos = etEditarApellidos.getText().toString().trim();
        String telefono = etEditarTelefono.getText().toString().trim();
        String descripcion = etEditarDescripcion.getText().toString().trim();

        if (nombre.isEmpty()) {
            etEditarNombre.setError("Introduce un Nombre.");
            etEditarNombre.requestFocus();
            return;
        }
        if (apellidos.isEmpty()) {
            etEditarApellidos.setError("Introduce un Apellido.");
            etEditarApellidos.requestFocus();
            return;
        }
        if (telefono.isEmpty()) {
            etEditarTelefono.setError("Introduce un Teléfono.");
            etEditarTelefono.requestFocus();
            return;
        }
        if (descripcion.isEmpty()) {
            etEditarDescripcion.setError("Introduce una Descripción.");
            etEditarDescripcion.requestFocus();
            return;
        }

        // Obtener el texto de los CheckBox checkeados.
        for (CheckBox item : items) {
            if (item.isChecked()) {
                Log.i("Check", item.getText().toString());
                firebaseFirestore.collection("Usuarios").document(id)
                        .update("lenguaje", FieldValue.arrayUnion(item.getText().toString()));
            }
        }

        firebaseFirestore.collection("Usuarios").document(id)
                .update("apellidos", apellidos,
                        "descripcion", descripcion,
                        "nombre", nombre,
                        "telefono", telefono)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditarCuentaUsuarioActivity.this, "Usuario Actualizado", Toast.LENGTH_LONG).show();
                    }
                });
    }
}