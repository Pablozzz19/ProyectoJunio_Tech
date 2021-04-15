package com.example.tech.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tech.R;
import com.google.firebase.auth.FirebaseAuth;

public class CrearCuentaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnCrearCuenta;
    private EditText etCrearEmail, etCrearLock;
    private RadioButton rbCrearUsuario, rbCrearEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        mAuth = FirebaseAuth.getInstance();

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
        String email = etCrearEmail.getText().toString().trim();
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
    }
}
