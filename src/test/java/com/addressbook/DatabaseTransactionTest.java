package com.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTransactionTest {
    private DatabaseDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DatabaseDataSource();
    }

    @Test
    void testAddContactWithTransaction() {
        Contact newContact = new Contact(
                0, "Bruce", "Wayne", "1007 Mountain Drive", "Gotham", "NJ", "07001", "555-0005", "bruce@batman.com", LocalDate.now()
        );
        
        // type_id 1 is 'Family', 2 is 'Friends', 3 is 'Profession' (based on DbTest seed)
        List<Integer> typeIds = Arrays.asList(2, 3); // Wayne is Friends and Profession
        
        boolean success = dataSource.addContactWithTransaction(newContact, typeIds);
        
        assertTrue(success, "Contact should be successfully added along with its mappings using DB Transaction");
        
        // Verify retrieval
        Contact retrieved = dataSource.getContactByName("Bruce");
        assertTrue(retrieved != null && retrieved.getCity().equals("Gotham"), "Retrieved contact must match inserted data");
    }
}
