package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tech.Clases.Empresa;
import com.example.tech.Clases.Usuario;
import com.example.tech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
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

    private Button btnCrearCuenta, btnUsuario, btnEmpresa;
    private EditText etCrearEmail, etCrearLock;
    private MaterialButtonToggleGroup btgTipoUsuario;
    private ProgressBar progressAccount;
    //private RadioButton rbCrearUsuario, rbCrearEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnCrearCuenta = (Button)findViewById(R.id.btnCrearCuenta);
        etCrearEmail = (EditText)findViewById(R.id.etCrearEmail);
        etCrearLock = (EditText)findViewById(R.id.etCrearLock);
        progressAccount = (ProgressBar)findViewById(R.id.progressAccount);

        btgTipoUsuario = (MaterialButtonToggleGroup)findViewById(R.id.btgTipoUsuario);
        btnUsuario = (Button)findViewById(R.id.btnUsuario);
        btnEmpresa = (Button)findViewById(R.id.btnEmpresa);

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

        // Si no ha puesto bien o está vacío el email, lo notificamos.
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
        // Comprobar que hay un tipo de usuario seleccionado.
        final int selectedButtonId = btgTipoUsuario.getCheckedButtonId();

        if (selectedButtonId == btnUsuario.getId() || selectedButtonId == btnEmpresa.getId()) {
            progressAccount.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                if (selectedButtonId == btnUsuario.getId()) {

                                    DocumentReference documentReference = fStore.collection("Usuarios").document(mAuth.getCurrentUser().getUid());
                                    Map<String, Object> mUsuario = new HashMap<>();
                                    mUsuario.put("email", email);
                                    mUsuario.put("bAux", false);
                                    mUsuario.put("nombre", null);
                                    mUsuario.put("apellidos", null);
                                    mUsuario.put("fechaNacimiento", null);
                                    mUsuario.put("telefono", null);
                                    mUsuario.put("descripcion", null);
                                    documentReference.set(mUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CrearCuentaActivity.this, "Usuario registrado correctamenete!", Toast.LENGTH_LONG).show();
                                                progressAccount.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(CrearCuentaActivity.this, "Error al crear el usuario.", Toast.LENGTH_LONG).show();
                                                progressAccount.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                                } else if (selectedButtonId == btnEmpresa.getId()){

                                    DocumentReference documentReference = fStore.collection("Empresas").document(mAuth.getCurrentUser().getUid());
                                    Map<String, Object> mEmpresa = new HashMap<>();
                                    mEmpresa.put("email", email);
                                    mEmpresa.put("bAux", false);
                                    mEmpresa.put("nombre", null);
                                    mEmpresa.put("sede", null);
                                    mEmpresa.put("telefono", null);
                                    mEmpresa.put("nroEmpleados", null);
                                    mEmpresa.put("descripcion", null);
                                    documentReference.set(mEmpresa).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CrearCuentaActivity.this, "Empresa registrada correctamenete!", Toast.LENGTH_LONG).show();
                                                progressAccount.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(CrearCuentaActivity.this, "Error al crear la empresa.", Toast.LENGTH_LONG).show();
                                                progressAccount.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                                }

                            }
                        }
                    });
        } else {
            Toast.makeText(CrearCuentaActivity.this, "ERROR. Por favor elija un el tipo de cuenta.", Toast.LENGTH_LONG).show();
        }


    }
}
