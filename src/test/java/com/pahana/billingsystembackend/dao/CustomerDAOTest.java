package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.model.Customer;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerDAOTest {

    private CustomerDAO dao;
    private String createdAccountNum = null;

    @BeforeAll
    public void setUpClass() {
        dao = new CustomerDAO();
    }

    @AfterAll
    public void tearDownClass() {
        // Clean up if needed
    }

    @BeforeEach
    public void setUp() {
        createdAccountNum = null;
    }

    @AfterEach
    public void tearDown() {
        // Remove test customer from DB
        if (createdAccountNum != null) {
            dao.deleteCustomerByAccountNum(createdAccountNum);
        }
    }

    @Test
    public void testAddCustomer_success() {
        Customer customer = new Customer();
        customer.setName("JUnit Test Customer");
        customer.setAddress("Test Address 123");
        customer.setTelephone("0771234567");
        customer.setConsumedUnits(0);

        boolean added = dao.addCustomer(customer);
        assertTrue(added, "Customer should be added successfully.");

        // Find customer by search
        List<Customer> found = dao.searchCustomers("JUnit Test Customer");
        assertFalse(found.isEmpty(), "Inserted customer should be found.");
        Customer retrieved = found.get(0);
        createdAccountNum = retrieved.getAccountNumber();

        assertEquals("JUnit Test Customer", retrieved.getName());
        assertEquals("Test Address 123", retrieved.getAddress());
        assertEquals("0771234567", retrieved.getTelephone());
    }

    @Test
    public void testAddCustomer_nullCustomer() {
        boolean added = dao.addCustomer(null);
        assertFalse(added, "Adding null customer should fail.");
    }

    @Test
    public void testGetAllCustomer() {
        List<Customer> customers = dao.getAllCustomer();
        assertNotNull(customers, "Customer list should not be null.");
        // Optionally assert list size if DB is not empty
    }

    @Test
    public void testSearchCustomers_found() {
        // Add customer to search for
        Customer customer = new Customer();
        customer.setName("SearchMe");
        customer.setAddress("Search Address");
        customer.setTelephone("0779999999");
        customer.setConsumedUnits(10);

        boolean added = dao.addCustomer(customer);
        assertTrue(added);
        List<Customer> found = dao.searchCustomers("SearchMe");
        assertFalse(found.isEmpty());
        Customer foundCustomer = found.get(0);
        createdAccountNum = foundCustomer.getAccountNumber();
        assertEquals("SearchMe", foundCustomer.getName());
    }

    @Test
    public void testSearchCustomers_notFound() {
        List<Customer> found = dao.searchCustomers("DefinitelyNotExist");
        assertTrue(found.isEmpty() || found.stream().noneMatch(c -> "DefinitelyNotExist".equals(c.getName())), "Non-existent customer should not be found.");
    }

    @Test
    public void testGetCustomerByAccountNum_existing() {
        // Add customer and fetch by account number
        Customer customer = new Customer();
        customer.setName("FetchMe");
        customer.setAddress("Fetch Address");
        customer.setTelephone("0711111111");
        customer.setConsumedUnits(5);

        boolean added = dao.addCustomer(customer);
        assertTrue(added);
        List<Customer> found = dao.searchCustomers("FetchMe");
        assertFalse(found.isEmpty());
        Customer inserted = found.get(0);
        createdAccountNum = inserted.getAccountNumber();

        Customer result = dao.getCustomerByAccountNum(createdAccountNum);
        assertNotNull(result, "Customer should be fetched by account number.");
        assertEquals("FetchMe", result.getName());
    }

    @Test
    public void testGetCustomerByAccountNum_nonExisting() {
        Customer result = dao.getCustomerByAccountNum("EDU-CUS-99");
        assertNull(result, "Non-existing account number should return null.");
    }

    @Test
    public void testUpdateCustomer_success() {
        // Add customer
        Customer customer = new Customer();
        customer.setName("UpdateMe");
        customer.setAddress("Update Address");
        customer.setTelephone("0788888888");
        customer.setConsumedUnits(3);

        boolean added = dao.addCustomer(customer);
        assertTrue(added);
        List<Customer> found = dao.searchCustomers("UpdateMe");
        assertFalse(found.isEmpty());
        Customer inserted = found.get(0);
        createdAccountNum = inserted.getAccountNumber();

        // Update
        Customer updated = new Customer();
        updated.setName("UpdatedName");
        updated.setAddress("Updated Address");
        updated.setTelephone("0788888899");
        updated.setConsumedUnits(3);

        boolean success = dao.updateCustomer(updated, createdAccountNum);
        assertTrue(success, "Customer should be updated.");

        Customer fetched = dao.getCustomerByAccountNum(createdAccountNum);
        assertEquals("UpdatedName", fetched.getName());
        assertEquals("Updated Address", fetched.getAddress());
        assertEquals("0788888899", fetched.getTelephone());
    }

    @Test
    public void testUpdateCustomer_invalid() {
        boolean updated = dao.updateCustomer(null, "");
        assertFalse(updated, "Updating with null customer should fail.");
    }

    @Test
    public void testDeleteCustomerByAccountNum_success() {
        // Add customer
        Customer customer = new Customer();
        customer.setName("DeleteMe");
        customer.setAddress("Delete Address");
        customer.setTelephone("0700000000");
        customer.setConsumedUnits(0);

        boolean added = dao.addCustomer(customer);
        assertTrue(added);
        List<Customer> found = dao.searchCustomers("DeleteMe");
        assertFalse(found.isEmpty());
        Customer inserted = found.get(0);
        String accountNum = inserted.getAccountNumber();

        boolean deleted = dao.deleteCustomerByAccountNum(accountNum);
        assertTrue(deleted, "Customer should be deleted.");

        Customer fetched = dao.getCustomerByAccountNum(accountNum);
        assertNull(fetched, "Deleted customer should not be found.");
    }

    @Test
    public void testDeleteCustomerByAccountNum_nonExisting() {
        boolean deleted = dao.deleteCustomerByAccountNum("EDU-CUS-99");
        assertFalse(deleted, "Deleting non-existent customer should fail.");
    }
}