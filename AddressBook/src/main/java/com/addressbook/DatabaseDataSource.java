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

    public int getContactCountByCity(String city) {
        String query = "SELECT COUNT(*) as count FROM contacts WHERE city = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getContactCountByState(String state) {
        String query = "SELECT COUNT(*) as count FROM contacts WHERE state = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, state);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean addContactWithTransaction(Contact contact, List<Integer> typeIds) {
        String insertContact = "INSERT INTO contacts (first_name, last_name, address, city, state, zip, phone_number, email, date_added) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertMapping = "INSERT INTO contact_type_mapping (contact_id, type_id) VALUES (?, ?)";
        
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); 
            
            
            int newContactId = -1;
            try (PreparedStatement pstmtContact = conn.prepareStatement(insertContact, Statement.RETURN_GENERATED_KEYS)) {
                pstmtContact.setString(1, contact.getFirstName());
                pstmtContact.setString(2, contact.getLastName());
                pstmtContact.setString(3, contact.getAddress());
                pstmtContact.setString(4, contact.getCity());
                pstmtContact.setString(5, contact.getState());
                pstmtContact.setString(6, contact.getZip());
                pstmtContact.setString(7, contact.getPhoneNumber());
                pstmtContact.setString(8, contact.getEmail());
                pstmtContact.setDate(9, Date.valueOf(contact.getDateAdded()));
                
                pstmtContact.executeUpdate();
                
                try (ResultSet rs = pstmtContact.getGeneratedKeys()) {
                    if (rs.next()) {
                        newContactId = rs.getInt(1);
                    }
                }
            }
            
            
            if (newContactId != -1 && typeIds != null) {
                try (PreparedStatement pstmtMapping = conn.prepareStatement(insertMapping)) {
                    for (int typeId : typeIds) {
                        pstmtMapping.setInt(1, newContactId);
                        pstmtMapping.setInt(2, typeId);
                        pstmtMapping.addBatch();
                    }
                    pstmtMapping.executeBatch();
                }
            }
            
            conn.commit(); 
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }
}
