package com.example.tech.Clases;

public class Empresa {

    private boolean bAux;
    private String descripcion;
    public String email;
    private String nombre;
    private String nroEmpleados;
    private String psw;
    private String sede;
    private String telefono;
    private String urlImage;

    public Empresa() {
    }

    public Empresa(String descripcion, String nombre, String urlImage) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.urlImage = urlImage;
    }

    public Empresa(String email) {
        this.email = email;
    }

    public boolean isbAux() {
        return bAux;
    }

    public void setbAux(boolean bAux) {
        this.bAux = bAux;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroEmpleados() {
        return nroEmpleados;
    }

    public void setNroEmpleados(String nroEmpleados) {
        this.nroEmpleados = nroEmpleados;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
