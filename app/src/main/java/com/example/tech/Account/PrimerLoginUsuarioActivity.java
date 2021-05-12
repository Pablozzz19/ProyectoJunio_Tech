package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.TimeZone;

public class PrimerLoginUsuarioActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String UsuarioId;

    private Button btnComenzarUsuario;
    private EditText etCrearNombre, etCrearApellidos, etCrearFecha, etCrearTelefono, etCrearDescripcion;
    private ImageView ivCrearFotoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_login);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        btnComenzarUsuario = (Button) findViewById(R.id.btnComenzarUsuario);
        etCrearNombre = (EditText) findViewById(R.id.etCrearNombre);
        etCrearApellidos = (EditText) findViewById(R.id.etCrearApellidos);
        etCrearFecha = (EditText) findViewById(R.id.etCrearFecha);
        etCrearTelefono = (EditText) findViewById(R.id.etCrearTelefono);
        etCrearDescripcion = (EditText) findViewById(R.id.etCrearDescripcion);
        ivCrearFotoUser = (ImageView) findViewById(R.id.ivCrearFotoUser);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CEST"));
        calendar.clear();

        final long today = MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Fecha Nacimiento");
        builder.setSelection(today);
        final MaterialDatePicker materialDatePicker = builder.build();

        etCrearFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etCrearFecha.setText(materialDatePicker.getHeaderText());
            }
        });

        btnComenzarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUsuario();
            }
        });

        ivCrearFotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir Galería.
                Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleria, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                ivCrearFotoUser.setImageURI(uri);
                
                uploadImageToFirebase(uri);
            }
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("profile.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PrimerLoginUsuarioActivity.this, "Imagen Actualizada", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrimerLoginUsuarioActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para actualizar los campos pedidos, ya que por defecto está a null.
    private void updateUsuario() {
        // Comprobar que los campos no están vacíos.
        String nombre = etCrearNombre.getText().toString().trim();
        String apellidos = etCrearApellidos.getText().toString().trim();
        String fechaNacimiento = etCrearFecha.getText().toString().trim();
        String telefono = etCrearTelefono.getText().toString().trim();
        String descripcion = etCrearDescripcion.getText().toString().trim();
        if (nombre.isEmpty()) {
            etCrearNombre.setError("Introduce un Nombre.");
            etCrearNombre.requestFocus();
            return;
        }
        if (apellidos.isEmpty()) {
            etCrearApellidos.setError("Introduce un Apellido.");
            etCrearApellidos.requestFocus();
            return;
        }
        if (fechaNacimiento.isEmpty()) {
            etCrearFecha.setError("Introduce una Fecha Nacimiento.");
            etCrearFecha.requestFocus();
            return;
        }
        if (telefono.isEmpty()) {
            etCrearTelefono.setError("Introduce un Teléfono.");
            etCrearTelefono.requestFocus();
            return;
        }
        if (descripcion.isEmpty()) {
            etCrearDescripcion.setError("Introduce una Descripción.");
            etCrearDescripcion.requestFocus();
            return;
        }

        UsuarioId = getIntent().getExtras().getString("UsuarioId");
        firebaseFirestore.collection("Usuarios").document(UsuarioId)
                .update("apellidos", apellidos,
                        "bAux", true,
                        "descripcion", descripcion,
                        "fechaNacimiento", fechaNacimiento,
                        "nombre", nombre,
                        "telefono", telefono)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(PrimerLoginUsuarioActivity.this, UsuarioMainActivity.class);
                        intent.putExtra("UsuarioId", UsuarioId);
                        startActivity(intent);
                        //Toast.makeText(PrimerLoginUsuarioActivity.this, "Bien", Toast.LENGTH_LONG).show();
                    }
                });
    }
}