package com.pampa.distribuidorachacabuco;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.pampa.distribuidorachacabuco.model.ClienteModel;
import com.pampa.distribuidorachacabuco.model.ConfiguracionModel;

public class ClienteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOGTAG = "LogsAndroid";
    private ClienteModel clienteModel = new ClienteModel(this);
    private boolean guardando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
    }

    public void guardar(View view){
        if (this.guardando) return;
        this.guardando = true;
        EditText nombreText = (EditText)findViewById(R.id.nombreText);
        EditText direccionText = (EditText)findViewById(R.id.direccionText);
        EditText cuitText = (EditText)findViewById(R.id.cuitText);
        EditText telefonoText = (EditText)findViewById(R.id.telefonoText);

        AlertDialog alertDialog = new AlertDialog.Builder(ClienteActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        if ("".equals(nombreText.getText().toString().trim())) {
            alertDialog.setMessage("Por favor ingrese un nombre.");
            alertDialog.show();
            return;
        }
        if ("".equals(direccionText.getText().toString().trim())) {
            alertDialog.setMessage("Por favor ingrese una direccion.");
            alertDialog.show();
            return;
        }
        if ("".equals(direccionText.getText().toString().trim())) {
            alertDialog.setMessage("Por favor un numero de CUIT/DNI.");
            alertDialog.show();
            return;
        }
        if ("".equals(telefonoText.getText().toString().trim())) {
            alertDialog.setMessage("Por favor un telefono.");
            alertDialog.show();
            return;
        }

        Cliente c = new Cliente();
        c.setNombre(nombreText.getText().toString());
        c.setDireccion(direccionText.getText().toString());
        c.setCuit(cuitText.getText().toString());
        c.setTelefono(telefonoText.getText().toString());
        int id = (int) clienteModel.insert(c);

        Intent intent = new Intent(ClienteActivity.this, CargarPedidoActivity.class);
        intent.putExtra("id_cliente",id);
        intent.putExtra("id_factura",0);
        intent.putExtra("id_recorrido",0);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
