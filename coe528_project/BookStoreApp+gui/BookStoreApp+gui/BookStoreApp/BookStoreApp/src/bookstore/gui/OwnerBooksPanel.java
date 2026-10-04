package bookstore.gui;

import bookstore.model.Book;
import bookstore.model.BookStore;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * OwnerBooksPanel allows the Owner to manage books.
 * It provides functionality to:
 * - View all books
 * - Add a new book
 * - Delete an existing book
 * - Navigate back to the owner dashboard
 */
public class OwnerBooksPanel extends JPanel {

    // Reference to main frame for navigation
    private final MainFrame frame;

    // Reference to BookStore model for managing books
    private final BookStore store;

    // Table model for displaying books
    private final DefaultTableModel tableModel;

    // Table UI component
    private final JTable table;

    // Input fields for adding a book
    private final JTextField nameField;
    private final JTextField priceField;

    /**
     * Constructor initializes UI layout and components.
     * @param frame reference to MainFrame
     * @param store reference to BookStore model
     */
    public OwnerBooksPanel(MainFrame frame, BookStore store) {
        this.frame = frame;
        this.store = store;

        // Use BorderLayout to organize sections (top, center, bottom)
        setLayout(new BorderLayout());

        // Table model with columns for name and price
        tableModel = new DefaultTableModel(new Object[]{"Book Name", "Book Price"}, 0) {

            // Prevent table cells from being edited directly
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table and add it inside a scroll pane
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /**
         * Top panel for adding a new book
         * Contains:
         * - Name input
         * - Price input
         * - Add button
         */
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        topPanel.add(nameField);

        topPanel.add(new JLabel("Price:"));
        priceField = new JTextField(10);
        topPanel.add(priceField);

        JButton addButton = new JButton("Add");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        /**
         * Bottom panel for actions:
         * - Delete selected book
         * - Navigate back
         */
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> deleteBook());
        backButton.addActionListener(e -> frame.showScreen("OWNER_START"));

        // Populate table initially
        refreshTable();
    }

    /**
     * Refreshes the table with current book data from the store.
     */
    public void refreshTable() {
        tableModel.setRowCount(0); // clear table

        // Add each book to the table
        for (Book b : store.getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), b.getPrice()});
        }
    }

    /**
     * Handles adding a new book.
     * Validates input and updates the model.
     */
    private void addBook() {

        // Get input values
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        // Check for empty fields
        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and price.");
            return;
        }

        try {
            // Convert price input to double
            double price = Double.parseDouble(priceText);

            // Create new Book object
            Book newBook = new Book(name, price);

            // Attempt to add book to store (ensures no duplicates)
            if (store.addBook(newBook)) {
                refreshTable(); // update table
                nameField.setText(""); // clear inputs
                priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Book could not be added. It may already exist.");
            }

        } catch (NumberFormatException ex) {
            // Handle invalid price input
            JOptionPane.showMessageDialog(this, "Invalid price.");
        }
    }

    /**
     * Handles deleting a selected book from the table.
     */
    private void deleteBook() {

        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        // If no row selected, show error
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }

        // Get book name from selected row
        String bookName = (String) tableModel.getValueAt(selectedRow, 0);

        // Find corresponding Book object in store
        Book book = store.findBookByName(bookName);

        // Remove book if found
        if (book != null) {
            store.removeBook(book);
            refreshTable(); // update table
        }
    }
}