package com.pampa.distribuidorachacabuco;

public class Articulo {
    private int id;
    private int codigo;
    private String descripcion;
    private String marca;
    private int id_empresa;
    private float costo_final;
    private float precio_final;
    private float precio_final_2;
    private float precio_final_3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public float getPrecio_final() {
        return precio_final;
    }

    public void setPrecio_final(float precio_final) {
        this.precio_final = precio_final;
    }

    public float getCosto_final() {
        return costo_final;
    }

    public void setCosto_final(float costo_final) {
        this.costo_final = costo_final;
    }

    public float getPrecio_final_2() {
        return precio_final_2;
    }

    public void setPrecio_final_2(float precio_final_2) {
        this.precio_final_2 = precio_final_2;
    }

    public float getPrecio_final_3() {
        return precio_final_3;
    }

    public void setPrecio_final_3(float precio_final_3) {
        this.precio_final_3 = precio_final_3;
    }
}
