package com.userregistration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AddressBookJSONService {

    public static final String ADDRESS_BOOK_JSON_FILE = "addressbook-file.json";

    public void writeData(List<Contact> contactList) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(ADDRESS_BOOK_JSON_FILE))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(contactList, writer);
            System.out.println("Data successfully written to JSON file: " + ADDRESS_BOOK_JSON_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            if (Files.exists(Paths.get(ADDRESS_BOOK_JSON_FILE))) {
                System.out.println("Reading contacts from JSON file:");
                try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESS_BOOK_JSON_FILE))) {
                    Gson gson = new Gson();
                    Type contactListType = new TypeToken<List<Contact>>(){}.getType();
                    List<Contact> contacts = gson.fromJson(reader, contactListType);
                    if (contacts != null) {
                        for (Contact contact : contacts) {
                            System.out.println(contact);
                        }
                    }
                }
            } else {
                System.out.println("No JSON file found to read.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
