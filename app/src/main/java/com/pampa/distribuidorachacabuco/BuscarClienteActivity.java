package com.pampa.distribuidorachacabuco;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.pampa.distribuidorachacabuco.model.ClienteModel;

public class BuscarClienteActivity extends AppCompatActivity {

    ListView listView;
    ClienteModel clienteModel ;
    SimpleCursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);
        ListView list = (ListView) findViewById(R.id.list_buscar_clientes);
        EditText search = (EditText)  findViewById(R.id.input_buscar_clientes);

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        String[] cols = new String[]{"nombre","direccion"};
        int[] adapterRows = new int[]{ android.R.id.text1, android.R.id.text2 };
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT id AS _id,nombre,direccion FROM clientes ORDER BY nombre ASC",null);
        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, cursor, cols, adapterRows,1);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                Log.i("ANDROID",constraint.toString());
                Cursor c = db.rawQuery("SELECT id AS _id,nombre,direccion FROM clientes WHERE nombre LIKE '%"+constraint.toString()+"%' ORDER BY nombre ASC",null);
                return c;
            }
        });
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuscarClienteActivity.this, CargarPedidoActivity.class);
                intent.putExtra("id_cliente",(int)id);
                intent.putExtra("id_factura",0);
                intent.putExtra("id_recorrido",0);
                startActivity(intent);
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BuscarClienteActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void nuevoCliente(View view) {
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}