package com.pampa.distribuidorachacabuco.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pampa.distribuidorachacabuco.AdminSQLiteOpenHelper;
import com.pampa.distribuidorachacabuco.Cliente;
import com.pampa.distribuidorachacabuco.Factura;

import java.util.ArrayList;

public class ClienteModel extends AbstractModel {

    public ClienteModel(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);
    }

    public long insert(Cliente c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre",c.getNombre());
        cv.put("direccion",c.getDireccion());
        cv.put("telefono",c.getTelefono());
        cv.put("cuit",c.getCuit());
        cv.put("id_tipo_iva",c.getId_tipo_iva());
        cv.put("uploaded",0);
        long id = db.insert("clientes",null,cv);
        return id;
    }

    public Cliente getClienteById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT C.id, C.nombre, C.direccion, C.lista, C.cuit, C.telefono, C.id_tipo_iva, C.uploaded FROM clientes C WHERE C.id = "+id;
        Cliente cliente = new Cliente();
        Cursor fila = db.rawQuery(selectQuery, null);
        if (fila.getCount() > 0) {
            fila.moveToFirst();
            cliente.setId(fila.getInt(0));
            cliente.setNombre(fila.getString(1));
            cliente.setDireccion(fila.getString(2));
            cliente.setLista(fila.getInt(3));
            cliente.setCuit(fila.getString(4));
            cliente.setTelefono(fila.getString(5));
            cliente.setId_tipo_iva(fila.getInt(6));
            cliente.setUploaded(fila.getInt(7));
        } else {
            cliente.setId(0);
            cliente.setNombre("Sin recorrido");
            cliente.setDireccion("");
            cliente.setLista(1);
            cliente.setCuit("");
            cliente.setTelefono("");
            cliente.setId_tipo_iva(4);
            cliente.setUploaded(0);
        }
        return cliente;
    }

    public ArrayList<Cliente> getNuevos() {
        this.openReadableDB();
        ArrayList list = new ArrayList<Cliente>();
        String sql ="SELECT id FROM clientes WHERE uploaded = 0";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            Cliente f = this.getClienteById(id);
            list.add(f);
        }
        this.closeDB();
        return list;
    }
}
