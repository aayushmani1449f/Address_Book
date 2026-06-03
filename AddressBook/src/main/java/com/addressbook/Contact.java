package com.addressbook;

import java.time.LocalDate;
import java.util.Objects;

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;
    private LocalDate dateAdded;

    public Contact() {}

    public Contact(int id, String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email, LocalDate dateAdded) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateAdded = dateAdded;
    }

    public Contact(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email, LocalDate dateAdded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateAdded = dateAdded;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDate dateAdded) { this.dateAdded = dateAdded; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
               Objects.equals(firstName, contact.firstName) &&
               Objects.equals(lastName, contact.lastName) &&
               Objects.equals(address, contact.address) &&
               Objects.equals(city, contact.city) &&
               Objects.equals(state, contact.state) &&
               Objects.equals(zip, contact.zip) &&
               Objects.equals(phoneNumber, contact.phoneNumber) &&
               Objects.equals(email, contact.email) &&
               Objects.equals(dateAdded, contact.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, city, state, zip, phoneNumber, email, dateAdded);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
