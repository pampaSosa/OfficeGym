package com.pampa.distribuidorachacabuco;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    final String TAG = this.getClass().getName();

    Button btnLogin;
    EditText etxtUsuario, etxtPassword;
    CheckBox cbRemember;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private int id_dispositivo;
    private int id_empresa;

    boolean checkFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etxtUsuario = (EditText) findViewById(R.id.etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.etxtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);

        cbRemember.setOnCheckedChangeListener(this);
        checkFlag = cbRemember.isChecked();
        Log.d(TAG,"check flag"+checkFlag);
        btnLogin.setOnClickListener(this);

        //se crea el shared y el editor
        pref = getSharedPreferences("login.conf",Context.MODE_PRIVATE);
        editor = pref.edit();

        String username = pref.getString("nombre","");
        String password = pref.getString("password","");
        int idEmpresa = pref.getInt("idEmpresa",0);


        HashMap data = new HashMap();
        data.put("nombre", username);
        data.put("password", MD5.encrypt((password)));
        data.put("idEmpresa", idEmpresa);

        //TODO terminar la logica de que entre directo al main cuando el remember me este check
        //checkLog(username, password,idEmpresa);
    }




    @Override
    public void onClick(View v) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage("Por favor espere...");
        dialog.show();
        String  server_url="https://www.varcreative.com/sistema/login/check/";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Respuesta",response);

                try {
                    JSONObject respuesta = new JSONObject(response);
                    //cuando agregues al response el id_dispositivo, hay que descomentar y listo
                    //id_dispositivo = Integer.parseInt(respuesta.getString("id_dispositivo"))
                    String s = respuesta.getString("error");
                    if (s == "false"){
                        id_empresa = Integer.parseInt(respuesta.getString("id_empresa"));
                        if(checkFlag) {
                            editor.putString("nombre", etxtUsuario.getText().toString());
                            editor.putString("password",MD5.encrypt((etxtUsuario.getText().toString())));
                            editor.putInt("idEmpresa", id_empresa);
                            editor.apply();

                            Log.d(TAG, pref.getString("password", ""));
                        }

                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                        //in.putExtra("id_dispositivo",id_dispositivo);
                        int idEmpresa = pref.getInt("idEmpresa",0);
                        in.putExtra("id_empresa",idEmpresa);
                        startActivity(in);

                    }else {
                        Toast.makeText(LoginActivity.this, "Usuario o Password invalido", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

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
            params.put("password",  MD5.encrypt(etxtPassword.getText().toString()));
            params.put("nombre", etxtUsuario.getText().toString());

            return params;
        }};
        requestQueue.add(stringRequest);




    }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkFlag = isChecked;
            Log.d(TAG, "checkFlag: " + checkFlag);
        }

        public void checkLog(String username, String password,int id_empresa){
            if(!(username.equals("") && password.equals(""))){
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    in.putExtra("id_empresa",id_empresa);
                    startActivity(in);

            }
        }

    }



