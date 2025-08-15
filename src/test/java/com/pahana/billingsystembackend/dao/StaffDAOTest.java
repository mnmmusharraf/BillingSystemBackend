package com.pahana.billingsystembackend.dao;

import com.pahana.billingsystembackend.model.Staff;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class StaffDAOTest {

    private StaffDAO dao;

    @BeforeAll
    public static void setUpClass() {
        // Optionally: Insert test staff user to DB (if you control the test DB)
        // For example, via SQL "INSERT INTO staff (username, password) VALUES ('musha', '123')"
    }

    @AfterAll
    public static void tearDownClass() {
        // Optionally: Clean up test staff user from DB
        // For example, via SQL "DELETE FROM staff WHERE username='musha'"
    }

    @BeforeEach
    public void setUp() {
        dao = new StaffDAO();
    }

    @AfterEach
    public void tearDown() {
        // No cleanup needed for these tests
    }

    @Test
    public void testValidateLogin_withValidCredentials() {
        // Given that "musha" with password "123" exists in DB
        boolean result = dao.validateLogin("musha", "123");
        assertTrue(result, "Login should succeed with valid credentials");
    }

    @Test
    public void testValidateLogin_withInvalidCredentials() {
        // Non-existing user
        boolean result = dao.validateLogin("wronguser", "wrongpass");
        assertFalse(result, "Login should fail with invalid credentials");
    }

    @Test
    public void testValidateLogin_withWrongPassword() {
        // Existing username, wrong password
        boolean result = dao.validateLogin("musha", "wrongpass");
        assertFalse(result, "Login should fail with wrong password");
    }

    @Test
    public void testValidateLogin_withNullOrEmpty() {
        assertFalse(dao.validateLogin("", ""), "Login should fail with empty credentials");
        assertFalse(dao.validateLogin(null, null), "Login should fail with null credentials");
    }

    @Test
    public void testGetStaffByUsername_existingUser() {
        Staff staff = dao.getStaffByUsername("musha");
        assertNotNull(staff, "Should return Staff object for existing user");
        assertEquals("musha", staff.getUsername());
        assertEquals("123", staff.getPassword());
    }

    @Test
    public void testGetStaffByUsername_nonExistingUser() {
        Staff staff = dao.getStaffByUsername("nouser");
        assertNull(staff, "Should return null for non-existing user");
    }

    @Test
    public void testGetStaffByUsername_nullOrEmpty() {
        assertNull(dao.getStaffByUsername(""), "Should return null for empty username");
        assertNull(dao.getStaffByUsername(null), "Should return null for null username");
    }
}