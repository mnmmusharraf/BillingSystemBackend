package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.model.Item;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemDAOTest {

    private ItemDAO dao;
    private int testItemId = -1;

    @BeforeAll
    public void setUpClass() {
        dao = new ItemDAO();
    }

    @AfterAll
    public void tearDownClass() {
        // Clean up any lingering test data if needed
    }

    @BeforeEach
    public void setUp() {
        testItemId = -1;
    }

    @AfterEach
    public void tearDown() {
        // Remove test item to keep DB clean
        if (testItemId > 0) {
            dao.deleteItem(testItemId);
        }
    }

    @Test
    public void testAddItem_success() {
        Item item = new Item();
        item.setItemName("JUnit Test Item");
        item.setItemPrice(99.99);
        item.setStockQuantity(10);

        boolean result = dao.addItem(item);
        assertTrue(result, "Item should be added successfully.");

        // Get item back by name and record its ID for cleanup
        List<Item> items = dao.searchItems("JUnit Test Item");
        assertFalse(items.isEmpty(), "Inserted item should be found.");
        Item fetched = items.get(0);
        testItemId = fetched.getItemId();
        assertEquals("JUnit Test Item", fetched.getItemName());
        assertEquals(99.99, fetched.getItemPrice());
        assertEquals(10, fetched.getStockQuantity());
    }

    @Test
    public void testAddItem_nullItem() {
        boolean result = dao.addItem(null);
        assertFalse(result, "Adding null item should fail.");
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = dao.getAllItems();
        assertNotNull(items, "Items list should not be null.");
        // Optionally, you can check if list.size() > 0 if DB is not empty
    }

    @Test
    public void testGetItemById_existing() {
        // Add item first
        Item item = new Item();
        item.setItemName("FindMe");
        item.setItemPrice(10.0);
        item.setStockQuantity(5);
        boolean added = dao.addItem(item);
        assertTrue(added);
        List<Item> items = dao.searchItems("FindMe");
        assertFalse(items.isEmpty());
        Item inserted = items.get(0);
        testItemId = inserted.getItemId();

        Item result = dao.getItemById(testItemId);
        assertNotNull(result, "Should retrieve item by ID.");
        assertEquals("FindMe", result.getItemName());
    }

    @Test
    public void testGetItemById_nonExisting() {
        Item result = dao.getItemById(-999); // unlikely to exist
        assertNull(result, "Non-existing item should return null.");
    }

    @Test
    public void testUpdateItem_success() {
        // Add item first
        Item item = new Item();
        item.setItemName("UpdateMe");
        item.setItemPrice(20.0);
        item.setStockQuantity(2);
        boolean added = dao.addItem(item);
        assertTrue(added);
        List<Item> items = dao.searchItems("UpdateMe");
        assertFalse(items.isEmpty());
        Item inserted = items.get(0);
        testItemId = inserted.getItemId();

        // Update
        item.setItemName("UpdatedName");
        item.setItemPrice(25.0);
        item.setStockQuantity(3);
        boolean updated = dao.updateItem(item, testItemId);
        assertTrue(updated, "Item should be updated.");

        Item updatedItem = dao.getItemById(testItemId);
        assertEquals("UpdatedName", updatedItem.getItemName());
        assertEquals(25.0, updatedItem.getItemPrice());
        assertEquals(3, updatedItem.getStockQuantity());
    }

    @Test
    public void testUpdateItem_invalidItem() {
        boolean result = dao.updateItem(null, -1);
        assertFalse(result, "Updating null item should fail.");
    }

    @Test
    public void testDeleteItem_existing() {
        // Add and then delete
        Item item = new Item();
        item.setItemName("DeleteMe");
        item.setItemPrice(5.0);
        item.setStockQuantity(1);
        boolean added = dao.addItem(item);
        assertTrue(added);
        List<Item> items = dao.searchItems("DeleteMe");
        assertFalse(items.isEmpty());
        Item inserted = items.get(0);
        int itemId = inserted.getItemId();

        boolean deleted = dao.deleteItem(itemId);
        assertTrue(deleted, "Item should be deleted.");
        Item fetched = dao.getItemById(itemId);
        assertNull(fetched, "Deleted item should not be found.");
    }

    @Test
    public void testDeleteItem_nonExisting() {
        boolean deleted = dao.deleteItem(-999);
        assertFalse(deleted, "Deleting non-existing item should fail.");
    }

    @Test
    public void testIsItemNameExists_true() {
        // Add item
        Item item = new Item();
        item.setItemName("ExistMe");
        item.setItemPrice(8.0);
        item.setStockQuantity(4);
        boolean added = dao.addItem(item);
        assertTrue(added);
        List<Item> items = dao.searchItems("ExistMe");
        assertFalse(items.isEmpty());
        Item inserted = items.get(0);
        testItemId = inserted.getItemId();

        boolean exists = dao.isItemNameExists("ExistMe");
        assertTrue(exists, "Item name should exist.");
    }

    @Test
    public void testIsItemNameExists_false() {
        boolean exists = dao.isItemNameExists("DefinitelyNotExist");
        assertFalse(exists, "Non-existing item name should not exist.");
    }

    @Test
    public void testSearchItems_found() {
        // Add item
        Item item = new Item();
        item.setItemName("SearchMe");
        item.setItemPrice(12.5);
        item.setStockQuantity(7);
        boolean added = dao.addItem(item);
        assertTrue(added);
        List<Item> items = dao.searchItems("SearchMe");
        assertFalse(items.isEmpty(), "Search should find the item.");
        Item inserted = items.get(0);
        testItemId = inserted.getItemId();
    }

    @Test
    public void testSearchItems_notFound() {
        List<Item> items = dao.searchItems("NoSuchItem");
        assertTrue(items.isEmpty(), "Search for non-existent item should return empty list.");
    }
}