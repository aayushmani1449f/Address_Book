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
        // Reserved for bulk write if needed
        throw new UnsupportedOperationException("Bulk write data not implemented yet.");
    }

    public int updateContactCity(String firstName, String newCity) {
        String updateQuery = "UPDATE contacts SET city = ? WHERE first_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            
            pstmt.setString(1, newCity);
            pstmt.setString(2, firstName);
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Contact getContactByName(String firstName) {
        String query = "SELECT * FROM contacts WHERE first_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, firstName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Contact(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Contact> getContactsAddedBetween(LocalDate startDate, LocalDate endDate) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(
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
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
