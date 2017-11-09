package com.pampa.distribuidorachacabuco;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.Map;

import com.kosalgeek.android.md5simply.MD5;


public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    final String TAG = this.getClass().getName();

    Button btnLogin;
    EditText etxtUsuario, etxtPassword;
    CheckBox cbRemember;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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

        String username = pref.getString("email","");
        String password = pref.getString("password","");
        Log.d(TAG,pref.getString("password",""));

        HashMap data = new HashMap();
        data.put("email", username);
        data.put("password", MD5.encrypt((password)));


        if(!(username.equals("") && password.equals(""))){
            PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data,
                    new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            Log.d(TAG, s);
                            if(s.contains("success")){
                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(in);
                            }
                        }
                    });

            task.execute("www.varcreative.com/sistema/login/check");
        }
    }




    @Override
    public void onClick(View v) {
        /*HashMap data = new HashMap();
        data.put("email", etxtUsuario.getText().toString());
        data.put("password",  MD5.encrypt(etxtPassword.getText().toString()));

        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data,
                new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(TAG, s);
                        String[] sc = convertir(s);

                        if(sc[0]==("error:false")){

                            if(checkFlag) {
                                editor.putString("email", etxtUsuario.getText().toString());
                                editor.putString("password",MD5.encrypt((etxtUsuario.getText().toString())));
                                editor.apply();

                                Log.d(TAG, pref.getString("password", ""));
                            }

                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(in);
                        }
                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(in);
                    }
                });

        task.execute("https://www.varcreative.com/sistema/login/check/");*/
        String  server_url="https://www.varcreative.com/sistema/login/check/";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Respuesta",response);
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

    public String[] convertir(String s){
        String[] vectorVariables = s.split(",");

        return vectorVariables;
    };

    }



