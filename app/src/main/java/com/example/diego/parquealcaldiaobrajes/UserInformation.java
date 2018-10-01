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

    public UserInformation(String Propietario, String Placa, String Clase, String Marca, String carnet, String Color, String crpva, String correo, String foto) {
        this.Propietario = Propietario;
        this.Placa = Placa;
        this.Clase = Clase;
        this.Marca = Marca;
        this.carnet = carnet;
        this.Color = Color;

        this.crpva = crpva;
        this.correo = correo;
        this.foto = foto;


    }

    public String getPropietario() {
        return Propietario;
    }

    public String getPlaca() {
        return Placa;
    }

    public String getClase() {
        return Clase;
    }

    public String getMarca() {
        return Marca;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getColor() {
        return Color;
    }
    public String getCrpva() {
        return crpva;
    }
    public String getCorreo() {
        return correo;
    }
    public String getFoto() {
        return foto;
    }
}