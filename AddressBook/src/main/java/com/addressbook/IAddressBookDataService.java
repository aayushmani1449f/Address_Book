package com.addressbook;

import java.util.List;

public interface IAddressBookDataService {
    void writeData(List<Contact> contacts);
    void readData();
}
