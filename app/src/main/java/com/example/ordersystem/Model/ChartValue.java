package com.example.ordersystem.Model;

public class ChartValue {
    private int ChartId;
    private int ChartQuantity;

    public ChartValue() {
    }

    public ChartValue(int Id, int Quantity) {
        this.ChartId = Id;
        this.ChartQuantity = Quantity;

    }



    public int getChartId() {
        return ChartId;
    }

    public int getChartQuantity() {
        return ChartQuantity;
    }
}
