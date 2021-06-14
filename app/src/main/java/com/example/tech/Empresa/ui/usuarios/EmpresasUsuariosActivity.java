package com.example.tech.Empresa.ui.usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tech.R;
import com.squareup.picasso.Picasso;

public class EmpresasUsuariosActivity extends AppCompatActivity {

    private ImageView ivFotoUserEmp;
    private TextView tvUserNombreEmp, tvUserApellidoEmp, tvUserDescripcionEmp, tvUserEmailEmp, tvUserTelefonoEmp, tvUserFechaEmp, tvLenguajeEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas_usuarios);

        Bundle bundle = getIntent().getExtras();

        ivFotoUserEmp = (ImageView)findViewById(R.id.ivFotoUserEmp);
        tvUserNombreEmp = (TextView)findViewById(R.id.tvUserNombreEmp);
        tvUserApellidoEmp = (TextView)findViewById(R.id.tvUserApellidoEmp);
        tvUserDescripcionEmp = (TextView)findViewById(R.id.tvUserDescripcionEmp);
        tvUserEmailEmp = (TextView)findViewById(R.id.tvUserEmailEmp);
        tvUserTelefonoEmp = (TextView)findViewById(R.id.tvUserTelefonoEmp);
        tvUserFechaEmp = (TextView)findViewById(R.id.tvUserFechaEmp);
        tvLenguajeEmp = (TextView)findViewById(R.id.tvLenguajeEmp);

        tvUserNombreEmp.setText(bundle.getString("NombreUser"));
        tvUserApellidoEmp.setText(bundle.getString("ApellidosUser"));
        tvUserDescripcionEmp.setText(bundle.getString("DescripcionUser"));
        tvUserEmailEmp.setText(bundle.getString("EmailUser"));
        tvUserTelefonoEmp.setText(bundle.getString("TelefonoUser"));
        tvUserFechaEmp.setText(bundle.getString("FechaUser"));
        tvLenguajeEmp.setText(bundle.getString("LenguajeUser"));

        Picasso.with(this).load(bundle.getString("ImagenUser")).into(ivFotoUserEmp);
    }
}