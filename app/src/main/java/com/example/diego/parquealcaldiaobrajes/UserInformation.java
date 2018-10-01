package com.example.diego.parquealcaldiaobrajes;

/**
 * Created by Jeffrey on 21/5/2017.
 */

public class UserInformation {

    public String Propietario;
    public String Placa;
    public String Clase;
    public String Marca;
    public String carnet;
    public String Color;
    public String crpva;
    public String correo;
    public String foto;



    public UserInformation(){

    }

    public UserInformation(String propietario, String placa, String clase, String marca, String carnet, String color, String crpva, String correo, String foto) {
        Propietario = propietario;
        Placa = placa;
        Clase = clase;
        Marca = marca;
        this.carnet = carnet;
        Color = color;
        this.crpva = crpva;
        this.correo = correo;
        this.foto = foto;
    }
}