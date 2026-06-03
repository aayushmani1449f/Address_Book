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

class JsonFileDataSourceTest {
    private static final String TEST_FILE = "test_address_book.json";
    private JsonFileDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new JsonFileDataSource(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(TEST_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void testWriteAndReadJsonData() {
        Contact c1 = new Contact(1, "Charlie", "Brown", "789 Json Way", "GsonCity", "GC", "33333", "555-0003", "charlie@email.com", LocalDate.now());
        Contact c2 = new Contact(2, "Diana", "Prince", "101 Amazon Blvd", "Themyscira", "TH", "44444", "555-0004", "diana@email.com", LocalDate.now());

        List<Contact> contacts = Arrays.asList(c1, c2);

        
        dataSource.writeData(contacts);

        
        List<Contact> readContacts = dataSource.readData();

        assertEquals(2, readContacts.size());
        assertEquals("Charlie", readContacts.get(0).getFirstName());
        assertEquals("Diana", readContacts.get(1).getFirstName());
        assertEquals("GC", readContacts.get(0).getState());
        assertEquals(LocalDate.now(), readContacts.get(0).getDateAdded());
    }
}
