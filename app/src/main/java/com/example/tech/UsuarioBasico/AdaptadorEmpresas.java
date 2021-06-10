package com.example.tech.UsuarioBasico;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tech.Clases.Empresa;
import com.example.tech.R;
import com.example.tech.UsuarioBasico.ui.empresas.PerfilUsuarioEmpesaActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorEmpresas extends ArrayAdapter<Empresa> {

    public AdaptadorEmpresas(@NonNull Context context, ArrayList<Empresa> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        final Empresa empresa = getItem(position);

        TextView tvLvNombreEmpresa = listitemView.findViewById(R.id.tvLvNombreEmpresa);
        TextView tvLvDescripcion = listitemView.findViewById(R.id.tvLvDescripcion);
        ImageView ivLvFotoEmpresa = listitemView.findViewById(R.id.ivLvFotoEmpresa);

        tvLvNombreEmpresa.setText(empresa.getNombre());
        tvLvDescripcion.setText(empresa.getDescripcion());
        Picasso.with(getContext()).load(empresa.getUrlImage()).into(ivLvFotoEmpresa);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PerfilUsuarioEmpesaActivity.class);
                intent.putExtra("NombreEmpresa", empresa.getNombre());
                intent.putExtra("DescripcionEmpresa", empresa.getDescripcion());
                intent.putExtra("EmailEmpresa", empresa.getEmail());
                intent.putExtra("TelefonoEmpresa", empresa.getTelefono());
                intent.putExtra("EmpleadosEmpresa", empresa.getNroEmpleados());
                intent.putExtra("SedeEmpresa", empresa.getSede());
                intent.putExtra("ImagenEmpresa", empresa.getUrlImage());
                getContext().startActivity(intent);
            }
        });

        return listitemView;
    }
}
