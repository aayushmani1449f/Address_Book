package com.addressbook;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class AddressBookTest {

    private AddressBook addressBook;
    private Contact contact1;
    private Contact contact2;

    @Before
    public void setUp() {
        addressBook = new AddressBook();
        contact1 = new Contact("John", "Doe", "123 Main St", "Anytown", "NY", "12345", "555-1234", "john@test.com");
        contact2 = new Contact("Jane", "Smith", "456 Oak St", "Othertown", "CA", "98765", "555-5678", "jane@test.com");
        
        // Use reflection or package-private access if addContact doesn't exist for direct objects
        // Assuming we can add to contacts list directly for testing, or if there's an addContact(Contact) method.
        // Wait, AddressBook.java only has addContact(Scanner). We must update AddressBook.java to have addContact(Contact) for programmatic adding.
        addressBook.getContacts().add(contact1);
        addressBook.getContacts().add(contact2);
    }

    @Test
    public void testAddContact() {
        assertEquals(2, addressBook.getContacts().size());
        Contact newContact = new Contact("Alice", "Brown", "789 Pine St", "Sometown", "TX", "75001", "555-9012", "alice@test.com");
        addressBook.getContacts().add(newContact);
        assertEquals(3, addressBook.getContacts().size());
    }

    @Test
    public void testDuplicateCheck() {
        Contact duplicate = new Contact("John", "Doe", "123 Diff St", "Anytown", "NY", "12345", "555-0000", "john2@test.com");
        boolean isDuplicate = addressBook.getContacts().stream().anyMatch(c -> c.equals(duplicate));
        assertTrue(isDuplicate);
    }

    @Test
    public void testSortByName() {
        addressBook.sortContactsByName();
        List<Contact> contacts = addressBook.getContacts();
        assertEquals("Jane", contacts.get(0).getFirstName());
        assertEquals("John", contacts.get(1).getFirstName());
    }
}
