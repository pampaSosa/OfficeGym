package com.pampa.distribuidorachacabuco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pampa.distribuidorachacabuco.model.FacturaModel;

import java.util.ArrayList;

/**
 * Created by adrian on 05/07/2017.
 */

public class ListarPedidosAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Factura> facturaArray;
    private Context context;

    Factura factura = new Factura();

    public ListarPedidosAdapter(Activity activity, ArrayList<Factura> items){
        this.activity = activity;
        this.facturaArray = items;
    }

    @Override
    public int getCount() {
        return facturaArray.size();
    }

    @Override
    public Object getItem(int position) {
        return facturaArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return facturaArray.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listar_pedidos_item,null);
        }
        final Factura factura = facturaArray.get(position);
        TextView nombre = (TextView) v.findViewById(R.id.clienteTview);
        TextView direccion = (TextView) v.findViewById(R.id.direccionTview);
        TextView reparto = (TextView) v.findViewById(R.id.itemsTview);

        nombre.setText(String.valueOf(factura.getNombre_cliente()));
        direccion.setText(String.valueOf(factura.getDireccion_cliente()));
        reparto.setText(factura.getHora()+" - Reparto: "+String.valueOf(factura.getReparto()));

        Button btnDel = (Button) v.findViewById(R.id.btnBorrarPedido);
        btnDel.setTypeface(FontManager.getTypeface(this.activity.getApplicationContext(),FontManager.FONTAWESOME));
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Desea eliminar el pedido?");
                builder.setTitle("Atencion");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FacturaModel facturaModel = new FacturaModel(activity);
                        facturaModel.borrarSeleccionado(factura.getId());
                        facturaArray.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button btnEdit = (Button) v.findViewById(R.id.btnEditarPedido);
        btnEdit.setTypeface(FontManager.getTypeface(this.activity.getApplicationContext(),FontManager.FONTAWESOME));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacturaModel facturaModel = new FacturaModel(activity);
                Factura f = facturaModel.get(factura.getId());
                Intent intent = new Intent(activity, CargarPedidoActivity.class);
                intent.putExtra("id_cliente",f.getId_cliente());
                intent.putExtra("id_factura",factura.getId());
                intent.putExtra("id_recorrido",0);
                activity.startActivity(intent);
            }
        });

        // Si la factura fue subida, no podemos borrar, entonces ocultamos el boton
        /*if (factura.getUploaded() == 1) {
            btnDel.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }*/
        return v;
    }
}
