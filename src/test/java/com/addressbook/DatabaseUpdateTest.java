package com.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseUpdateTest {
    private DatabaseDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DatabaseDataSource();
    }

    @Test
    void testUpdateContactCitySync() {
        // Arrange
        String name = "John";
        String newCity = "NewCityName";
        
        // Act
        int rowsAffected = dataSource.updateContactCity(name, newCity);
        
        // Assert
        assertTrue(rowsAffected > 0, "Should update at least 1 record");
        
        // Fetch from DB
        Contact dbContact = dataSource.getContactByName(name);
        assertNotNull(dbContact);
        
        // Create in-memory object imitating sync
        Contact memoryContact = new Contact(
                dbContact.getId(),
                "John", "Doe", "123 Elm St", "NewCityName", "IL", "62701", "555-1234", "john@example.com", dbContact.getDateAdded()
        );
        
        // Sync test using overridden equals
        assertEquals(memoryContact, dbContact, "Memory object should be in sync with DB state");
        assertEquals("NewCityName", dbContact.getCity());
    }
}
