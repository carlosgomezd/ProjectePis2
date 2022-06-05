package com.example.projectepis;

public class Perfil {

    private String  birthday, email, genero, nombre, password,token,datosP,direccion,telefono,imagen;

    public Perfil() {
    }

    public Perfil( String birthday, String email, String genero, String nombre, String password, String token, String datosP, String direccion, String telefono, String imagen) {

        this.birthday = birthday;
        this.email = email;
        this.genero = genero;
        this.nombre = nombre;
        this.password = password;
        this.token = token;
        this.datosP = datosP;
        this.direccion = direccion;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatosP() {
        return datosP;
    }

    public void setDatosP(String datosP) {
        this.datosP = datosP;
    }
}
