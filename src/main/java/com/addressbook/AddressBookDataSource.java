package com.addressbook;

import java.util.List;

/**
 * Core Interface representing any data source for the Address Book System.
 * 
 * UC 23: This interface explicitly ensures that the Open-Closed Principle (OCP) 
 * is not violated. Whenever a new data source is added (e.g., CSV, JSON, Database, JSONServer),
 * we only need to create a new class implementing this interface. The core AddressBook logic
 * does not need to be modified.
 */
public interface AddressBookDataSource {
    List<Contact> readData();
    void writeData(List<Contact> contacts);
}
