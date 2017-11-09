package com.pampa.distribuidorachacabuco;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.pampa.distribuidorachacabuco.model.ConfiguracionModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ConfiguracionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOGTAG = "LogsAndroid";
    ConfiguracionModel configuracionModel = new ConfiguracionModel(this);
    Configuracion configuracion;
    Spinner spinner;
    Button btnexport;
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
        db.close();
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

     public String createCSV(View view){

        File folder = new File(Environment.getExternalStorageDirectory() + "/Folder");
        if (!folder.exists())
            folder.mkdir();
        final String filename = folder.toString() + "/" + "facturas.csv";


        AdminSQLiteOpenHelper dbHelper1 = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db1 = dbHelper1.getReadableDatabase();
        Cursor cursor2 = db1.rawQuery("SELECT * FROM facturas where uploaded = 0", null);

        try {
            FileWriter fw = new FileWriter(filename);

            if (cursor2!= null) {
                cursor2.moveToFirst();
                for (int i = 0; i < cursor2.getCount(); i++) {
                    for (int j = 0; j < cursor2.getColumnNames().length; j++) {
                        fw.append(cursor2.getString(j) + ",");
                    }
                    fw.append("\n");

                    cursor2.moveToNext();
                }
                cursor2.close();
            }
            fw.close();
        } catch(Exception e){
            e.getMessage();
        }
        return filename;
    }



}
