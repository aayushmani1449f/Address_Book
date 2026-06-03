package com.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFileDataSource implements AddressBookDataSource {
    private final Path filePath;

    public CsvFileDataSource(String fileName) {
        this.filePath = Paths.get(fileName);
    }

    @Override
    public List<Contact> readData() {
        List<Contact> contacts = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return contacts;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath.toFile()))) {
            String[] nextLine;
            
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 10) {
                    Contact contact = new Contact(
                            Integer.parseInt(nextLine[0]),
                            nextLine[1], nextLine[2], nextLine[3], nextLine[4],
                            nextLine[5], nextLine[6], nextLine[7], nextLine[8],
                            LocalDate.parse(nextLine[9])
                    );
                    contacts.add(contact);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void writeData(List<Contact> contacts) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath.toFile()))) {
            
            String[] header = {"ID", "FirstName", "LastName", "Address", "City", "State", "Zip", "PhoneNumber", "Email", "DateAdded"};
            writer.writeNext(header);

            for (Contact c : contacts) {
                String[] data = {
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
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
