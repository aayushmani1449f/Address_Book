package com.addressbook;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Scanner scanner) {
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
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        
        boolean isDuplicate = contacts.stream().anyMatch(c -> c.equals(contact));
        if (isDuplicate) {
            System.out.println("Duplicate entry found! Contact not added.");
        } else {
            contacts.add(contact);
            System.out.println("Contact added successfully.");
        }
    }

    public void editContact(Scanner scanner) {
        System.out.print("Enter First Name of contact to edit: ");
        String name = scanner.nextLine();
        
        contacts.stream()
                .filter(c -> c.getFirstName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(contact -> {
                    System.out.print("Enter new Last Name: ");
                    contact.setLastName(scanner.nextLine());
                    System.out.print("Enter new Address: ");
                    contact.setAddress(scanner.nextLine());
                    System.out.print("Enter new City: ");
                    contact.setCity(scanner.nextLine());
                    System.out.print("Enter new State: ");
                    contact.setState(scanner.nextLine());
                    System.out.print("Enter new Zip: ");
                    contact.setZip(scanner.nextLine());
                    System.out.print("Enter new Phone Number: ");
                    contact.setPhoneNumber(scanner.nextLine());
                    System.out.print("Enter new Email: ");
                    contact.setEmail(scanner.nextLine());
                    System.out.println("Contact updated successfully.");
                }, () -> System.out.println("Contact not found."));
    }

    public void deleteContact(Scanner scanner) {
        System.out.print("Enter First Name of contact to delete: ");
        String name = scanner.nextLine();
        boolean removed = contacts.removeIf(c -> c.getFirstName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts in this address book.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void sortContactsByName() {
        System.out.println("Contacts sorted by Name:");
        contacts.stream()
                .sorted(Comparator.comparing(Contact::getFirstName).thenComparing(Contact::getLastName))
                .forEach(System.out::println);
    }

    public void sortContactsByCity() {
        System.out.println("Contacts sorted by City:");
        contacts.stream()
                .sorted(Comparator.comparing(Contact::getCity))
                .forEach(System.out::println);
    }

    public void sortContactsByState() {
        System.out.println("Contacts sorted by State:");
        contacts.stream()
                .sorted(Comparator.comparing(Contact::getState))
                .forEach(System.out::println);
    }

    public void sortContactsByZip() {
        System.out.println("Contacts sorted by Zip:");
        contacts.stream()
                .sorted(Comparator.comparing(Contact::getZip))
                .forEach(System.out::println);
    }
}
