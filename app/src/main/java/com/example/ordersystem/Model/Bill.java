package com.example.ordersystem.Model;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Bill {
    private String billId;
    private String billTotalPrice;
    private String billDate;
    private String billQuantity;
    private String billName;
    private Double billPrice;

    public Bill(String billId, String billTotalPrice, String billDate, String billQuantity, String billName, Double billPrice) {
        this.billId = billId;
        this.billTotalPrice = billTotalPrice;
        this.billDate = billDate;
        this.billQuantity = billQuantity;
        this.billName = billName;
        this.billPrice = billPrice;
    }

    public String getBillId() {
        return billId;
    }

    public String getBillTotalPrice() {
        return billTotalPrice;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getBillQuantity() {
        return billQuantity;
    }

    public String getBillName() {
        return billName;
    }

    public Double getBillPrice() {
        return billPrice;
    }
}