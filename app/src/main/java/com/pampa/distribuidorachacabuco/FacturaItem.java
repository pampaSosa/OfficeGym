package com.pampa.distribuidorachacabuco;

/**
 * Created by adrian on 19/06/2017.
 */

public class FacturaItem {

    private int id_facturaItem;
    private int id_empresa;
    private float cantidad;
    private int id_factura;
    private String tipoCantidad;
    private String marca;
    private String descripcion;
    private float neto;
    private double precio;
    private int codigoArticulo;
    private int id_articulo;
    private float porc_bonif;

    public FacturaItem(int i, int i1, int i2, int i3, int i4) {
    }

    public FacturaItem() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(int codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getId_facturaItem() {
        return id_facturaItem;
    }

    public void setId_facturaItem(int id_facturaItem) {
        this.id_facturaItem = id_facturaItem;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(int id_articulo) {
        this.id_articulo = id_articulo;
    }


    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public float getNeto() {
        return neto;
    }

    public void setNeto(float neto) {
        this.neto = neto;
    }

    public float getPorc_bonif() {
        return porc_bonif;
    }

    public void setPorc_bonif(float porc_bonif) {
        this.porc_bonif = porc_bonif;
    }
}
