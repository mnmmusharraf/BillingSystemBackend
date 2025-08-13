package com.pahana.billingsystembackend.model;

import java.util.Date;
import java.util.List;

public class Billing {
    private int id;
    private String accountNumber;
    private String staffUser;
    private Date billDate;
    private double totalAmount;
    private List<BillItem> items;

    public Billing() {
    }

    public Billing(int id, String accountNumber, String staffUser, Date billDate, double totalAmount, List<BillItem> items) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.staffUser = staffUser;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(String staffUser) {
        this.staffUser = staffUser;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }
}