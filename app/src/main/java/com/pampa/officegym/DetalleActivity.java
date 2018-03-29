package com.pampa.officegym;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        TextView tituloEjer= (TextView) findViewById(R.id.tituloEjer);
        ImageView imagenEjer = (ImageView) findViewById(R.id.imagenEjer);
        TextView textoEjer= (TextView) findViewById(R.id.textoEjer);

        Bundle intentData = getIntent().getExtras();
        String tituloEjercio;
        String textoEjercicio;
        String urlImagen;

        if (intentData != null) {
            tituloEjercio = intentData.getString("titulo");
            tituloEjer.setText(tituloEjercio);

            textoEjercicio= intentData.getString("texto");
            textoEjer.setText(textoEjercicio);

            urlImagen = intentData.getString("url");
            Picasso.with(context)
                    .load(urlImagen)
                    .placeholder(R.drawable.sin_imagen)
                    .into(imagenEjer);


        }
    }
}
