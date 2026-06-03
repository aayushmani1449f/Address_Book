package com.addressbook;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncAddressBookDataSource {
    private final AddressBookDataSource dataSource;

    public AsyncAddressBookDataSource(AddressBookDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CompletableFuture<List<Contact>> readDataAsync() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Reading data asynchronously on thread: " + Thread.currentThread().getName());
            return dataSource.readData();
        });
    }

    public CompletableFuture<Void> writeDataAsync(List<Contact> contacts) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Writing data asynchronously on thread: " + Thread.currentThread().getName());
            dataSource.writeData(contacts);
        });
    }
}
