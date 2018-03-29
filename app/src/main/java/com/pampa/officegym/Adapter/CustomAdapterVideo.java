package com.pampa.officegym.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pampa.officegym.Model.Ejercicio;
import com.pampa.officegym.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adrian on 14/01/2018.
 */


public class CustomAdapterVideo extends BaseAdapter {
    Activity activity;
    Context context;
    ArrayList<Ejercicio> ejercicios;
    LayoutInflater inflater;

    public CustomAdapterVideo(Context context, ArrayList<Ejercicio> ejercicios){
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

       /* ImageView imagen = convertView.findViewById(R.id.ImageEjercicio);
        String url = ejercicio.getLink();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.sin_imagen)
                .into(imagen);*/

        return convertView;
    }

}
