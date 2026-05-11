import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        Scanner scanner = new Scanner(System.in);
        AddressBookMain system = new AddressBookMain();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add New Address Book");
            System.out.println("2. Select Address Book");
            System.out.println("3. Exit");
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
            System.out.println("1. Add Contact\n2. Edit Contact\n3. Delete Contact\n4. Display Contacts\n5. Go Back to Main Menu");
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
