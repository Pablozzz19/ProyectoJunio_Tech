package com.example.tech.Clases;

public class Usuario {

    private boolean uBAux;
    private int uFoto;
    private String apellidos;
    private String descripcion;
    public String email;
    private String fechaNacimiento;
    private String lenguaje;
    private String nombre;
    private String telefono;

    public Usuario() {
    }

    /*public Usuario(String email) {
        this.email = email;
    }*/

    /*public Usuario(int uFoto, String uApellidos, String uDescripcion, String uNombre) {
        this.uFoto = uFoto;
        this.uApellidos = uApellidos;
        this.uDescripcion = uDescripcion;
        this.uNombre = uNombre;
    }*/

    /*public Usuario(String apellidos, String descripcion, String nombre) {
        this.apellidos = apellidos;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }*/

    public boolean isuBAux() {
        return uBAux;
    }

    public void setuBAux(boolean uBAux) {
        this.uBAux = uBAux;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
