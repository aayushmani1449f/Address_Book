package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDataSource implements AddressBookDataSource {
    private final String url = "jdbc:mysql://localhost:3306/address_book";
    private final String user = "root";
    private final String pass = "Aayush@01";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    public List<Contact> readData() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getDate("date_added").toLocalDate()
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void writeData(List<Contact> contacts) {
        // Not implemented for UC 16 (Only Retrieval). 
        // We will expand this in subsequent UCs.
        throw new UnsupportedOperationException("Write data not implemented yet.");
    }
}
