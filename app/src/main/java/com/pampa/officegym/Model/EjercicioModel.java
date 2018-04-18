package com.pampa.officegym.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pampa.officegym.ContenidoMain;
import com.pampa.officegym.Fragments.VideoFragment;
import com.pampa.officegym.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.android.volley.Request.*;

/**
 * Created by adrian on 22/01/2018.
 */

public class EjercicioModel {
    String urlGetVideos ="https://www.varcreative.com/sistema/laboral_gym/function/get_publicaciones/";
    private RequestQueue requestQueue;

   /* String json= "[{titulo: titulo1,subtitulo: subtiulo 1,texto: texto simulado texto simuladotexto simuladotexto simuladotexto simuladotexto simuladotexto simulado,link: www.varcreative.com/videos,id_categoria: visio,fecha: 01/02/2018,mostrar_fecha: 1,destacado: 1,realizable: 1,realizado: 0}, {titulo: titulo1,subtitulo: subtiulo 1,texto: texto simulado texto simuladotexto simuladotexto simuladotexto simuladotexto simuladotexto simulado,link: www.varcreative.com/videos,id_categoria: visio,fecha: 01/02/2018,mostrar_fecha: 1,destacado: 1,realizable: 1,realizado: 0}]";
    Gson gson = new Gson();
    Ejercicio[] ejercicios = gson.fromJson(json, Ejercicio[].class);
*/
    //String tituloEjer = "Titulo del texto";
    //String lorem ="Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. ";
    public ArrayList<Ejercicio> getEjerciciosFotos(){

        ArrayList<Ejercicio> ejercicios=new ArrayList<>();
        //Ejercicio ejercicio =new Ejercicio("Nombre ejer1",lorem, "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg"");
       // ejercicios.add(ejercicio);
        //Ejercicio ejercicio2 =new Ejercicio("Nombre ejer2",lorem,"https://i0.wp.com/www.elblogdeyes.com/wp-content/uploads/2016/07/gym.jpg");
       // ejercicios.add(ejercicio2);


        return ejercicios;

    }

    /*public ArrayList<Ejercicio>getEjerciciosTextos(){
        /*ArrayList<Ejercicio> ejercicios=new ArrayList<>();
        Ejercicio ejercicio =new Ejercicio("titulo del texto 1",lorem);
        Ejercicio ejercicio2 =new Ejercicio("titulo del texto 2",lorem);
        Ejercicio ejercicio3 =new Ejercicio("titulo del texto 3",lorem);
        Ejercicio ejercicio4 =new Ejercicio("titulo del texto 4",lorem);
        Ejercicio ejercicio5 =new Ejercicio("titulo del texto 5",lorem);
        ejercicios.add(ejercicio);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);
        ejercicios.add(ejercicio4);
        ejercicios.add(ejercicio5);
        return ejercicios;
    }*/



    /*public ArrayList<Ejercicio> getEjerciciosVideos(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
        final ArrayList<Ejercicio> arrayEjercicio = new ArrayList<Ejercicio>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Method.POST,urlGetVideos,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    int count = 0;
                    while (count<response.length()){
                        try {
                            JSONObject jsonObject = response.getJSONObject(count);
                            Ejercicio ejercicio = new Ejercicio(jsonObject.getString("titulo"),jsonObject.getString("breve"));
                            arrayEjercicio.add(ejercicio);
                            count ++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(this,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }
        );
        requestQueue.add(jsonArrayRequest);
        return arrayEjercicio;
       return null;
    }*/

    public ArrayList<Ejercicio>getEjerciciosVideos(){
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        formateador.format(ahora);
        ArrayList<Ejercicio> ejercicios=new ArrayList<>();
        Ejercicio ejercicio1 = new Ejercicio(1,"titulo ejercicio 1",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "https://ep00.epimg.net/elpais/imagenes/2016/12/07/album/1481112708_143319_1481113244_album_normal.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio2 = new Ejercicio(2,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio3 = new Ejercicio(3,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio4 = new Ejercicio(4,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);
        ejercicios.add(ejercicio4);

        return ejercicios;
    }

    public ArrayList<Ejercicio>getEjerciciosVideosCat(){
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        formateador.format(ahora);
        ArrayList<Ejercicio> ejercicios=new ArrayList<>();
        Ejercicio ejercicio1 = new Ejercicio(1,"titulo ejercicio 1",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "https://ep00.epimg.net/elpais/imagenes/2016/12/07/album/1481112708_143319_1481113244_album_normal.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio2 = new Ejercicio(2,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio3 = new Ejercicio(3,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        Ejercicio ejercicio4 = new Ejercicio(4,"titulo ejercicio 2",
                "texto breve, texto breve,texto brevetexto brevetexto brevetexto brevetexto breve",
                "http://buscasalud.com/wp-content/uploads/2017/01/fitness2.jpg",
                "https://www.youtube.com/watch?v=c1c3dvmGWZ4",1, ahora,true,false );
        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);
        ejercicios.add(ejercicio4);

        return ejercicios;
    }





}
