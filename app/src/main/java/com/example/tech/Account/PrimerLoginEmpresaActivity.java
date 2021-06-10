package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PrimerLoginEmpresaActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String EmpresaId, urlImage;
    private Uri imageUri;

    private Button btnComenzarEmpresa;
    private EditText etCrearNombreEmp, etCrearSedeEmp, etCrearTelefonoEmp, etCrearEmpleadosEmp, etCrearDescripcionEmp;
    private ImageView ivCrearFotoEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_login_empresa);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        btnComenzarEmpresa = (Button) findViewById(R.id.btnComenzarEmpresa);
        etCrearNombreEmp = (EditText) findViewById(R.id.etCrearNombreEmp);
        etCrearSedeEmp = (EditText) findViewById(R.id.etCrearSedeEmp);
        etCrearTelefonoEmp = (EditText) findViewById(R.id.etCrearTelefonoEmp);
        etCrearEmpleadosEmp = (EditText) findViewById(R.id.etCrearEmpleadosEmp);
        etCrearDescripcionEmp = (EditText) findViewById(R.id.etCrearDescripcionEmp);
        ivCrearFotoEmpresa = (ImageView) findViewById(R.id.ivCrearFotoEmpresa);

        EmpresaId = getIntent().getExtras().getString("EmpresaId");

        btnComenzarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmpresa();
            }
        });

        ivCrearFotoEmpresa.setOnClickListener(new View.OnClickListener() {
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
                //Uri uri = data.getData();
                //ivCrearFotoUser.setImageURI(uri);

                //uploadImageToFirebase(uri);
                imageUri = data.getData();
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("empresas/" + EmpresaId + "/profile.jpg")/*.child(System.currentTimeMillis() + "." + getFileExtension(imageUri))*/;
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlImage = uri.toString();
                            Log.i("DownloadUrl", urlImage);
                            Picasso.with(PrimerLoginEmpresaActivity.this).load(uri).into(ivCrearFotoEmpresa);
                        }
                    });
                }
            });
        }
    }

    // Método para actualizar los campos pedidos, ya que por defecto está a null.
    private void updateEmpresa() {
        // Comprobar que los campos no están vacíos.
        String nombre = etCrearNombreEmp.getText().toString().trim();
        String sede = etCrearSedeEmp.getText().toString().trim();
        String telefono = etCrearTelefonoEmp.getText().toString().trim();
        String nroEmpleados = etCrearEmpleadosEmp.getText().toString().trim();
        String descripcion = etCrearDescripcionEmp.getText().toString().trim();
        if (nombre.isEmpty()) {
            etCrearNombreEmp.setError("Introduce un Nombre.");
            etCrearNombreEmp.requestFocus();
            return;
        }
        if (sede.isEmpty()) {
            etCrearSedeEmp.setError("Introduce una Sede.");
            etCrearSedeEmp.requestFocus();
            return;
        }
        if (telefono.isEmpty()) {
            etCrearTelefonoEmp.setError("Introduce un Teléfono.");
            etCrearTelefonoEmp.requestFocus();
            return;
        }
        if (nroEmpleados.isEmpty()) {
            etCrearEmpleadosEmp.setError("Introduce un Nombre.");
            etCrearEmpleadosEmp.requestFocus();
            return;
        }
        if (descripcion.isEmpty()) {
            etCrearDescripcionEmp.setError("Introduce un Nombre.");
            etCrearDescripcionEmp.requestFocus();
            return;
        }

        firebaseFirestore.collection("Empresas").document(EmpresaId)
                .update("bAux", true,
                        "descripcion", descripcion,
                        "nombre", nombre,
                        "nroEmpleados", nroEmpleados,
                        "telefono", telefono,
                        "sede", sede,
                        "urlImage", urlImage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(PrimerLoginEmpresaActivity.this, EmpresaMainActivity.class);
                intent.putExtra("EmpresaId", EmpresaId);
                startActivity(intent);
                //Toast.makeText(PrimerLoginEmpresaActivity.this, "Bien", Toast.LENGTH_LONG).show();
            }
        });
    }
}