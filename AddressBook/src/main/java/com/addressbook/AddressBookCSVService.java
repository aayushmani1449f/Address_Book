package com.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class AddressBookCSVService implements IAddressBookDataService {

    public static final String ADDRESS_BOOK_CSV_FILE = "addressbook-file.csv";

    public void writeData(List<Contact> contactList) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(ADDRESS_BOOK_CSV_FILE))) {
            StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(contactList);
            System.out.println("Data successfully written to CSV file: " + ADDRESS_BOOK_CSV_FILE);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            if (Files.exists(Paths.get(ADDRESS_BOOK_CSV_FILE))) {
                System.out.println("Reading contacts from CSV file:");
                try (Reader reader = Files.newBufferedReader(Paths.get(ADDRESS_BOOK_CSV_FILE));
                     CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
                    List<String[]> records = csvReader.readAll();
                    for (String[] record : records) {
                        System.out.println("Contact: " + String.join(", ", record));
                    }
                }
            } else {
                System.out.println("No CSV file found to read.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
