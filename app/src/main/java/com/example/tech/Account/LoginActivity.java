package com.example.tech.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btnLogin;
    EditText etLoginEmail, etLoginLock;
    TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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

    // Método para login de un Usuario o una Empresa.
    private void loginUsuario() {
        String email = etLoginEmail.getText().toString().trim();
        String password = etLoginLock.getText().toString().trim();

        // Si no ha puesto bien o está vacío el email, lo notificamos.
        if (email.isEmpty()) {
            etLoginEmail.setError("Introduce un Email.");
            etLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.setError("Introduce un Email válido.");
            etLoginEmail.requestFocus();
            return;
        }

        // Si la contraseña está vacía o tiene menos de 6 carácteres, lo notificamos.
        if (password.isEmpty()) {
            etLoginLock.setError("Introduce una Contraseña.");
            etLoginLock.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etLoginLock.setError("Introduce una Contraseña con 6 o más carácteres.");
            etLoginLock.requestFocus();
            return;
        }

        // Login del Usuario o la Empresa.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this,  PrimerLoginUsuarioActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
