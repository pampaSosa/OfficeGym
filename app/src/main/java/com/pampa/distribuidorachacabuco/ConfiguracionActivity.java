package com.pampa.distribuidorachacabuco;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.pampa.distribuidorachacabuco.model.ConfiguracionModel;
import com.pampa.distribuidorachacabuco.model.FacturaModel;

import java.util.ArrayList;

public class ConfiguracionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOGTAG = "LogsAndroid";
    ConfiguracionModel configuracionModel = new ConfiguracionModel(this);
    Configuracion configuracion;
    Spinner spinner;
    private int id_recorrido = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        configuracion = configuracionModel.get();
        EditText urlText = (EditText)findViewById(R.id.urlText);
        EditText dispositivoText = (EditText)findViewById(R.id.dispositivoText);
        urlText.setText(configuracion.getUrl());
        dispositivoText.setText(configuracion.getDispositivo());

        spinner = (Spinner) findViewById(R.id.recorridoSpinner);
        String[] cols = new String[]{"nombre"};
        int[] adapterRows = new int[]{ android.R.id.text1 };

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id,nombre FROM recorridos",null);
        SimpleCursorAdapter adap = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item, cursor, cols, adapterRows,1);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adap);
        spinner.setOnItemSelectedListener(this);
    }

    public void limpiar(View view) {
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE facturas SET uploaded = 0");
        db.execSQL("UPDATE facturaItem SET uploaded = 0");
    }

    public void guardar(View view){
        Configuracion c = new Configuracion();
        EditText urlText = (EditText)findViewById(R.id.urlText);
        EditText dispositivoText = (EditText)findViewById(R.id.dispositivoText);
        String dispositivo = dispositivoText.getText().toString();
        String url = urlText.getText().toString();
        c.setDispositivo(dispositivo);
        c.setUrl(url);
        c.setId_recorrido(this.id_recorrido);
        configuracionModel.set(c);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.id_recorrido = (int)id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
