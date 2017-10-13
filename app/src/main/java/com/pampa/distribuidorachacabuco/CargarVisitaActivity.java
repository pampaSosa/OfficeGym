package com.pampa.distribuidorachacabuco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class CargarVisitaActivity extends AppCompatActivity {
    TextView actualClientTextView;
    String clienteActual;
    String idClienteActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_visita);
        //Este valor viene dela actividad Main.
        String clienteActual = getIntent().getStringExtra("nombreClienteAct");
        idClienteActual = getIntent().getStringExtra("idClienteActual");

        //Se le asigna una variable textview para luego setearle el texto con el valor que vino de la acvity anterior.
        actualClientTextView = (TextView) findViewById(R.id.clientNameTextView);
        actualClientTextView.setText(clienteActual);
    }

    public void irCargarPedido(View view){
        Intent intent = new Intent(this, CargarPedidoActivity.class);
        actualClientTextView = (TextView) findViewById(R.id.clientNameTextView);
        clienteActual = actualClientTextView.getText().toString();

        intent.putExtra("nombreClienteAct",clienteActual);
        intent.putExtra("idClienteActual",idClienteActual);
        intent.putExtra("id_factura","0");
        startActivity(intent);
    }



}
