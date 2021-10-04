package com.example.ordersystem.Model;

public class Report {
    private int dishReportId;
    private String dishReportName;
    private int dishReportValue;


    public Report() {
    }

    public Report(int dishReportId, String dishReportName, int dishReportValue) {
        this.dishReportId = dishReportId;
        this.dishReportName = dishReportName;
        this.dishReportValue = dishReportValue;
    }


    public int getdishReportValue() {
        return dishReportValue;
    }

    public int getDishDishId() {
        return dishReportId;
    }

    public String getDishDishName() {
        return dishReportName;
    }

}
