package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PrimerLoginUsuarioActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private List<CheckBox> items;
    private String UsuarioId, urlImage;
    private Uri imageUri;

    private Button btnComenzarUsuario;
    private EditText etCrearNombre, etCrearApellidos, etCrearFecha, etCrearTelefono, etCrearDescripcion;
    private ImageView ivCrearFotoUser;
    private LinearLayout llAux, llAux2;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_login);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        items = new ArrayList<>();

        btnComenzarUsuario = (Button) findViewById(R.id.btnComenzarUsuario);
        etCrearNombre = (EditText) findViewById(R.id.etCrearNombre);
        etCrearApellidos = (EditText) findViewById(R.id.etCrearApellidos);
        etCrearFecha = (EditText) findViewById(R.id.etCrearFecha);
        etCrearTelefono = (EditText) findViewById(R.id.etCrearTelefono);
        etCrearDescripcion = (EditText) findViewById(R.id.etCrearDescripcion);
        ivCrearFotoUser = (ImageView) findViewById(R.id.ivCrearFotoUser);
        llAux = (LinearLayout) findViewById(R.id.llAux);
        llAux2 = (LinearLayout) findViewById(R.id.llAux2);

        UsuarioId = getIntent().getExtras().getString("UsuarioId");

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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#569DC7")));
                            }
                            if (contador < 3) {
                                llAux.addView(cb);
                                contador++;
                            } else {
                                llAux2.addView(cb);
                                contador++;
                            }
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                //Uri uri = data.getData();
                //ivCrearFotoUser.setImageURI(uri);
                
                //uploadImageToFirebase(uri);
                imageUri = data.getData();
                uploadImage();
            }
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        final StorageReference fileRef = storageReference.child("usuarios/" + UsuarioId + "/profile.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(PrimerLoginUsuarioActivity.this).load(uri).into(ivCrearFotoUser);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrimerLoginUsuarioActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imageUri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("usuarios/" + UsuarioId + "/profile.jpg")/*.child(System.currentTimeMillis() + "." + getFileExtension(imageUri))*/;
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlImage = uri.toString();
                            Log.i("DownloadUrl", urlImage);
                            Picasso.with(PrimerLoginUsuarioActivity.this).load(uri).into(ivCrearFotoUser);
                        }
                    });
                }
            });
        }
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

        // Obtener el texto de los CheckBox checkeados.
        for (CheckBox item : items) {
            if (item.isChecked()) {
                Log.i("Check", item.getText().toString());
                firebaseFirestore.collection("Usuarios").document(UsuarioId)
                        .update("lenguaje", FieldValue.arrayUnion(item.getText().toString()));
            }
        }

        firebaseFirestore.collection("Usuarios").document(UsuarioId)
                .update("apellidos", apellidos,
                        "bAux", true,
                        "descripcion", descripcion,
                        "fechaNacimiento", fechaNacimiento,
                        "nombre", nombre,
                        "telefono", telefono,
                        "urlImage", urlImage)
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