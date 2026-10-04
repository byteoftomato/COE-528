package bookstore.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central class that manages books and customers.
 * Also loads/saves data from/to files.
 */
public class BookStore {
    private final List<Book> books;
    private final List<Customer> customers;

    // Default file names from the project specification.
    private static final String BOOKS_FILE = "books.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";

    public BookStore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable view of the book list so GUI code can read it
     * without directly changing it.
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Returns an unmodifiable view of the customer list.
     */
    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    /**
     * Finds a customer using username and password.
     * Returns null if not found.
     */
    public Customer findCustomer(String username, String password) {
        for (Customer c : customers) {
            if (c.login(username, password)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds a customer by username only.
     * Helpful when checking duplicates.
     */
    public Customer findCustomerByUsername(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds a book by name.
     * Returns null if not found.
     */
    public Book findBookByName(String name) {
        for (Book b : books) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Adds a book if the name is not already used.
     * The project doc says only one copy of a given book is stored.
     */
    public boolean addBook(Book b) {
        if (b == null || b.getName() == null || b.getName().trim().isEmpty()) {
            return false;
        }
        if (b.getPrice() < 0) {
            return false;
        }
        if (findBookByName(b.getName()) != null) {
            return false; // duplicate book name not allowed
        }
        books.add(b);
        return true;
    }

    /**
     * Removes a specific book object.
     */
    public boolean removeBook(Book b) {
        return books.remove(b);
    }

    /**
     * Adds a customer if username is unique.
     */
    public boolean addCustomer(Customer c) {
        if (c == null || c.getUsername() == null || c.getUsername().trim().isEmpty()) {
            return false;
        }
        if (findCustomerByUsername(c.getUsername()) != null) {
            return false; // duplicate username not allowed
        }
        customers.add(c);
        return true;
    }

    /**
     * Removes a specific customer object.
     */
    public boolean removeCustomer(Customer c) {
        return customers.remove(c);
    }

    /**
     * Loads both books and customers from books.txt and customers.txt.
     * Existing in-memory data is cleared first.
     *
     * books.txt format:
     * Book Name,Book Price
     *
     * customers.txt format:
     * username,password,points
     */
    public void loadData() throws IOException {
        books.clear();
        customers.clear();

        loadBooks();
        loadCustomers();
    }

    /**
     * Saves both books and customers to books.txt and customers.txt.
     * Old file content is overwritten.
     */
    public void saveData() throws IOException {
        saveBooks();
        saveCustomers();
    }

    private void loadBooks() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    addBook(new Book(name, price));
                }
            }
        } catch (IOException e) {
            // If file does not exist yet, keep empty list.
            // Re-throw only if you want strict file handling.
        }
    }

    private void loadCustomers() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    addCustomer(new Customer(username, password, points));
                }
            }
        } catch (IOException e) {
            // If file does not exist yet, keep empty list.
        }
    }

    private void saveBooks() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : books) {
                bw.write(b.getName() + "," + b.getPrice());
                bw.newLine();
            }
        }
    }

    private void saveCustomers() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                bw.write(c.getUsername() + "," + c.getPassword() + "," + c.getPoints());
                bw.newLine();
            }
        }
    }
}
