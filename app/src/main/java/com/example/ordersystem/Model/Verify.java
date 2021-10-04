package com.example.ordersystem.Model;

public class Verify {
    private int dishId;
    private String dishName;


    public Verify() {
    }

    public Verify(int Id, String dishName) {
        this.dishId = Id;
        this.dishName = dishName;

    }



    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

}

