package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.db.DBConnection;
import com.pahana.billingsystembackend.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private String generateNextAccountNumber() {
        String prefix = "EDU-CUS-";
        String query = "SELECT account_number FROM customer ORDER BY account_number DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String lastAccountNumber = rs.getString("account_number");
                int numberPart = Integer.parseInt(lastAccountNumber.substring(prefix.length()));
                int nextNumber = numberPart + 1;
                return String.format("%s%02d", prefix, nextNumber);
            } else {
                return "EDU-CUS-01";
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to add Customer
    public boolean addCustomer(Customer customer) {
        String newAccountNumber = generateNextAccountNumber();
        if (newAccountNumber == null) {
            return false;
        }

        String query = "INSERT INTO customer (account_number, name, address, telephone, units_consumed) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newAccountNumber);
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setInt(5, customer.getConsumedUnits());

            int rows = stmt.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Mehtod to get All Customers from the db
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();

        String query = "SELECT * FROM customer";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setConsumedUnits(rs.getInt("units_consumed"));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public Customer getCustomerByAccountNum(String accountNum) {
        String query = "SELECT * FROM customer WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setConsumedUnits(rs.getInt("units_consumed"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCustomer(Customer customer, String accountNum) {
        String query = "UPDATE customer SET  name = ?, address = ?, telephone = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getTelephone());
            stmt.setString(4, accountNum);

            int rows = stmt.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomerByAccountNum(String accountNum) {
        String query = "DELETE FROM customer WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, accountNum);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
