package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tech.Clases.Empresa;
import com.example.tech.Clases.Usuario;
import com.example.tech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearCuentaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private Button btnCrearCuenta;
    private EditText etCrearEmail, etCrearLock;
    private RadioButton rbCrearUsuario, rbCrearEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnCrearCuenta = (Button)findViewById(R.id.btnCrearCuenta);
        etCrearEmail = (EditText)findViewById(R.id.etCrearEmail);
        etCrearLock = (EditText)findViewById(R.id.etCrearLock);
        rbCrearUsuario = (RadioButton)findViewById(R.id.rbCrearUsuario);
        rbCrearEmpresa = (RadioButton)findViewById(R.id.rbCrearEmpresa);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    // Método para registrar un Usuario o una Empresa.
    private void registrarUsuario() {
        final String email = etCrearEmail.getText().toString().trim();
        String password = etCrearLock.getText().toString().trim();

        // Si no a puesto bien o está vacío el email, lo notificamos.
        if (email.isEmpty()) {
            etCrearEmail.setError("Introduce un Email.");
            etCrearEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etCrearEmail.setError("Introduce un Email válido.");
            etCrearEmail.requestFocus();
            return;
        }

        // Si la contraseña está vacía o tiene menos de 6 carácteres, lo notificamos.
        if (password.isEmpty()) {
            etCrearLock.setError("Introduce una Contraseña.");
            etCrearLock.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etCrearLock.setError("Introduce una Contraseña con 6 o más carácteres.");
            etCrearLock.requestFocus();
            return;
        }

        // Registro del Usuario o la Empresa.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            if (rbCrearUsuario.isChecked()) {

                                DocumentReference documentReference = fStore.collection("Usuarios").document(mAuth.getCurrentUser().getUid());
                                Map<String, Object> mUsuario = new HashMap<>();
                                mUsuario.put("email", email);
                                documentReference.set(mUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CrearCuentaActivity.this, "Usuario registrado correctamenete!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(CrearCuentaActivity.this, "Error al crear el usuario.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {

                                DocumentReference documentReference = fStore.collection("Empresas").document(mAuth.getCurrentUser().getUid());
                                Map<String, Object> mEmpresa = new HashMap<>();
                                mEmpresa.put("email", email);
                                documentReference.set(mEmpresa).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CrearCuentaActivity.this, "Empresa registrada correctamenete!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(CrearCuentaActivity.this, "Error al crear la empresa.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

    }
}
