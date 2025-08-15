package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.model.BillItem;
import com.pahana.billingsystembackend.model.Billing;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class billingDAOTest {

    private billingDAO dao;

    private int createdBillId;

    @BeforeAll
    public void setUpClass() {
        dao = new billingDAO();
    }

    @AfterAll
    public void tearDownClass() {
        // Optionally clean up test DB entries here
    }

    @BeforeEach
    public void setUp() {
        createdBillId = -1; // Reset before each test
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Clean up test bill (if created)
        if (createdBillId > 0) {
            dao.deleteBill(createdBillId);
        }
    }

    @Test
    public void testCreateBill() throws Exception {
        Billing bill = new Billing();
        bill.setAccountNumber("TEST123");
        bill.setStaffUser("testuser");
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotalAmount(125.50);
        bill.setCashGiven(130.00);
        bill.setChangeDue(4.50);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItemId(1);
        item.setItemName("Test Product");
        item.setUnitPrice(62.75);
        item.setQuantity(2);
        item.setSubtotal(125.50);
        items.add(item);
        bill.setItems(items);

        int billId = dao.createBill(bill);
        createdBillId = billId; // for cleanup

        assertTrue(billId > 0, "Bill should be created and have a positive ID.");
    }

    @Test
    public void testGetBillById() throws Exception {
        // First create a bill
        Billing bill = new Billing();
        bill.setAccountNumber("TEST124");
        bill.setStaffUser("testuser2");
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotalAmount(50.00);
        bill.setCashGiven(60.00);
        bill.setChangeDue(10.00);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItemId(2);
        item.setItemName("Test Item 2");
        item.setUnitPrice(50.00);
        item.setQuantity(1);
        item.setSubtotal(50.00);
        items.add(item);
        bill.setItems(items);

        int billId = dao.createBill(bill);
        createdBillId = billId;

        Billing fetched = dao.getBillById(billId);
        assertNotNull(fetched);
        assertEquals("TEST124", fetched.getAccountNumber());
        assertEquals(50.00, fetched.getTotalAmount());
        assertEquals(1, fetched.getItems().size());
        assertEquals("Test Item 2", fetched.getItems().get(0).getItemName());
    }

    @Test
    public void testUpdateBill() throws Exception {
        // Create a bill
        Billing bill = new Billing();
        bill.setAccountNumber("TEST125");
        bill.setStaffUser("testuser3");
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotalAmount(200.00);
        bill.setCashGiven(250.00);
        bill.setChangeDue(50.00);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItemId(3);
        item.setItemName("Test Item 3");
        item.setUnitPrice(200.00);
        item.setQuantity(1);
        item.setSubtotal(200.00);
        items.add(item);
        bill.setItems(items);

        int billId = dao.createBill(bill);
        createdBillId = billId;

        // Update fields
        bill.setId(billId);
        bill.setTotalAmount(180.00);
        bill.setCashGiven(200.00);
        bill.setChangeDue(20.00);

        boolean updated = dao.updateBill(bill);
        assertTrue(updated);

        Billing updatedBill = dao.getBillById(billId);
        assertEquals(180.00, updatedBill.getTotalAmount());
        assertEquals(200.00, updatedBill.getCashGiven());
        assertEquals(20.00, updatedBill.getChangeDue());
    }

    @Test
    public void testDeleteBill() throws Exception {
        // Create a bill
        Billing bill = new Billing();
        bill.setAccountNumber("TEST126");
        bill.setStaffUser("testuser4");
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotalAmount(300.00);
        bill.setCashGiven(350.00);
        bill.setChangeDue(50.00);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItemId(4);
        item.setItemName("Test Item 4");
        item.setUnitPrice(300.00);
        item.setQuantity(1);
        item.setSubtotal(300.00);
        items.add(item);
        bill.setItems(items);

        int billId = dao.createBill(bill);

        boolean deleted = dao.deleteBill(billId);
        assertTrue(deleted);

        Billing deletedBill = dao.getBillById(billId);
        assertNull(deletedBill);
    }

    @Test
    public void testGetAllBills() throws Exception {
        List<Billing> bills = dao.getAllBills();
        assertNotNull(bills);
        // Optionally, check at least one bill exists
        // assertTrue(bills.size() > 0);
    }

    @Test
    public void testUpdateUnitsConsumed() throws Exception {
        // Setup: create a bill
        Billing bill = new Billing();
        bill.setAccountNumber("TEST127");
        bill.setStaffUser("testuser5");
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotalAmount(80.00);
        bill.setCashGiven(100.00);
        bill.setChangeDue(20.00);

        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setItemId(5);
        item.setItemName("Test Item 5");
        item.setUnitPrice(80.00);
        item.setQuantity(1);
        item.setSubtotal(80.00);
        items.add(item);
        bill.setItems(items);

        int billId = dao.createBill(bill);
        createdBillId = billId;

        // Act: update units consumed for this account
        dao.updateUnitsConsumed("TEST127");

        // Optionally: assert by querying the customer table or checking no exception
        assertTrue(true); // Just check no exception for now.
    }
}