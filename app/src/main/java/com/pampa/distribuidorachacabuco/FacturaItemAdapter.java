package com.pampa.distribuidorachacabuco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.pampa.distribuidorachacabuco.model.FacturaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 27/06/2017.
 */

public class FacturaItemAdapter extends BaseAdapter {
    CargarPedidoActivity activity;
    ArrayList<FacturaItem> itemsArray;

    public FacturaItemAdapter(Activity activity, ArrayList<FacturaItem> items){
        this.activity = (CargarPedidoActivity) activity;
        this.itemsArray = items;
    }

    @Override
    public int getCount() {
        return itemsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemsArray.get(position).getCodigoArticulo();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_item_row,null);
        }
        FacturaItem facturaItem = itemsArray.get(position);
        TextView codigo = (TextView) v.findViewById(R.id.codigoTxt);
        TextView cantidad = (TextView) v.findViewById(R.id.canTxtView);
        TextView descripcion = (TextView) v.findViewById(R.id.descripcionTxtView);
        TextView precio = (TextView) v.findViewById(R.id.PrecioTxtView);
        TextView porcBonifTxt = (TextView) v.findViewById(R.id.porcBonifTxt);

        String desc = String.valueOf(facturaItem.getDescripcion());
        if (facturaItem.getTipoCantidad().equals("Bonificado")) {
            desc = "Bonif: "+desc;
        } else if (facturaItem.getTipoCantidad().equals("Devolucion")) {
            desc = "Dev: "+desc;
        }
        codigo.setText(String.valueOf(facturaItem.getCodigoArticulo()));
        cantidad.setText(String.valueOf(facturaItem.getCantidad()));
        descripcion.setText(desc);
        double total = Math.round(facturaItem.getPrecio() * 100.0) / 100.0;
        precio.setText(String.valueOf(total));
        porcBonifTxt.setText(String.valueOf(facturaItem.getPorc_bonif()));

        Button btnDel = (Button) v.findViewById(R.id.lvDelBtn);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Borrar el elemento?");
                builder.setTitle("Atencion");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        itemsArray.remove(position);
                        notifyDataSetChanged();
                        activity.calcularTotales();
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
        return v;
    }


}
