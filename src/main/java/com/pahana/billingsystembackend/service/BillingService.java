package com.pahana.billingsystembackend.service;

import com.pahana.billingsystembackend.dao.billingDAO;
import com.pahana.billingsystembackend.model.Billing;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    private final billingDAO billingDao = new billingDAO();

    // Create a new bill (with items)
    public int createBill(Billing bill) throws SQLException {
        return billingDao.createBill(bill);
    }

    // Get a bill by its ID
    public Billing getBillById(int billId) throws SQLException {
        return billingDao.getBillById(billId);
    }

    // Update a bill (rare for billing, but possible)
    public boolean updateBill(Billing bill) throws SQLException {
        return billingDao.updateBill(bill);
    }

    // Delete a bill (and its items)
    public boolean deleteBill(int billId) throws SQLException {
        return billingDao.deleteBill(billId);
    }

    // Get all bills (listing)
    public List<Billing> getAllBills() throws SQLException {
        return billingDao.getAllBills();
    }
}