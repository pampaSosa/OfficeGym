package com.pampa.distribuidorachacabuco;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pampa.distribuidorachacabuco.model.ArticuloModel;
import com.pampa.distribuidorachacabuco.model.ClienteModel;
import com.pampa.distribuidorachacabuco.model.FacturaModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CargarPedidoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGTAG = "LogsAndroid";

    private boolean guardando = false;
    TextView actualClientTextView;
    private FacturaModel facturaModel = new FacturaModel(this);
    private ArticuloModel articuloModel = new ArticuloModel(this);
    ArrayList<FacturaItem> facturaItemsArray = new ArrayList<FacturaItem>();
    FacturaItemAdapter facturaItemAdapter;
    ArrayList<Articulo> articulosArray = new ArrayList<Articulo>();

    private EditText editTextCant;
    EditText editText;
    private TextView txtViewDescripcion;
    private TextView tViewPrecio;
    private Spinner tipoCantidadSelect;
    private EditText observacionesText;
    private EditText porcBonifTxt;
    private Button buscarArticulosBtn;
    private EditText repartoTxt;

    private Factura factura;
    private Articulo articuloActual = null;
    private Cliente clienteActual = null;

    private int dia, mes, anio;
    Button pickDate;
    TextView dateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_pedido);
        //int reparto = getIntent().getIntExtra("reparto",0);
        int id_cliente = getIntent().getIntExtra("id_cliente",0);
        Log.i("CLIENTE",String.valueOf(id_cliente));
        ClienteModel clienteModel = new ClienteModel(this);
        clienteActual = clienteModel.getClienteById(id_cliente);
        FacturaModel facturaModel = new FacturaModel(this);

        editTextCant = (EditText)findViewById(R.id.editTextCant);
        editText = (EditText)findViewById(R.id.editText);
        observacionesText = (EditText)findViewById(R.id.observacionesText);
        tipoCantidadSelect = (Spinner)findViewById(R.id.spinner);
        porcBonifTxt = (EditText)findViewById(R.id.porcBonifTxt);
        repartoTxt = (EditText)findViewById(R.id.repartoTxt);
        buscarArticulosBtn = (Button)findViewById(R.id.buscarArticulosBtn);

        int id_factura = getIntent().getIntExtra("id_factura",0);
        if (id_factura == 0) {
            // Es una factura nueva
            this.factura = new Factura();
            factura.setReparto(1);
            factura.setId_cliente(id_cliente);

            // Ponemos el recorrido en la factura, para que al momento de guardarla
            // se marque como que ya se visito a ese cliente
            factura.setId_recorrido(getIntent().getIntExtra("id_recorrido",0));

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            factura.setFecha(sdf.format(cal.getTime()));

        } else {
            // Buscar en la base de datos la factura para editar
            factura = facturaModel.get(id_factura);
            this.facturaItemsArray = factura.getFacturaItemsArray();
        }

        actualClientTextView = (TextView) findViewById(R.id.clientNameTextView);
        actualClientTextView.setText(clienteActual.getNombre());
        observacionesText.setText(factura.getObservaciones());
        repartoTxt.setText(String.valueOf(factura.getReparto()));
        buscarArticulosBtn.setTypeface(FontManager.getTypeface(this.getApplicationContext(),FontManager.FONTAWESOME));

        final ListView listView = (ListView) findViewById(R.id.listViewPedidos);
        txtViewDescripcion = (TextView) findViewById(R.id.txtViewDescripcion);
        tViewPrecio = (TextView) findViewById(R.id.txtViewPrecio);

        facturaItemAdapter = new FacturaItemAdapter(this,this.facturaItemsArray);
        listView.setAdapter(facturaItemAdapter);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String codigo = editText.getText().toString();
                    int cod = 0;
                    try {
                        cod = Integer.parseInt(codigo);
                    } catch(Exception e) {
                        // Mostramos un alert que no encuentra el articulo
                        final AlertDialog alertDialog1 = new AlertDialog.Builder(CargarPedidoActivity.this).create();
                        alertDialog1.setTitle("Error");
                        alertDialog1.setMessage("El codigo ingresado es incorrecto.");
                        alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1.show();
                        return false;
                    }
                    articuloActual = articuloModel.getArticuloByCodigo(cod);

                    if (articuloActual.getId() == 0) {
                        // Mostramos un alert que no encuentra el articulo
                        final AlertDialog alertDialog = new AlertDialog.Builder(CargarPedidoActivity.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("El codigo "+codigo+" no existe.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return false;
                    }
                    txtViewDescripcion.setText(articuloActual.getDescripcion());
                    float precio_final = 0;
                    if (clienteActual.getLista() == 0) {
                        precio_final = articuloActual.getPrecio_final();
                    } else if (clienteActual.getLista() == 1) {
                        precio_final = articuloActual.getPrecio_final_2();
                    } else if (clienteActual.getLista() == 2) {
                        precio_final = articuloActual.getPrecio_final_3();
                    }
                    tViewPrecio.setText(Float.toString(precio_final));
                    editTextCant.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editTextCant.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    cargarItems(listView);
                    editText.setText("");
                    editTextCant.setText("");
                    txtViewDescripcion.setText("");
                    tViewPrecio.setText("");
                    tipoCantidadSelect.setSelection(0);
                    editText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        pickDate = (Button)findViewById(R.id.pickDate);
        pickDate.setTypeface(FontManager.getTypeface(this.getApplicationContext(),FontManager.FONTAWESOME));
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        pickDate.setOnClickListener(this);
        dateDisplay.setText(factura.getFecha());
    }

    public void cargarItems(View view){
       if (articuloActual != null) {
           if (articuloActual.getId() != 0) {

               float porcBonif = 0;
               try {
                   String porcBonifStr = porcBonifTxt.getText().toString();
                   if (!("".equals(porcBonifStr))) {
                       porcBonif = Float.parseFloat(porcBonifStr);
                   }
               } catch(Exception e) {
                   final AlertDialog alertDialog = new AlertDialog.Builder(CargarPedidoActivity.this).create();
                   alertDialog.setTitle("Error");
                   alertDialog.setMessage("El porcentaje de bonificacion debe ser un numero.");
                   alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           alertDialog.dismiss();
                       }
                   });
                   alertDialog.show();
                   return;
               }

               try {
                   float cantidad = Float.parseFloat(editTextCant.getText().toString());
                   String tipoCantidad = tipoCantidadSelect.getSelectedItem().toString();

                   FacturaItem facturasItem = new FacturaItem();
                   facturasItem.setCodigoArticulo(articuloActual.getCodigo());
                   facturasItem.setCantidad(cantidad);
                   facturasItem.setDescripcion(articuloActual.getDescripcion());
                   facturasItem.setTipoCantidad(tipoCantidad);
                   facturasItem.setId_articulo(articuloActual.getId());
                   float precio = 0;
                   if (clienteActual.getLista() == 0) {
                       precio = articuloActual.getPrecio_final();
                   } else if (clienteActual.getLista() == 1) {
                       precio = articuloActual.getPrecio_final_2();
                   } else if (clienteActual.getLista() == 2) {
                       precio = articuloActual.getPrecio_final_3();
                   }

                   // Si es Devolucion
                   if ("devolucion".equals(tipoCantidad.toLowerCase())) {
                       precio = 0;
                   }

                   facturasItem.setPrecio(precio);
                   facturasItem.setPorc_bonif(porcBonif);
                   this.facturaItemsArray.add(facturasItem);
                   factura.setFacturaItemsArray(facturaItemsArray);
                   this.calcularTotales();

                   facturaItemAdapter.notifyDataSetChanged();

               } catch(Exception e) {
                   final AlertDialog alertDialog = new AlertDialog.Builder(CargarPedidoActivity.this).create();
                   alertDialog.setTitle("Error");
                   alertDialog.setMessage("La cantidad ingresada no es valida.");
                   alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           alertDialog.dismiss();
                       }
                   });
                   alertDialog.show();
                   return;
               }
           }
       }
    }

    public void calcularTotales() {
        double total = 0;
        for (int i=0; i<this.facturaItemsArray.size();i++) {
            FacturaItem item = this.facturaItemsArray.get(i);
            double subtotal = item.getCantidad() * item.getPrecio() * ((100-item.getPorc_bonif()) / 100);
            total = total + subtotal;
        }
        total = Math.round(total * 100.0) / 100.0;
        this.factura.setTotal((float)total);
        TextView totalLabel = (TextView) findViewById(R.id.totalLabel);
        totalLabel.setText("TOTAL: $ "+String.valueOf(total));
    }

    public void guardar(View view){
        if (this.guardando) return;
        int reparto = 0;
        try {
            String repartoStr = repartoTxt.getText().toString().trim();
            if (!("".equals(repartoStr))) {
                reparto = Integer.parseInt(repartoStr);
            }
        } catch(Exception e) {
            final AlertDialog alertDialog = new AlertDialog.Builder(CargarPedidoActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("El reparto debe ser un numero.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return;
        }
        this.guardando = true;
        this.factura.setObservaciones(observacionesText.getText().toString().trim());
        this.factura.setReparto(reparto);
        this.factura.setFecha(dateDisplay.getText().toString());
        this.facturaModel.guardarFactura(factura);
        finish();
    }

    public void guardar_sin_pedido(View view){
        if (this.guardando) return;
        this.guardando = true;
        this.factura.setObservaciones(observacionesText.getText().toString().trim());

        CharSequence opciones[] = new CharSequence[]{"No quiere nada.", "No estaba el cliente.", "Llamar por telefono.", "Otro"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                guardando = false;
            }
        });
        builder.setTitle("Motivo");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Las opciones guardadas en el panel empiezan de 100.. por lo que simplemente sumamos 100 al numero de opcion
                CargarPedidoActivity.this.factura.setId_tipo_estado(which + 100);
                facturaModel.guardarFactura(CargarPedidoActivity.this.factura);
                finish();
            }
        });
        builder.show();
    }

    public void buscarArticulos(View view) {
        Intent intent = new Intent(this, BuscarArticuloActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                int id = Integer.parseInt(data.getStringExtra("id"));
                articuloActual = articuloModel.getArticuloById(id);
                editText.setText(String.valueOf(articuloActual.getCodigo()));
                txtViewDescripcion.setText(articuloActual.getDescripcion());
                float precio_final = 0;
                if (clienteActual.getLista() == 0) {
                    precio_final = articuloActual.getPrecio_final();
                } else if (clienteActual.getLista() == 1) {
                    precio_final = articuloActual.getPrecio_final_2();
                } else if (clienteActual.getLista() == 2) {
                    precio_final = articuloActual.getPrecio_final_3();
                }
                tViewPrecio.setText(Float.toString(precio_final));
                editTextCant.requestFocus();
            }
        } catch(Exception e) {
            // Se pone en un try catch porque si se hace click en ATRAS sin elegir un codigo tira error
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            // Solamente se puede hacer para atras cuando estamos editando o el pedido es nuevo y esta vacio
            if ((factura.getId() != 0) || (factura.getId() == 0 && facturaItemsArray.size() == 0)) {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        /*
        if (v == pickDate) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR,year);
                    cal.set(Calendar.MONTH,month);
                    cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dateDisplay.setText(sdf.format(cal.getTime()));
                }
            }, anio, mes, dia);
            datePickerDialog.show();
        }
        */
    }
}
