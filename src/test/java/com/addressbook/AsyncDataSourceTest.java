package com.addressbook;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AsyncDataSourceTest {

    @Test
    void testAsyncRead() throws ExecutionException, InterruptedException {
        // We will use DatabaseDataSource to demonstrate async read
        AddressBookDataSource dbDataSource = new DatabaseDataSource();
        AsyncAddressBookDataSource asyncDataSource = new AsyncAddressBookDataSource(dbDataSource);
        
        CompletableFuture<List<Contact>> futureContacts = asyncDataSource.readDataAsync();
        
        // Ensure main thread is not blocked
        System.out.println("Main thread is doing other work...");
        
        // Wait for async task to complete
        List<Contact> contacts = futureContacts.get();
        
        assertNotNull(contacts, "Async read should return a list of contacts");
    }
}
