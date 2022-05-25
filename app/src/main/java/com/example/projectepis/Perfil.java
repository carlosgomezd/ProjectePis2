package com.example.projectepis;

public class Perfil {

    private String apellido, birthday, email, genero, nombre, password,token,datosP;

    public Perfil() {
    }

    public Perfil(String apellido, String birthday, String email, String genero, String nombre, String password, String token, String datosP) {
        this.apellido = apellido;
        this.birthday = birthday;
        this.email = email;
        this.genero = genero;
        this.nombre = nombre;
        this.password = password;
        this.token = token;
        this.datosP=datosP;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
