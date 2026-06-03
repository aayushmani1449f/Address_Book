package com.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileDataSource implements AddressBookDataSource {
    private final Path filePath;

    public TextFileDataSource(String fileName) {
        this.filePath = Paths.get(fileName);
    }

    @Override
    public List<Contact> readData() {
        List<Contact> contacts = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return contacts;
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length == 10) {
                    Contact contact = new Contact(
                            Integer.parseInt(data[0]),
                            data[1], data[2], data[3], data[4],
                            data[5], data[6], data[7], data[8],
                            LocalDate.parse(data[9])
                    );
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void writeData(List<Contact> contacts) {
        List<String> lines = contacts.stream().map(c -> String.join(",",
                String.valueOf(c.getId()),
                c.getFirstName(),
                c.getLastName(),
                c.getAddress(),
                c.getCity(),
                c.getState(),
                c.getZip(),
                c.getPhoneNumber(),
                c.getEmail(),
                c.getDateAdded().toString()
        )).collect(Collectors.toList());

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
