package com.example.tech.Clases;

public class Reto {

    private boolean Aleatorio;
    private String Descripcion;
    private String EmpresaId;
    private String Lenguaje;
    private String Nombre;
    private int NumIntentos;
    private int NumPreguntas;
    private int TiempoLimite;
    private String TipoReto;

    public Reto() {
    }

    public boolean isAleatorio() {
        return Aleatorio;
    }

    public void setAleatorio(boolean aleatorio) {
        Aleatorio = aleatorio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEmpresaId() {
        return EmpresaId;
    }

    public void setEmpresaId(String empresaId) {
        EmpresaId = empresaId;
    }

    public String getLenguaje() {
        return Lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        Lenguaje = lenguaje;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getNumIntentos() {
        return NumIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        NumIntentos = numIntentos;
    }

    public int getNumPreguntas() {
        return NumPreguntas;
    }

    public void setNumPreguntas(int numPreguntas) {
        NumPreguntas = numPreguntas;
    }

    public int getTiempoLimite() {
        return TiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
        TiempoLimite = tiempoLimite;
    }

    public String getTipoReto() {
        return TipoReto;
    }

    public void setTipoReto(String tipoReto) {
        TipoReto = tipoReto;
    }
}
