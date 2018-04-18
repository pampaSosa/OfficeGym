package com.pampa.officegym.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pampa.officegym.Model.Ejercicio;
import com.pampa.officegym.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adrian on 14/01/2018.
 */


public class CustomAdapterFoto extends BaseAdapter {
    Activity activity;
    Context context;
    ArrayList<Ejercicio> ejercicios;
    LayoutInflater inflater;

    public CustomAdapterFoto(Context context, ArrayList<Ejercicio> ejercicios){
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @Override
    public int getCount() {
        return ejercicios.size();
    }

    @Override
    public Object getItem(int position) {
        return ejercicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.item_row,parent,false);
        }

        Ejercicio ejercicio = ejercicios.get(position);


        TextView titulo = (TextView) convertView.findViewById(R.id.nombreEjer);
        titulo.setText(ejercicio.getTitulo());

        TextView descripcion = (TextView) convertView.findViewById(R.id.descripcion);
        descripcion.setText(ejercicio.getTexto().substring(0,200)+"....");

       /* ImageView imagen = convertView.findViewById(R.id.ImageEjercicio);
        String url = ejercicio.getLink();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.sin_imagen)
                .into(imagen);

        Button botonRealizado = convertView.findViewById(R.id.buttonRealizado);
        botonRealizado.setVisibility(View.GONE);*/
        return convertView;
    }


}
