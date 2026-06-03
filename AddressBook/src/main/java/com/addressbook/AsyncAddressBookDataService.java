package com.addressbook;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncAddressBookDataService implements IAddressBookDataService {
    
    private final IAddressBookDataService delegate;
    private final ExecutorService executorService;

    public AsyncAddressBookDataService(IAddressBookDataService delegate) {
        this.delegate = delegate;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void writeData(List<Contact> contacts) {
        CompletableFuture.runAsync(() -> {
            System.out.println("Async Write started...");
            delegate.writeData(contacts);
            System.out.println("Async Write completed.");
        }, executorService);
    }

    @Override
    public void readData() {
        CompletableFuture.runAsync(() -> {
            System.out.println("Async Read started...");
            delegate.readData();
            System.out.println("Async Read completed.");
        }, executorService);
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}
