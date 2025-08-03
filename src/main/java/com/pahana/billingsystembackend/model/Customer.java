package com.pahana.billingsystembackend.model;



public class Customer {
    
    protected String accountNumber;
    protected String name;
    protected String address;
    protected String telephone;
    protected int consumedUnits;
    
    public Customer(){}

    public Customer(String accountNumber, String name, String address, String telephone, int consumedUnits) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.consumedUnits = consumedUnits;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getConsumedUnits() {
        return consumedUnits;
    }

    public void setConsumedUnits(int consumedUnits) {
        this.consumedUnits = consumedUnits;
    }
    
    
    
    
    
}
