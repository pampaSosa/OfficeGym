package com.pampa.distribuidorachacabuco;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pampa.distribuidorachacabuco.model.ArticuloModel;
import com.pampa.distribuidorachacabuco.model.ClienteModel;
import com.pampa.distribuidorachacabuco.model.ConfiguracionModel;
import com.pampa.distribuidorachacabuco.model.FacturaModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = "LogsAndroid";
    TextView TextViewActualClientNombre;
    TextView TextViewActualClientDire;
    TextView TextViewActualClientId;
    TextView TextZona;

    TextView textView;

    // Variables de configuracion
    String server_url = "";
    String server_url_facturas = "";
    String dispositivo = "";
    int id_recorrido = 0;
    String recorrido = "";
    ConfiguracionModel configuracionModel = new ConfiguracionModel(this);
    private boolean enviando = false;

    private Cliente clienteActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        TextViewActualClientNombre = (TextView) findViewById(R.id.actualClientValue);
        TextViewActualClientDire = (TextView) findViewById(R.id.actualDirClientValue);
        TextViewActualClientId=(TextView) findViewById(R.id.actualClientId);
        TextZona = (TextView) findViewById(R.id.textZona);
    }

    @Override
    protected void onStart() {
        super.onStart();
        get_configuracion();
        get_cliente();
    }

    private void get_cliente() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        try {
            clienteActual = admin.getClienteActual();
            TextViewActualClientId.setText(String.valueOf(clienteActual.getId()));
            TextViewActualClientNombre.setText(clienteActual.getNombre());
            TextViewActualClientDire.setText(clienteActual.getDireccion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_configuracion() {
        try {
            Configuracion c = configuracionModel.get();
            this.server_url = "https://"+c.getUrl()+"/sistema/app/sincronizar/";
            this.server_url_facturas = "https://"+c.getUrl()+"/sistema/facturas/function/sincronizar/";
            this.dispositivo = c.getDispositivo();
            this.id_recorrido = c.getId_recorrido();
            TextZona.setText(c.getRecorrido());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarBD(String response) {

        if (this.enviando) return;
        this.enviando = true;

        // TODO: Antes de cargar del servidor, tenemos que controlar si no existen
        // pedidos para enviar, porque sino se reemplazaria el recorrido de nuevo
        final String res = response;
        final ArticuloModel articuloModel = new ArticuloModel(this);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                get_cliente();
                enviando = false;
            }
        };

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Por favor espere...");
        dialog.show();
        Thread t = new Thread() {
            @Override
            public void run() {
                articuloModel.borrarTabla();
                articuloModel.insertarDesdeServer(res);
                dialog.dismiss();
                Message msj = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("status","OK");
                msj.obj = bundle;
                handler.sendMessage(msj);
            }
        };
        t.start();
    }

    public void irCargarVisita(View view){
        // Controlamos si hay un proximo cliente
        if (this.clienteActual.getId() != 0) {
            Intent intent = new Intent(this, CargarPedidoActivity.class);
            intent.putExtra("id_cliente",this.clienteActual.getId());
            intent.putExtra("id_factura",0);
            intent.putExtra("id_recorrido",this.id_recorrido);
            startActivity(intent);
        }
    }

    public void irPedidoFueraDeRuta(View view) {
        Intent intent = new Intent(this, BuscarClienteActivity.class);
        startActivity(intent);
    }

    public void irListadoPedidos(View view){
        Intent intent = new Intent(this, ListarPedidosActivity.class);
        startActivity(intent);
    }

    public void irConfiguracion(View view) {
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        startActivity(intent);
    }

    public void enviarServidor2(View view){

        if (this.enviando) return;
        this.enviando = true;
        final FacturaModel modelo = new FacturaModel(this);
        final ProgressDialog dialog = new ProgressDialog(this);
        final ClienteModel clienteModel = new ClienteModel(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Por favor espere...");
        dialog.show();

        ArrayList<Factura> facturas = modelo.getNuevas();
        for (int i = 0; i < facturas.size(); i++) {
            Factura f = facturas.get(i);
            Log.i(LOGTAG, f.toString());
        }
        Gson gson = new GsonBuilder().create();
        final String disp = this.dispositivo;
        final String res = gson.toJson(facturas);

        Gson gson2 = new GsonBuilder().create();
        ArrayList<Cliente> clientesNuevos = clienteModel.getNuevos();
        final String clientes = gson2.toJson(clientesNuevos);
        Log.i(LOGTAG, res);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_facturas,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelo.limpiar_facturas();
                dialog.dismiss();
                requestQueue.stop();
                enviando = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                requestQueue.stop();
                enviando = false;
            }
        }){@Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("dispositivo",disp);
            params.put("facturas",res);
            params.put("clientes",clientes);
            return params;
        }};
        Thread t = new Thread() {
            @Override
            public void run() {
                requestQueue.add(stringRequest);
            }
        };
        t.start();
    }

    public void inicioDb(View view){
        final String disp = this.dispositivo;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                actualizarBD(response);
                onRestart();
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                requestQueue.stop();
            }
        }){@Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("dispositivo",disp);
            return params;
        }};
            requestQueue.add(stringRequest);
        }

    }