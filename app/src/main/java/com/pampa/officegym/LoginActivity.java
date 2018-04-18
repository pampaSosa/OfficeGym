package com.pampa.officegym;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    EditText etxtUsuario, etxtPassword;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private int id_empresa;
    TextView registrateTw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etxtUsuario = (EditText) findViewById(R.id.etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.etxtPassword);
        btnLogin.setOnClickListener(this);
        registrateTw = findViewById(R.id.registrarteTw);


        //se crea el shared y el editor

        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor = pref.edit();
        String username = pref.getString("nombre","");
        String password = pref.getString("password","");

        HashMap data = new HashMap();
        data.put("nombre", username);
        data.put("password", MD5.encrypt((password)));
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);

        /*final ProgressDialog dialog = new ProgressDialog(this);
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
                    String s = respuesta.getString("error");
                    if (s == "false"){
                        id_empresa = Integer.parseInt(respuesta.getString("id_empresa"));
                        editor.putString("nombre", etxtUsuario.getText().toString());
                        editor.putString("password",MD5.encrypt((etxtUsuario.getText().toString())));
                        editor.putInt("idEmpresa", id_empresa);
                        editor.apply();

                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
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
        requestQueue.add(stringRequest);*/
    }

    public void registroIntent(View view){
        Intent in = new Intent(LoginActivity.this, RegistrarActivity.class);
        startActivity(in);
    }

}
