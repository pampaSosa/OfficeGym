package com.pampa.officegym.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pampa.officegym.Adapter.CustomAdapterFoto;
import com.pampa.officegym.DetalleActivity;
import com.pampa.officegym.Model.Ejercicio;
import com.pampa.officegym.Model.EjercicioModel;
import com.pampa.officegym.R;

import java.util.ArrayList;


public class PhotoFragment extends Fragment {
    EjercicioModel ejercicioModel;
    ArrayList<Ejercicio> arrayListEjericio;
    ListView lv;
    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        lv= (ListView) view.findViewById(R.id.listViewPhotoFragment);

        CustomAdapterFoto adapter=new CustomAdapterFoto(this.getActivity(),getPhotos());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayListEjericio = ejercicioModel.getEjerciciosFotos();
                Ejercicio e = arrayListEjericio.get(i);

                /*Intent intent = new Intent(getActivity(), DetalleActivity.class);
                intent.putExtra("titulo", e.getNombre());
                intent.putExtra("texto",e.getTexto());
                intent.putExtra("url", e.getImgUrl());
                startActivity(intent);*/
            }

        });

        return view;

    }






    private ArrayList<Ejercicio> getPhotos(){
        ejercicioModel = new EjercicioModel();
        return ejercicioModel.getEjerciciosFotos();

    }


}
