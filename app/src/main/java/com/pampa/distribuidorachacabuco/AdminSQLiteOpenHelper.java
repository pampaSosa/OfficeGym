package com.pampa.distribuidorachacabuco;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME="distri2.sqlite";
    private static final int DB_SCHEME_VERSION=1;

    public AdminSQLiteOpenHelper(Context context){
        super(context,DB_NAME, null, DB_SCHEME_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("create table articulos ( _id integer primary key autoincrement, codigo integer, descripcion text, marca text, precio_final real, precio_final_2 real, precio_final_3 real)");
        db.execSQL("create table clientes (id integer primary key autoincrement, nombre text, direccion text, lista integer, telefono text, cuit text, id_tipo_iva integer, uploaded integer)");
        db.execSQL("create table recorridos (_id integer primary key autoincrement, nombre text, reparto integer)");
        db.execSQL("create table recorridos_clientes (id_recorrido integer, id_cliente integer, orden integer, visitado integer, PRIMARY KEY (id_recorrido,id_cliente))");
        db.execSQL("create table facturas (id integer primary key autoincrement,id_tipo_estado integer ,id_cliente integer, total real, fecha string, hora string, observaciones text, uploaded integer, reparto integer)");
        db.execSQL("create table facturaItem (id integer primary key autoincrement,cantidad real,codigo_art integer,id_articulo integer, id_factura integer, tipo_cantidad text, neto real,uploaded integer,porc_bonif real)");
        db.execSQL("create table configuracion (id integer primary key autoincrement,dispositivo text,url text, id_recorrido integer)");
        db.execSQL("insert into configuracion (id,dispositivo,url,id_recorrido) values(1,'1','varcreative.com',0)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2){
        db.execSQL("drop table if exists clientes");
        db.execSQL("drop table if exists articulos");
        db.execSQL("drop table if exists recorridos");
        db.execSQL("drop table if exists recorridos_clientes");
        db.execSQL("drop table if exists facturas");
        db.execSQL("drop table if exists facturaItem");
        db.execSQL("drop table if exists configuracion");
        createTables(db);
    }

     public Cliente getClienteActual(){
         Cliente cliente = new Cliente();
         SQLiteDatabase bd = this.getWritableDatabase();
         String sql = "SELECT C.id, C.nombre, C.direccion, C.lista "+
                 "FROM recorridos_clientes AS RC INNER JOIN clientes AS C ON (RC.id_cliente = C.id) " +
                 "INNER JOIN configuracion AS CONF ON (RC.id_recorrido = CONF.id_recorrido) " +
                 "WHERE RC.visitado = 0 " +
                 "ORDER BY RC.orden ASC ";
         Cursor fila = bd.rawQuery(sql, null);
         if (fila.getCount() > 0) {
             fila.moveToFirst();
             cliente.setId(fila.getInt(0));
             cliente.setNombre(fila.getString(1));
             cliente.setDireccion(fila.getString(2));
             cliente.setLista(fila.getInt(3));
         } else {
             cliente.setId(0);
             cliente.setNombre("Sin recorrido");
             cliente.setDireccion("");
             cliente.setLista(1);
         }
         return cliente;
     }

     public void abrirBD(){
         this.getWritableDatabase();
     }

     public void cerrarBd(){
        this.close();
    }

}