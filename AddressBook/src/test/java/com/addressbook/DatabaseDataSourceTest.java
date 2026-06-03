package com.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseDataSourceTest {
    private DatabaseDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DatabaseDataSource();
    }

    @Test
    void testReadDataFromDB() {
        List<Contact> contacts = dataSource.readData();
        
        assertNotNull(contacts);
        // The DB was seeded with at least 1 contact (John Doe) in the setup
        assertFalse(contacts.isEmpty(), "Contact list from DB should not be empty");
        
        // Let's verify if the seeded contact exists
        boolean hasJohn = contacts.stream().anyMatch(c -> c.getFirstName().equals("John") && c.getLastName().equals("Doe"));
        assertTrue(hasJohn, "Seeded contact John Doe should be found in the database");
    }
}
