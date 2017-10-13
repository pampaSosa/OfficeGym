package com.pampa.distribuidorachacabuco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pampa.distribuidorachacabuco.model.FacturaModel;

import java.util.ArrayList;
import java.util.List;

public class ListarPedidosActivity extends AppCompatActivity {

    ListarPedidosAdapter listarPedidosAdapter;
    ArrayList<Factura> listaItemsPedidos;
    FacturaModel facturaModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);

        facturaModel = new FacturaModel(this);
        listaItemsPedidos = facturaModel.get_list();

        final ListView listView = (ListView) findViewById(R.id.listViewPedidosTomados);
        listarPedidosAdapter  = new ListarPedidosAdapter(this,this.listaItemsPedidos);
        listView.setAdapter(listarPedidosAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ITEM",String.valueOf(id));
            }
        });

    }







}
