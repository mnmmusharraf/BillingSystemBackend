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
    private double cashGiven;
    private double changeDue;

    public Billing() {
    }

    public Billing(int id, String accountNumber, String staffUser, Date billDate, double totalAmount, List<BillItem> items, double cashGiven, double changeDue) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.staffUser = staffUser;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.items = items;
        this.cashGiven = cashGiven;
        this.changeDue = changeDue;
    }

    public double getCashGiven() {
        return cashGiven;
    }

    public void setCashGiven(double cashGiven) {
        this.cashGiven = cashGiven;
    }

    public double getChangeDue() {
        return changeDue;
    }

    public void setChangeDue(double changeDue) {
        this.changeDue = changeDue;
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