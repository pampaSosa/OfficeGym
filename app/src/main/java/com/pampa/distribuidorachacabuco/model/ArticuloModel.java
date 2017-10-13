package com.pampa.distribuidorachacabuco.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pampa.distribuidorachacabuco.AdminSQLiteOpenHelper;
import com.pampa.distribuidorachacabuco.Articulo;
import com.pampa.distribuidorachacabuco.Factura;

import java.sql.Array;
import java.util.ArrayList;

public class ArticuloModel extends AbstractModel {


    public ArticuloModel(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);
    }

    private ContentValues articuloMapperContentValues(Articulo articulo) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", articulo.getCodigo());
        cv.put("descripcion", articulo.getDescripcion());
        cv.put("marca", articulo.getMarca());
        cv.put("precio_final",articulo.getPrecio_final());
        cv.put("costo_final",articulo.getCosto_final());

        return cv;
    }


    public Cursor getListaArticulos() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT _id, codigo, descripcion, marca FROM articulos";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    public Cursor  getArticulosPorLetra(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT codigo, descripcion, marca FROM articulos WHERE descripcion LIKE  '%" +search + "%' ";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Articulo getArticuloById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT _id,codigo,descripcion,marca,precio_final,precio_final_2,precio_final_3 FROM articulos WHERE _id = "+Id;
        Articulo articulo = new Articulo();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            // No se encontro el articulo, mandamos un articulo vacio con ID = 0
            articulo.setId(0);
            return articulo;
        }
        if (cursor.moveToFirst()) {
            do {
                articulo.setId(cursor.getInt(0));
                articulo.setCodigo(cursor.getInt(1));
                articulo.setDescripcion(cursor.getString(2));
                articulo.setMarca(cursor.getString(3));
                articulo.setPrecio_final(cursor.getFloat(4));
                articulo.setPrecio_final_2(cursor.getFloat(5));
                articulo.setPrecio_final_3(cursor.getFloat(6));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return articulo;
    }

    public Articulo getArticuloByCodigo(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT _id,codigo,descripcion,marca,precio_final,precio_final_2,precio_final_3 FROM articulos WHERE codigo = "+Id;
        Articulo articulo = new Articulo();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            // No se encontro el articulo, mandamos un articulo vacio con ID = 0
            articulo.setId(0);
            return articulo;
        }
        if (cursor.moveToFirst()) {
            do {
                articulo.setId(cursor.getInt(0));
                articulo.setCodigo(cursor.getInt(1));
                articulo.setDescripcion(cursor.getString(2));
                articulo.setMarca(cursor.getString(3));
                articulo.setPrecio_final(cursor.getFloat(4));
                articulo.setPrecio_final_2(cursor.getFloat(5));
                articulo.setPrecio_final_3(cursor.getFloat(6));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return articulo;
    }

    public void borrarTabla() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM articulos");
        db.execSQL("DELETE FROM clientes");
        db.execSQL("DELETE FROM recorridos");
        db.execSQL("DELETE FROM recorridos_clientes");
    }

    public void insertarDesdeServer(String respuesta) {
        String respuestaStr = respuesta;
        String[] arrayConsulta = respuestaStr.split("\n");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        for (int i = 0; i < arrayConsulta.length; i++) {
            System.out.println(arrayConsulta[i]);
            db.execSQL(arrayConsulta[i]);
            //Cursor cursor = db.rawQuery(arrayConsulta[i], null);
        }

    }
}
