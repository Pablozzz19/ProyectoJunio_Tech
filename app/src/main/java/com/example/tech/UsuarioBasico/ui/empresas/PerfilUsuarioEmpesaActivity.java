package com.example.tech.UsuarioBasico.ui.empresas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tech.R;
import com.squareup.picasso.Picasso;

public class PerfilUsuarioEmpesaActivity extends AppCompatActivity {

    private ImageView ivFotoUserEmpresa;
    private TextView tvUserEmpresaNombre, tvUserEmpresaDescripcion, tvUserEmpresaEmail, tvUserEmpresaTelefono, tvUserEmpresaEmpleados, tvUserEmpresaSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_empesa);

        Bundle bundle = getIntent().getExtras();

        ivFotoUserEmpresa = (ImageView) findViewById(R.id.ivFotoUserEmpresa);
        tvUserEmpresaNombre = (TextView) findViewById(R.id.tvUserEmpresaNombre);
        tvUserEmpresaDescripcion = (TextView) findViewById(R.id.tvUserEmpresaDescripcion);
        tvUserEmpresaEmail = (TextView) findViewById(R.id.tvUserEmpresaEmail);
        tvUserEmpresaTelefono = (TextView) findViewById(R.id.tvUserEmpresaTelefono);
        tvUserEmpresaEmpleados = (TextView) findViewById(R.id.tvUserEmpresaEmpleados);
        tvUserEmpresaSede = (TextView) findViewById(R.id.tvUserEmpresaSede);

        tvUserEmpresaNombre.setText(bundle.getString("NombreEmpresa"));
        tvUserEmpresaDescripcion.setText(bundle.getString("DescripcionEmpresa"));
        tvUserEmpresaEmail.setText(bundle.getString("EmailEmpresa"));
        tvUserEmpresaTelefono.setText(bundle.getString("TelefonoEmpresa"));
        tvUserEmpresaEmpleados.setText(bundle.getString("EmpleadosEmpresa"));
        tvUserEmpresaSede.setText(bundle.getString("SedeEmpresa"));

        Picasso.with(this).load(bundle.getString("ImagenEmpresa")).into(ivFotoUserEmpresa);
    }
}