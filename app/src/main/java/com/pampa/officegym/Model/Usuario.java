package com.pampa.officegym.Model;

import java.util.ArrayList;

/**
 * Created by adrian on 16/02/2018.
 */

public class Usuario {

    private int id_usuario;
    private String nombre;
    private String apellido;
    private boolean fuma;

    private ArrayList<Ejercicio> ejerciciosRealizados;



    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isFuma() {
        return fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public ArrayList<Ejercicio> getEjerciciosRealizados() {
        return ejerciciosRealizados;
    }

    public void setEjerciciosRealizados(ArrayList<Ejercicio> ejerciciosRealizados) {
        this.ejerciciosRealizados = ejerciciosRealizados;
    }
}
