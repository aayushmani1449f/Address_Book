package com.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseDateTest {
    private DatabaseDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DatabaseDataSource();
    }

    @Test
    void testGetContactsAddedBetween() {
        
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        List<Contact> contacts = dataSource.getContactsAddedBetween(startDate, endDate);
        
        assertFalse(contacts.isEmpty(), "Should retrieve contacts within the date range");
        
        boolean found = contacts.stream().anyMatch(c -> c.getFirstName().equals("John") && c.getDateAdded().equals(LocalDate.of(2023, 1, 15)));
        assertTrue(found, "Should contain the test contact John added on 2023-01-15");
    }
}
