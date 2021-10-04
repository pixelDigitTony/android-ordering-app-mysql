package com.example.ordersystem.Model;

public class Menu {

    private int menuId;
    private String menuName;
    private String menuDescrip;


    public Menu() {
    }

    public Menu(int Id, String Name, String Descrip) {
        this.menuId = Id;
        this.menuName = Name;
        this.menuDescrip = Descrip;
    }


    public String getMenuDescrip() {
        return menuDescrip;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    @Override
    public String toString() {
        return menuName;
    }
}
