package com.pampa.distribuidorachacabuco.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pampa.distribuidorachacabuco.AdminSQLiteOpenHelper;
import com.pampa.distribuidorachacabuco.Articulo;
import com.pampa.distribuidorachacabuco.Cliente;
import com.pampa.distribuidorachacabuco.Factura;
import com.pampa.distribuidorachacabuco.FacturaItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FacturaModel extends AbstractModel {

    private static final String LOGTAG = "LogsAndroid";
    public static final String CLIENTE_ID = "id_cliente";

    public FacturaModel(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);
    }

    public FacturaModel() {
    }

    private ContentValues facturaMapperContentValues(Factura factura) {
        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        cv.put("id_cliente", factura.getId_cliente());
        cv.put("uploaded",0);
        cv.put("fecha", factura.getFecha());
        cv.put("hora",sdf1.format(new Date()).toString());
        cv.put("reparto",factura.getReparto());
        cv.put("id_tipo_estado",factura.getId_tipo_estado());
        cv.put("observaciones",factura.getObservaciones());
        return cv;
    }

    public void guardarFactura(Factura factura) {
        this.openWriteableDB();
        if (factura.getId() == 0) {
            //Se inserta y se obtiene el ultimo id insertado
            long rowId = db.insert("facturas",null,facturaMapperContentValues(factura));
            String sql = "UPDATE recorridos_clientes SET visitado = 1 WHERE id_cliente = "+String.valueOf(factura.getId_cliente())+" AND id_recorrido = "+String.valueOf(factura.getId_recorrido());
            db.execSQL(sql);
            factura.setId((int)rowId);
        } else {
            db.execSQL("UPDATE facturas SET observaciones = '"+factura.getObservaciones()+"' WHERE id = "+String.valueOf(factura.getId()));
            // Borramos todas los items para volverlos a insertar luego
            db.execSQL("DELETE FROM facturaItem WHERE id_factura = "+String.valueOf(factura.getId()));
        }
        for (int i=0; i< factura.getFacturaItemsArray().size();i++){
            FacturaItem f = factura.getFacturaItemsArray().get(i);
            String sql = "INSERT INTO facturaItem (cantidad, codigo_art,id_articulo,id_factura,uploaded,tipo_cantidad,porc_bonif) VALUES ("+f.getCantidad()+","+f.getCodigoArticulo()+","+f.getId_articulo()+","+factura.getId()+",0,'"+f.getTipoCantidad()+"',"+f.getPorc_bonif()+")";
            Log.i(LOGTAG, sql);
            db.execSQL(sql);
        }
        this.closeDB();
    }

    public ArrayList<Factura> getNuevas() {
        this.openReadableDB();
        ArrayList list = new ArrayList<Factura>();
        String sql ="SELECT id FROM facturas WHERE uploaded = 0";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            Log.i(LOGTAG, String.valueOf(id));
            Factura f = this.get(id);
            list.add(f);
        }
        this.closeDB();
        return list;
    }

    public void limpiar_facturas() {
        this.openWriteableDB();
        db.execSQL("UPDATE facturas SET uploaded = 1");
        db.execSQL("UPDATE facturaItem SET uploaded = 1");
        this.closeDB();
    }

    public Factura get(int id) {
        openReadableDB();
        String sql = "SELECT * FROM facturas WHERE id = "+String.valueOf(id);
        Cursor c = db.rawQuery(sql, null);
        Factura factura = new Factura();
        while (c.moveToNext()) {
            factura.setId(c.getInt(0));
            factura.setId_tipo_estado(c.getInt(1));
            factura.setId_cliente(c.getInt(2));
            factura.setTotal(c.getFloat(3));
            factura.setFecha(c.getString(4));
            factura.setHora(c.getString(5));
            factura.setObservaciones(c.getString(6));
            factura.setReparto(c.getInt(8));

            String sql2 = "SELECT FI.id, FI.cantidad, FI.codigo_art, FI.id_articulo, FI.id_factura, FI.tipo_cantidad, FI.neto, A.descripcion, FI.porc_bonif " +
                    "FROM facturaItem AS FI INNER JOIN articulos AS A ON (FI.id_articulo = A._id) " +
                    "WHERE id_factura = "+String.valueOf(factura.getId());
            Cursor c2 = db.rawQuery(sql2,null);

            ArrayList items = new ArrayList<FacturaItem>();
            while(c2.moveToNext()) {
                FacturaItem item = new FacturaItem();
                item.setCantidad(c2.getFloat(1));
                item.setCodigoArticulo(c2.getInt(2));
                item.setId_articulo(c2.getInt(3));
                item.setId_factura(c2.getInt(4));
                item.setTipoCantidad(c2.getString(5));
                item.setNeto(c2.getFloat(6));
                item.setDescripcion(c2.getString(7));
                item.setPorc_bonif(c2.getFloat(8));
                items.add(item);
            }
            factura.setFacturaItemsArray(items);
        }
        return factura;
    }


   public ArrayList<Factura> get_list() {
       this.openReadableDB();
       ArrayList list = new ArrayList<>();
       String innerJoinConsulta ="SELECT f.id ,f.id_cliente,f.fecha,f.hora, cli.nombre, cli.direccion, F.uploaded, F.reparto FROM facturas AS f INNER JOIN clientes AS cli ON f.id_cliente = cli.id ORDER BY F.id DESC LIMIT 0,100";
       Cursor c = db.rawQuery(innerJoinConsulta, null);
        try {
            while (c.moveToNext()) {
                Factura factura = new Factura();
                factura.setId(c.getInt(0));
                factura.setId_cliente(c.getInt(1));
                factura.setFecha(c.getString(2));
                factura.setHora(c.getString(3));
                factura.setNombre_cliente(c.getString(4));
                factura.setDireccion_cliente(c.getString(5));
                factura.setUploaded(c.getInt(6));
                factura.setReparto(c.getInt(7));
                list.add(factura);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public void borrarSeleccionado(int idClicked) {
        this.openWriteableDB();
        //String where = String.valueOf(idClicked) + "= id";
        //db.delete("facturas", where, new String[]{String.valueOf(idClicked)});
        db.execSQL("DELETE FROM facturas where id =" +idClicked);
        this.closeDB();
    }

}
