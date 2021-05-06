package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.TimeZone;

public class PrimerLoginUsuarioActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private String UsuarioId;

    private Button btnComenzarUsuario;
    private EditText etCrearNombre, etCrearApellidos, etCrearFecha, etCrearTelefono, etCrearDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_login);

        firebaseFirestore = FirebaseFirestore.getInstance();

        btnComenzarUsuario = (Button) findViewById(R.id.btnComenzarUsuario);
        etCrearNombre = (EditText) findViewById(R.id.etCrearNombre);
        etCrearApellidos = (EditText) findViewById(R.id.etCrearApellidos);
        etCrearFecha = (EditText) findViewById(R.id.etCrearFecha);
        etCrearTelefono = (EditText) findViewById(R.id.etCrearTelefono);
        etCrearDescripcion = (EditText) findViewById(R.id.etCrearDescripcion);

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
                        Toast.makeText(PrimerLoginUsuarioActivity.this, "Bien", Toast.LENGTH_LONG).show();
                    }
                });
    }
}