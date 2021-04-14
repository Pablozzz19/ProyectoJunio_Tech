package com.example.tech.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tech.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvRegistrarse = (TextView)findViewById(R.id.tvRegistrarse);

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  CrearCuentaActivity.class);
                startActivity(intent);
            }
        });*/

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  CrearCuentaActivity.class);
                startActivity(intent);
            }
        });
    }
}
