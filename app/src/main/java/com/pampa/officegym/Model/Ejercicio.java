package com.pampa.officegym.Model;

import android.graphics.drawable.Drawable;

import java.util.Date;

/**
 * Created by adrian on 05/01/2018.
 */

public class Ejercicio {
    private int idEjercicio;
    private String titulo;
    private String texto;
    private String Link;
    private String link_youtube;
    private int id_categoria;
    private Date fecha;
    private Boolean mostrar_fecha;
    private Boolean destacado;

    public Ejercicio(int idEjercicio, String titulo, String texto, String link, String link_youtube, int id_categoria, Date fecha, Boolean mostrar_fecha, Boolean destacado) {
        this.idEjercicio = idEjercicio;
        this.titulo = titulo;
        this.texto = texto;
        Link = link;
        this.link_youtube = link_youtube;
        this.id_categoria = id_categoria;
        this.fecha = fecha;
        this.mostrar_fecha = mostrar_fecha;
        this.destacado = destacado;
    }

    public Ejercicio(String titulo, String breve) {
        this.titulo = titulo;
        this.texto = breve;

    }



    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getLink_youtube() {
        return link_youtube;
    }

    public void setLink_youtube(String link_youtube) {
        this.link_youtube = link_youtube;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getMostrar_fecha() {
        return mostrar_fecha;
    }

    public void setMostrar_fecha(Boolean mostrar_fecha) {
        this.mostrar_fecha = mostrar_fecha;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }
}
