package com.mecanico.mecanicoapp.pedidos;


import android.widget.ImageView;



public class Pedido {

    private String Tipo,marca,state,date;
    private ImageView imgType;



    @Override
    public String toString() {
        return "Pedido{" +
                "Tipo='" + Tipo + '\'' +
                ", date='" + date + '\'' +
                ", marca='" + marca + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public Pedido() {

    }
    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ImageView getImgType() {
        return imgType;
    }

    public void setImgType(ImageView imgType) {
        this.imgType = imgType;
    }
}
