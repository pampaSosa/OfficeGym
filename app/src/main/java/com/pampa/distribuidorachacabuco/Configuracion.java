package com.pampa.distribuidorachacabuco;

public class Configuracion {

    private String dispositivo = "";
    private String url = "";
    private int id_recorrido = 0;
    private String recorrido = "";

    public Configuracion() {
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId_recorrido() { return id_recorrido; }

    public void setId_recorrido(int id_recorrido) {
        this.id_recorrido = id_recorrido;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }
}
