import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/address_book";
        String user = "root";
        String pass = "Aayush@01";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
             
            System.out.println("Connected to address_book database!");

            // Drop existing tables to start fresh
            stmt.executeUpdate("DROP TABLE IF EXISTS contact_type_mapping;");
            stmt.executeUpdate("DROP TABLE IF EXISTS contact_types;");
            stmt.executeUpdate("DROP TABLE IF EXISTS contacts;");

            // Create Contacts Table
            String createContacts = "CREATE TABLE contacts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "first_name VARCHAR(100) NOT NULL," +
                    "last_name VARCHAR(100) NOT NULL," +
                    "address VARCHAR(255) NOT NULL," +
                    "city VARCHAR(100) NOT NULL," +
                    "state VARCHAR(100) NOT NULL," +
                    "zip VARCHAR(20) NOT NULL," +
                    "phone_number VARCHAR(20) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "date_added DATE NOT NULL" +
                    ");";
            stmt.executeUpdate(createContacts);
            System.out.println("Created contacts table.");

            // Create Contact Types Table
            String createTypes = "CREATE TABLE contact_types (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "type_name VARCHAR(100) NOT NULL" +
                    ");";
            stmt.executeUpdate(createTypes);
            System.out.println("Created contact_types table.");

            // Create Mapping Table
            String createMapping = "CREATE TABLE contact_type_mapping (" +
                    "contact_id INT NOT NULL," +
                    "type_id INT NOT NULL," +
                    "PRIMARY KEY (contact_id, type_id)," +
                    "FOREIGN KEY (contact_id) REFERENCES contacts(id)," +
                    "FOREIGN KEY (type_id) REFERENCES contact_types(id)" +
                    ");";
            stmt.executeUpdate(createMapping);
            System.out.println("Created contact_type_mapping table.");

            // Insert initial data
            stmt.executeUpdate("INSERT INTO contact_types (type_name) VALUES ('Family'), ('Friends'), ('Profession');");
            
            String insertContact = "INSERT INTO contacts " +
                    "(first_name, last_name, address, city, state, zip, phone_number, email, date_added) " +
                    "VALUES ('John', 'Doe', '123 Elm St', 'Springfield', 'IL', '62701', '555-1234', 'john@example.com', '2023-01-15');";
            stmt.executeUpdate(insertContact);

            stmt.executeUpdate("INSERT INTO contact_type_mapping (contact_id, type_id) VALUES (1, 1);"); // John is Family
            
            System.out.println("Inserted initial test data.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
