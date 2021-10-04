package com.example.ordersystem.Model;

public class Dish {
    private int dishId;
    private String dishName;
    private double dishPrice;
    private int dishImage;
    private String dishDescrip;
    private String dishAvail;


    public Dish() {
    }

    public Dish(int Id, String dishName, double dishPrice, int dishImage, String dishDescrip, String dishAvail) {
        this.dishId = Id;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishImage = dishImage;
        this.dishDescrip = dishDescrip;
        this.dishAvail = dishAvail;
    }

    public String getDishAvail() {
        return dishAvail;
    }

    public String getDishDescrip() {
        return dishDescrip;
    }

    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public int getDishImage() {
        return dishImage;
    }
}
