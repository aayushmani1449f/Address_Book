package com.addressbook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvFileDataSourceTest {
    private static final String TEST_FILE = "test_address_book.csv";
    private CsvFileDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new CsvFileDataSource(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(TEST_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void testWriteAndReadCsvData() {
        Contact c1 = new Contact(1, "Alice", "Wonder", "123 Magic Rd", "Wonderland", "WL", "11111", "555-0001", "alice@email.com", LocalDate.now());
        Contact c2 = new Contact(2, "Bob", "Builder", "456 Build St", "ConstructionCity", "CC", "22222", "555-0002", "bob@email.com", LocalDate.now());
        
        List<Contact> contacts = Arrays.asList(c1, c2);
        
        // Write
        dataSource.writeData(contacts);
        
        // Read
        List<Contact> readContacts = dataSource.readData();
        
        assertEquals(2, readContacts.size());
        assertEquals("Alice", readContacts.get(0).getFirstName());
        assertEquals("Bob", readContacts.get(1).getFirstName());
        assertEquals("WL", readContacts.get(0).getState());
    }
}
