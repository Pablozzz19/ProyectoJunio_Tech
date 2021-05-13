package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrimerLoginEmpresaActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private String EmpresaId;

    Button btnComenzarEmpresa;
    EditText etCrearNombreEmp, etCrearSedeEmp, etCrearTelefonoEmp, etCrearEmpleadosEmp, etCrearDescripcionEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_login_empresa);

        firebaseFirestore = FirebaseFirestore.getInstance();

        btnComenzarEmpresa = (Button) findViewById(R.id.btnComenzarEmpresa);
        etCrearNombreEmp = (EditText) findViewById(R.id.etCrearNombreEmp);
        etCrearSedeEmp = (EditText) findViewById(R.id.etCrearSedeEmp);
        etCrearTelefonoEmp = (EditText) findViewById(R.id.etCrearTelefonoEmp);
        etCrearEmpleadosEmp = (EditText) findViewById(R.id.etCrearEmpleadosEmp);
        etCrearDescripcionEmp = (EditText) findViewById(R.id.etCrearDescripcionEmp);

        EmpresaId = getIntent().getExtras().getString("EmpresaId");

        btnComenzarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmpresa();
            }
        });
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
                        "sede", sede)
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