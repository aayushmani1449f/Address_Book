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
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextFileDataSourceTest {
    private static final String TEST_FILE = "test_address_book.txt";
    private TextFileDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new TextFileDataSource(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(TEST_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void testWriteAndReadData() {
        Contact c1 = new Contact(1, "John", "Doe", "123 St", "City", "State", "12345", "555-1234", "john@email.com", LocalDate.now());
        Contact c2 = new Contact(2, "Jane", "Smith", "456 Ave", "Town", "State", "67890", "555-5678", "jane@email.com", LocalDate.now());
        
        List<Contact> contacts = Arrays.asList(c1, c2);
        
        
        dataSource.writeData(contacts);
        
        
        List<Contact> readContacts = dataSource.readData();
        
        assertEquals(2, readContacts.size());
        assertEquals("John", readContacts.get(0).getFirstName());
        assertEquals("Jane", readContacts.get(1).getFirstName());
    }
}
