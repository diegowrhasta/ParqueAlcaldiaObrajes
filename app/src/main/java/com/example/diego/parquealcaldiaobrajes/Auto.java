package com.example.diego.parquealcaldiaobrajes;

/**
 * Created by Jeffrey on 21/5/2017.
 */

public class Auto {


    public String Placa;
    public String Clase;
    public String Marca;
    public String Color;
    public String crpva;
    public String foto;



    public Auto(){

    }

    public Auto(String placa, String clase, String marca, String color, String crpva, String foto) {
        Placa = placa;
        Clase = clase;
        Marca = marca;
        Color = color;
        this.crpva = crpva;
        this.foto = foto;
    }
}