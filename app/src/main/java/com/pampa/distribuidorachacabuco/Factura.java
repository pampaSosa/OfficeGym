package com.pampa.distribuidorachacabuco;

import java.util.ArrayList;

/**
 * Created by adrian on 19/06/2017.
 */

public class Factura {

    private int id;
    private int id_empresa;
    private float total;
    private float subTotal;
    private String fecha;
    private String hora;
    private String observaciones;
    private int id_cliente;
    private String nombre_cliente;
    private String direccion_cliente;
    private int id_tipo_estado;
    private ArrayList<FacturaItem> facturaItemsArray = new ArrayList<FacturaItem>();
    private int reparto;
    private int uploaded;
    private int id_recorrido;

    public ArrayList<FacturaItem> getFacturaItemsArray() {
        return facturaItemsArray;
    }

    public void setFacturaItemsArray(ArrayList<FacturaItem> facturaItemsArray) {
        this.facturaItemsArray = facturaItemsArray;
    }



    public int getId() {
        return id;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public float getTotal() {
        return total;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    //private date fecha;
    //private int hora;


    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", id_empresa=" + id_empresa +
                ", total=" + total +
                ", subTotal=" + subTotal +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", id_cliente=" + id_cliente +
                ", nombre_cliente='" + nombre_cliente + '\'' +
                ", direccion_cliente='" + direccion_cliente + '\'' +
                ", id_tipo_estado=" + id_tipo_estado +
                ", facturaItemsArray=" + facturaItemsArray +
                '}';
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccion_cliente = direccion_cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId_tipo_estado() {
        return id_tipo_estado;
    }

    public void setId_tipo_estado(int id_tipo_estado) {
        this.id_tipo_estado = id_tipo_estado;
    }

    public int getReparto() {
        return reparto;
    }

    public void setReparto(int reparto) {
        this.reparto = reparto;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public int getId_recorrido() {
        return id_recorrido;
    }

    public void setId_recorrido(int id_recorrido) {
        this.id_recorrido = id_recorrido;
    }
}
