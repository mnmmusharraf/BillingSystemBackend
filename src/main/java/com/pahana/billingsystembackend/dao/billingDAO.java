package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.db.DBConnection;
import com.pahana.billingsystembackend.model.Billing;
import com.pahana.billingsystembackend.model.BillItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class billingDAO {

    // CREATE - Insert Bill and Bill Items (transactional)
    public int createBill(Billing bill) throws SQLException {
        Connection conn = null;
        PreparedStatement billStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet rs = null;
        int billId = -1;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String billSql = "INSERT INTO bill (account_number, staff_user, bill_date, total_amount) VALUES (?, ?, ?, ?)";
            billStmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            billStmt.setString(1, bill.getAccountNumber());
            billStmt.setString(2, bill.getStaffUser());
            billStmt.setTimestamp(3, new Timestamp(bill.getBillDate().getTime()));
            billStmt.setDouble(4, bill.getTotalAmount());
            billStmt.executeUpdate();

            rs = billStmt.getGeneratedKeys();
            if (rs.next()) {
                billId = rs.getInt(1);
            }

            String itemSql = "INSERT INTO bill_item (bill_id, item_id, item_name, unit_price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(itemSql);

            for (BillItem item : bill.getItems()) {
                itemStmt.setInt(1, billId);
                itemStmt.setInt(2, item.getItemId());
                itemStmt.setString(3, item.getItemName());
                itemStmt.setDouble(4, item.getUnitPrice());
                itemStmt.setInt(5, item.getQuantity());
                itemStmt.setDouble(6, item.getSubtotal());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

            conn.commit();
            return billId;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (billStmt != null) billStmt.close();
            if (itemStmt != null) itemStmt.close();
            if (conn != null) conn.close();
        }
    }

    // READ - Get Bill by ID (with items)
    public Billing getBillById(int billId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Billing bill = null;
        try {
            // Get bill
            String sql = "SELECT * FROM bill WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bill = new Billing();
                bill.setId(rs.getInt("id"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setStaffUser(rs.getString("staff_user"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));

                // Get bill items
                List<BillItem> items = new ArrayList<>();
                String itemSql = "SELECT * FROM bill_item WHERE bill_id = ?";
                PreparedStatement itemStmt = conn.prepareStatement(itemSql);
                itemStmt.setInt(1, billId);
                ResultSet itemRs = itemStmt.executeQuery();
                while (itemRs.next()) {
                    BillItem item = new BillItem();
                    item.setId(itemRs.getInt("id"));
                    item.setBillId(itemRs.getInt("bill_id"));
                    item.setItemId(itemRs.getInt("item_id"));
                    item.setItemName(itemRs.getString("item_name"));
                    item.setUnitPrice(itemRs.getDouble("unit_price"));
                    item.setQuantity(itemRs.getInt("quantity"));
                    item.setSubtotal(itemRs.getDouble("subtotal"));
                    items.add(item);
                }
                bill.setItems(items);
            }
            return bill;
        } finally {
            conn.close();
        }
    }

    // UPDATE - Update Bill (not common for bills, but possible)
    public boolean updateBill(Billing bill) throws SQLException {
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "UPDATE bill SET account_number = ?, staff_user = ?, bill_date = ?, total_amount = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, bill.getAccountNumber());
            stmt.setString(2, bill.getStaffUser());
            stmt.setTimestamp(3, new Timestamp(bill.getBillDate().getTime()));
            stmt.setDouble(4, bill.getTotalAmount());
            stmt.setInt(5, bill.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } finally {
            conn.close();
        }
    }

    // DELETE - Delete Bill and Bill Items
    public boolean deleteBill(int billId) throws SQLException {
        Connection conn = null;
        PreparedStatement itemStmt = null;
        PreparedStatement billStmt = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Delete bill items
            String itemSql = "DELETE FROM bill_item WHERE bill_id = ?";
            itemStmt = conn.prepareStatement(itemSql);
            itemStmt.setInt(1, billId);
            itemStmt.executeUpdate();

            // Delete bill
            String billSql = "DELETE FROM bill WHERE id = ?";
            billStmt = conn.prepareStatement(billSql);
            billStmt.setInt(1, billId);
            int rows = billStmt.executeUpdate();

            conn.commit();
            return rows > 0;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (itemStmt != null) itemStmt.close();
            if (billStmt != null) billStmt.close();
            if (conn != null) conn.close();
        }
    }

    // Optional: List all bills
    public List<Billing> getAllBills() throws SQLException {
        Connection conn = DBConnection.getConnection();
        List<Billing> bills = new ArrayList<>();
        try {
            String sql = "SELECT * FROM bill";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Billing bill = new Billing();
                bill.setId(rs.getInt("id"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setStaffUser(rs.getString("staff_user"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bills.add(bill);
            }
            return bills;
        } finally {
            conn.close();
        }
    }
}