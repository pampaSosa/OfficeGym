package com.pampa.distribuidorachacabuco.model;

import android.database.sqlite.SQLiteDatabase;

import com.pampa.distribuidorachacabuco.AdminSQLiteOpenHelper;

public abstract class AbstractModel {

    protected SQLiteDatabase db;
    protected AdminSQLiteOpenHelper dbHelper;

    protected void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }
    protected void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }
    protected void closeDB() { if(db!=null){ db.close(); } }
}
