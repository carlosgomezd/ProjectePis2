package com.example.projectepis;

public class Evento {
    String photo;
    String titol;
    public Evento(String ph, String ti){
        this.photo = ph;
        this.titol = ti;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitol() {
        return titol;
    }
}
