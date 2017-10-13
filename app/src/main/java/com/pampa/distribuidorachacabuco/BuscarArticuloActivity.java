package com.pampa.distribuidorachacabuco;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.support.v7.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.pampa.distribuidorachacabuco.model.ArticuloModel;

public class BuscarArticuloActivity extends AppCompatActivity {

    ListView listView;
    ArticuloModel articuloModel ;
    SimpleCursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_articulo);
        ListView list = (ListView) findViewById(R.id.list_buscar_articulos);
        EditText search = (EditText)  findViewById(R.id.input_buscar_articulos);

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        String[] cols = new String[]{"codigo","descripcion"};
        int[] adapterRows = new int[]{ android.R.id.text1, android.R.id.text2 };
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT _id,codigo,descripcion FROM articulos ORDER BY codigo ASC",null);
        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, cursor, cols, adapterRows,1);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                Log.i("ANDROID",constraint.toString());
                Cursor c = db.rawQuery("SELECT _id,codigo,descripcion FROM articulos WHERE descripcion LIKE '%"+constraint.toString()+"%' ORDER BY codigo ASC",null);
                return c;
            }
        });
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", String.valueOf(id));
                setResult(1, resultIntent);
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BuscarArticuloActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}