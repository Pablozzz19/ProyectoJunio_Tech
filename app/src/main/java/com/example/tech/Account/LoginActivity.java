package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tech.Empresa.EmpresaMainActivity;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.UsuarioMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private Button btnLogin;
    private EditText etLoginEmail, etLoginLock;
    private TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnLogin = (Button)findViewById(R.id.btnLogin);
        etLoginEmail = (EditText)findViewById(R.id.etLoginEmail);
        etLoginLock = (EditText)findViewById(R.id.etLoginLock);
        tvRegistrarse = (TextView)findViewById(R.id.tvRegistrarse);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  CrearCuentaActivity.class);
                startActivity(intent);
            }
        });
    }

    // M??todo para login de un Usuario o una Empresa.
    private void loginUsuario() {
        final String email = etLoginEmail.getText().toString().trim();
        String password = etLoginLock.getText().toString().trim();

        // Si no ha puesto bien o est?? vac??o el email, lo notificamos.
        if (email.isEmpty()) {
            etLoginEmail.setError("Introduce un Email.");
            etLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.setError("Introduce un Email v??lido.");
            etLoginEmail.requestFocus();
            return;
        }

        // Si la contrase??a est?? vac??a o tiene menos de 6 car??cteres, lo notificamos.
        if (password.isEmpty()) {
            etLoginLock.setError("Introduce una Contrase??a.");
            etLoginLock.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etLoginLock.setError("Introduce una Contrase??a con 6 o m??s car??cteres.");
            etLoginLock.requestFocus();
            return;
        }

        // Login del Usuario o la Empresa.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    // Recorremos todos los Usuarios y/o Empresas, para comprobar si el Login es Usuario o Empresa.
                    // USUARIOS.
                    firebaseFirestore.collection("Usuarios")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.get("email").toString().equalsIgnoreCase(email)) {

                                                // Si es PRIMER LOGIN, lo mandamos a PrimerLoginUsuarioActivity.
                                                String UsId = document.getId();
                                                if ((Boolean) document.get("bAux") == false) {
                                                    // Paso el ID del Usuario.
                                                    Intent intent = new Intent(LoginActivity.this,  PrimerLoginUsuarioActivity.class);
                                                    intent.putExtra("UsuarioId", UsId);
                                                    startActivity(intent);
                                                    //startActivity(new Intent(LoginActivity.this,  PrimerLoginUsuarioActivity.class));
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, UsuarioMainActivity.class);
                                                    intent.putExtra("UsuarioId", UsId);
                                                    startActivity(intent);
                                                    Log.d("USUARIO", document.getId() + " => " + document.get("email"));
                                                }
                                            }
                                        }
                                    } else {
                                        Log.w("ERROR", "Error getting documents.", task.getException());
                                    }
                                }
                            });

                    // EMPRESAS.
                    firebaseFirestore.collection("Empresas")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.get("email").toString().equalsIgnoreCase(email)) {

                                                // Si es PRIMER LOGIN, lo mandamos a PrimerLoginEmpresaActivity.
                                                String EmpId = document.getId();
                                                if ((Boolean) document.get("bAux") == false) {
                                                    // Paso el ID de la Empresa.
                                                    Intent intent = new Intent(LoginActivity.this,  PrimerLoginEmpresaActivity.class);
                                                    intent.putExtra("EmpresaId", EmpId);
                                                    startActivity(intent);

                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, EmpresaMainActivity.class);
                                                    intent.putExtra("EmpresaId", EmpId);
                                                    startActivity(intent);
                                                    Log.d("EMPRESA", document.getId() + " => " + document.get("email"));
                                                }
                                            }
                                        }
                                    } else {
                                        Log.w("ERROR", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contrase??a incorrecta.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
