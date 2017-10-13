package com.pampa.distribuidorachacabuco.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pampa.distribuidorachacabuco.AdminSQLiteOpenHelper;
import com.pampa.distribuidorachacabuco.Configuracion;

public class ConfiguracionModel extends AbstractModel {

    public ConfiguracionModel(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);
    }

    public Configuracion get() {
        String sql = "SELECT C.dispositivo, C.url, C.id_recorrido, R.nombre " +
                "FROM configuracion AS C LEFT JOIN recorridos AS R ON (C.id_recorrido = R._id) " +
                "WHERE C.id = 1";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Configuracion conf = new Configuracion();
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            conf.setDispositivo(c.getString(0));
            conf.setUrl(c.getString(1));
            conf.setId_recorrido(c.getInt(2));
            conf.setRecorrido(c.getString(3));
        }
        this.closeDB();
        return conf;
    }

    public void set(Configuracion c) {
        this.openWriteableDB();
        db.execSQL("UPDATE configuracion SET dispositivo = '"+c.getDispositivo()+"', url = '"+c.getUrl()+"', id_recorrido = "+String.valueOf(c.getId_recorrido())+" WHERE id = 1");
    }


}
