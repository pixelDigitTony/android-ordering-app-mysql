package com.example.ordersystem.Model;


import com.example.ordersystem.R;

public class Category  {
    private int menuId;
    private String Name;
    private int Image;


    public Category( int Id, String Name, int Image) {
        this.menuId = Id;
        this.Name = Name;
        this.Image = Image;

    }


    public int getId() {
        return menuId;
    }

    public String getName() {
        return Name;
    }

    public int getImage() {
        return Image;
    }


}