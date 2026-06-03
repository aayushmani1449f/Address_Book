package com.addressbook;

import java.time.LocalDate;
import java.util.Scanner;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Address Book System!");

        AddressBookDataSource dataSource = selectDataSource(scanner);
        AddressBook addressBook = new AddressBook();

        if (dataSource != null) {
            try {
                System.out.println("Loading existing contacts...");
                addressBook.getContacts().addAll(dataSource.readData());
                System.out.println("Loaded " + addressBook.getContacts().size() + " contacts.");
            } catch (Exception e) {
                System.out.println("Could not load data. Starting with an empty address book.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- Address Book Menu ---");
            System.out.println("1. Add Contact");
            System.out.println("2. Display All Contacts");
            System.out.println("3. Save and Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    Contact newContact = createContactFromInput(scanner);
                    addressBook.addContact(newContact);
                    System.out.println("Contact added successfully.");
                    break;
                case 2:
                    System.out.println("\n--- Contacts ---");
                    for (Contact c : addressBook.getContacts()) {
                        System.out.println(c.getFirstName() + " " + c.getLastName() + " - " + c.getCity() + ", " + c.getState());
                    }
                    break;
                case 3:
                    if (dataSource != null) {
                        System.out.println("Saving contacts to data source...");
                        try {
                            dataSource.writeData(addressBook.getContacts());
                            System.out.println("Saved successfully.");
                        } catch (Exception e) {
                            System.out.println("Error saving data: " + e.getMessage());
                        }
                    }
                    System.out.println("Exiting Address Book. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static AddressBookDataSource selectDataSource(Scanner scanner) {
        System.out.println("\nSelect Data Source for Persistence:");
        System.out.println("1. In-Memory (No saving)");
        System.out.println("2. Text File (contacts.txt)");
        System.out.println("3. CSV File (contacts.csv)");
        System.out.println("4. JSON File (contacts.json)");
        System.out.println("5. Database (MySQL JDBC)");
        System.out.print("Choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 2: return new TextFileDataSource("contacts.txt");
            case 3: return new CsvFileDataSource("contacts.csv");
            case 4: return new JsonFileDataSource("contacts.json");
            case 5: return new DatabaseDataSource();
            default: return null;
        }
    }

    private static Contact createContactFromInput(Scanner scanner) {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        System.out.print("Enter Zip: ");
        String zip = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        return new Contact(0, firstName, lastName, address, city, state, zip, phone, email, LocalDate.now());
    }
}
