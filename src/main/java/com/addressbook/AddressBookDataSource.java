package com.addressbook;

import java.util.List;

public interface AddressBookDataSource {
    List<Contact> readData();
    void writeData(List<Contact> contacts);
}
