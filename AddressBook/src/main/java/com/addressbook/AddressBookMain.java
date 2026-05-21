package com.userregistration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBookMain {
    private Map<String, AddressBook> addressBooks;

    public AddressBookMain() {
        this.addressBooks = new HashMap<>();
    }

    public void addAddressBook(String name) {
        if (addressBooks.containsKey(name)) {
            System.out.println("Address Book already exists!");
        } else {
            addressBooks.put(name, new AddressBook());
            System.out.println("Address Book added successfully.");
        }
    }

    public AddressBook getAddressBook(String name) {
        return addressBooks.get(name);
    }

    public void searchPerson(String location, boolean isCity) {
        System.out.println("Search results in " + location + ":");
        addressBooks.values().stream()
                .flatMap(addressBook -> addressBook.getContacts().stream())
                .filter(contact -> isCity ? contact.getCity().equalsIgnoreCase(location) : contact.getState().equalsIgnoreCase(location))
                .forEach(System.out::println);
    }

    public void viewPersonsByLocation(boolean isCity) {
        Map<String, List<Contact>> locationDictionary;
        if (isCity) {
            locationDictionary = addressBooks.values().stream()
                    .flatMap(addressBook -> addressBook.getContacts().stream())
                    .collect(Collectors.groupingBy(Contact::getCity));
            System.out.println("Persons viewed by City: " + locationDictionary);
        } else {
            locationDictionary = addressBooks.values().stream()
                    .flatMap(addressBook -> addressBook.getContacts().stream())
                    .collect(Collectors.groupingBy(Contact::getState));
            System.out.println("Persons viewed by State: " + locationDictionary);
        }
    }

    public void countPersonsByLocation(String location, boolean isCity) {
        long count = addressBooks.values().stream()
                .flatMap(addressBook -> addressBook.getContacts().stream())
                .filter(contact -> isCity ? contact.getCity().equalsIgnoreCase(location) : contact.getState().equalsIgnoreCase(location))
                .count();
        System.out.println("Total persons in " + location + ": " + count);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        Scanner scanner = new Scanner(System.in);
        AddressBookMain system = new AddressBookMain();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add New Address Book");
            System.out.println("2. Select Address Book");
            System.out.println("3. Search Person by City or State");
            System.out.println("4. View Persons by City or State");
            System.out.println("5. Count Persons by City or State");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name for new Address Book: ");
                    String name = scanner.nextLine();
                    system.addAddressBook(name);
                    break;
                case 2:
                    System.out.print("Enter Address Book name to select: ");
                    String selectedName = scanner.nextLine();
                    AddressBook selectedBook = system.getAddressBook(selectedName);
                    if (selectedBook != null) {
                        system.addressBookMenu(selectedBook, scanner);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                case 3:
                    System.out.print("Search by (1) City or (2) State? Enter choice: ");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (searchChoice == 1) {
                        System.out.print("Enter City: ");
                        String city = scanner.nextLine();
                        system.searchPerson(city, true);
                    } else if (searchChoice == 2) {
                        System.out.print("Enter State: ");
                        String state = scanner.nextLine();
                        system.searchPerson(state, false);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 4:
                    System.out.print("View by (1) City or (2) State? Enter choice: ");
                    int viewChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (viewChoice == 1) {
                        system.viewPersonsByLocation(true);
                    } else if (viewChoice == 2) {
                        system.viewPersonsByLocation(false);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 5:
                    System.out.print("Count by (1) City or (2) State? Enter choice: ");
                    int countChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (countChoice == 1) {
                        System.out.print("Enter City: ");
                        String city = scanner.nextLine();
                        system.countPersonsByLocation(city, true);
                    } else if (countChoice == 2) {
                        System.out.print("Enter State: ");
                        String state = scanner.nextLine();
                        system.countPersonsByLocation(state, false);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    public void addressBookMenu(AddressBook addressBook, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Address Book Menu ---");
            System.out.println("1. Add Contact\n2. Edit Contact\n3. Delete Contact\n4. Display Contacts\n5. Sort Contacts by Name\n6. Sort Contacts by Location (City/State/Zip)\n7. Write to Text File\n8. Read from Text File\n9. Write to CSV File\n10. Read from CSV File\n11. Write to JSON File\n12. Read from JSON File\n13. Go Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addressBook.addContact(scanner);
                    break;
                case 2:
                    addressBook.editContact(scanner);
                    break;
                case 3:
                    addressBook.deleteContact(scanner);
                    break;
                case 4:
                    addressBook.displayContacts();
                    break;
                case 5:
                    addressBook.sortContactsByName();
                    break;
                case 6:
                    System.out.print("Sort by (1) City, (2) State, or (3) Zip? Enter choice: ");
                    int sortChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (sortChoice == 1) addressBook.sortContactsByCity();
                    else if (sortChoice == 2) addressBook.sortContactsByState();
                    else if (sortChoice == 3) addressBook.sortContactsByZip();
                    else System.out.println("Invalid choice.");
                    break;
                case 7:
                    new AddressBookFileIOService().writeData(addressBook.getContacts());
                    break;
                case 8:
                    new AddressBookFileIOService().readData();
                    break;
                case 9:
                    new AddressBookCSVService().writeData(addressBook.getContacts());
                    break;
                case 10:
                    new AddressBookCSVService().readData();
                    break;
                case 11:
                    new AddressBookJSONService().writeData(addressBook.getContacts());
                    break;
                case 12:
                    new AddressBookJSONService().readData();
                    break;
                case 13:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
