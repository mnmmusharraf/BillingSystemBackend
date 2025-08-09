package com.pahana.billingsystembackend.service;

import com.pahana.billingsystembackend.dao.CustomerDAO;
import com.pahana.billingsystembackend.model.Customer;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    // get All Customers
    public List<Customer> getAllCustomer() {
        return customerDAO.getAllCustomer();
    }
    
    public List<Customer> searchCustomers(String searchTerms){
        return customerDAO.searchCustomers(searchTerms);
    }

    // Add new customer
    public boolean addCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    // get a Customer by Account Number
    public Customer getCustomerByAccountNum(String accountNum) {
        return customerDAO.getCustomerByAccountNum(accountNum);
    }

    // Update a Customer
    public boolean updateCustomer(Customer customer, String accountNum) {
        return customerDAO.updateCustomer(customer, accountNum);
    }

    // Delete a Customer
    public boolean deleteCustomer(String accountNum) {
        return customerDAO.deleteCustomerByAccountNum(accountNum);
    }

}
