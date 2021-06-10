package com.example.tech.UsuarioBasico;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tech.Clases.Reto;
import com.example.tech.Clases.Usuario;
import com.example.tech.R;

import java.util.ArrayList;

public class AdaptadorRetos extends ArrayAdapter<Reto> {

    public AdaptadorRetos(@NonNull Context context, ArrayList<Reto> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_reto, parent, false);
        }

        Reto reto = (Reto) getItem(position);

        TextView tvLvNombreReto = listitemView.findViewById(R.id.tvLvNombreReto);
        TextView tvLvDescripcionReto = listitemView.findViewById(R.id.tvLvDescripcionReto);
        TextView tvLvLenguajeReto = listitemView.findViewById(R.id.tvLvLenguajeReto);
        ImageView ivLvFotoRetoEmpresa = listitemView.findViewById(R.id.ivLvFotoRetoEmpresa);

        tvLvNombreReto.setText(reto.getNombre());
        tvLvDescripcionReto.setText(reto.getDescripcion());
        tvLvLenguajeReto.setText(reto.getLenguaje());

        return listitemView;
    }
}
