package com.addressbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
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

        Contact newContact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email, LocalDate.now());
        
        boolean isDuplicate = contacts.stream().anyMatch(contact -> contact.equals(newContact));
        if (isDuplicate) {
            System.out.println("Error: A contact with the same first and last name already exists in this address book.");
        } else {
            contacts.add(newContact);
            System.out.println("Contact added successfully!");
        }
    }

    public void addMultipleContacts(Scanner scanner) {
        System.out.print("How many contacts do you want to add? ");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            System.out.println("\nAdding Contact " + (i + 1) + ":");
            addContact(scanner);
        }
    }

    public void editContact(String name, Scanner scanner) {
        for (Contact contact : contacts) {
            if (contact.getFirstName().equalsIgnoreCase(name)) {
                System.out.println("Editing Contact: " + contact.getFirstName());
                System.out.print("Enter New First Name: ");
                contact.setFirstName(scanner.nextLine());
                System.out.print("Enter New Last Name: ");
                contact.setLastName(scanner.nextLine());
                System.out.print("Enter New Address: ");
                contact.setAddress(scanner.nextLine());
                System.out.print("Enter New City: ");
                contact.setCity(scanner.nextLine());
                System.out.print("Enter New State: ");
                contact.setState(scanner.nextLine());
                System.out.print("Enter New Zip: ");
                contact.setZip(scanner.nextLine());
                System.out.print("Enter New Phone Number: ");
                contact.setPhoneNumber(scanner.nextLine());
                System.out.print("Enter New Email: ");
                contact.setEmail(scanner.nextLine());
                System.out.println("Contact updated successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void deleteContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getFirstName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to display.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
