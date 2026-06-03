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
        
        String name = "John";
        String newCity = "NewCityName";
        
        
        int rowsAffected = dataSource.updateContactCity(name, newCity);
        
        
        assertTrue(rowsAffected > 0, "Should update at least 1 record");
        
        
        Contact dbContact = dataSource.getContactByName(name);
        assertNotNull(dbContact);
        
        
        Contact memoryContact = new Contact(
                dbContact.getId(),
                "John", "Doe", "123 Elm St", "NewCityName", "IL", "62701", "555-1234", "john@example.com", dbContact.getDateAdded()
        );
        
        
        assertEquals(memoryContact, dbContact, "Memory object should be in sync with DB state");
        assertEquals("NewCityName", dbContact.getCity());
    }
}
