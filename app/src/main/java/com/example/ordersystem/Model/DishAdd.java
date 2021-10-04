package com.example.ordersystem.Model;

public class DishAdd {
    private int dishAddDishId;
    private String dishAddDishName;
    private String dishAddDishDescrip;


    public DishAdd() {
    }

    public DishAdd(int Id, String dishAddName, String dishAddDescrip) {
        this.dishAddDishId = Id;
        this.dishAddDishName = dishAddName;
        this.dishAddDishDescrip = dishAddDescrip;
    }


    public String getDishDishDescrip() {
        return dishAddDishDescrip;
    }

    public int getDishDishId() {
        return dishAddDishId;
    }

    public String getDishDishName() {
        return dishAddDishName;
    }

    @Override
    public String toString() {
        return dishAddDishName;
    }
}
