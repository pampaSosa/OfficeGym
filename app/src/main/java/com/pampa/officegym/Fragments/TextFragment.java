package com.pampa.officegym.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pampa.officegym.Adapter.CustomAdapterTexto;
import com.pampa.officegym.Model.Ejercicio;
import com.pampa.officegym.Model.EjercicioModel;
import com.pampa.officegym.R;

import java.util.ArrayList;


public class TextFragment extends Fragment {
    EjercicioModel ejercicioModel;
    private ListView mListView;

    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        ListView lv= (ListView) view.findViewById(R.id.listViewTextFragment);
        CustomAdapterTexto adapter=new CustomAdapterTexto(this.getActivity(),getTextos());
        lv.setAdapter(adapter);


        return view;


    }

    private ArrayList<Ejercicio> getTextos(){
        ejercicioModel = new EjercicioModel();
        return ejercicioModel.getEjerciciosVideos(getContext());
    }


}
