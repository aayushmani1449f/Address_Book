package com.userregistration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddressBookFileIOService {

    public static final String ADDRESS_BOOK_FILE_NAME = "addressbook-file.txt";

    public void writeData(List<Contact> contactList) {
        StringBuffer empBuffer = new StringBuffer();
        contactList.forEach(contact -> {
            String contactDataString = contact.toString().concat("\n");
            empBuffer.append(contactDataString);
        });
        try {
            Files.write(Paths.get(ADDRESS_BOOK_FILE_NAME), empBuffer.toString().getBytes());
            System.out.println("Data successfully written to file: " + ADDRESS_BOOK_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            if (Files.exists(Paths.get(ADDRESS_BOOK_FILE_NAME))) {
                System.out.println("Reading contacts from file:");
                Files.lines(new File(ADDRESS_BOOK_FILE_NAME).toPath())
                        .map(String::trim)
                        .forEach(System.out::println);
            } else {
                System.out.println("No file found to read.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
