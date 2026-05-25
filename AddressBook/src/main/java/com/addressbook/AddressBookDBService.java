package com.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService implements IAddressBookDataService {

    private static final String URL = "jdbc:mysql://localhost:3306/address_book_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password"; // Set actual password

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void writeData(List<Contact> contacts) {
        for (Contact contact : contacts) {
            addContactToDB(contact);
        }
    }

    public void addContactToDB(Contact contact) {
        String query = "INSERT INTO contacts (first_name, last_name, address, city, state, zip, phone_number, email, date_added) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // DB Transaction implemented
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, contact.getFirstName());
                preparedStatement.setString(2, contact.getLastName());
                preparedStatement.setString(3, contact.getAddress());
                preparedStatement.setString(4, contact.getCity());
                preparedStatement.setString(5, contact.getState());
                preparedStatement.setString(6, contact.getZip());
                preparedStatement.setString(7, contact.getPhoneNumber());
                preparedStatement.setString(8, contact.getEmail());
                if (contact.getDateAdded() != null) {
                    preparedStatement.setDate(9, java.sql.Date.valueOf(contact.getDateAdded()));
                } else {
                    preparedStatement.setNull(9, java.sql.Types.DATE);
                }
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void readData() {
        List<Contact> contacts = readDataFromDB();
        System.out.println("Contacts read from DB: ");
        contacts.forEach(System.out::println);
    }

    public List<Contact> readDataFromDB() {
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("zip"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getDate("date_added") != null ? resultSet.getDate("date_added").toLocalDate() : null
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
    public int updateContactPhone(String firstName, String lastName, String newPhone) {
        String query = "UPDATE contacts SET phone_number = ? WHERE first_name = ? AND last_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPhone);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isContactInSync(String firstName, String lastName, List<Contact> memoryContacts) {
        List<Contact> dbContacts = getContactFromDB(firstName, lastName);
        if (dbContacts.isEmpty()) return false;
        Contact dbContact = dbContacts.get(0);
        Contact memoryContact = memoryContacts.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                .findFirst().orElse(null);
        if (memoryContact == null) return false;
        
        return dbContact.getPhoneNumber().equals(memoryContact.getPhoneNumber());
    }

    public List<Contact> getContactFromDB(String firstName, String lastName) {
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE first_name = ? AND last_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("zip"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getDate("date_added") != null ? resultSet.getDate("date_added").toLocalDate() : null
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List<Contact> getContactsAddedInPeriod(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("zip"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getDate("date_added") != null ? resultSet.getDate("date_added").toLocalDate() : null
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public int getCountByCity(String city) {
        return getCount("SELECT COUNT(*) FROM contacts WHERE city = ?", city);
    }

    public int getCountByState(String state) {
        return getCount("SELECT COUNT(*) FROM contacts WHERE state = ?", state);
    }

    private int getCount(String query, String parameter) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
