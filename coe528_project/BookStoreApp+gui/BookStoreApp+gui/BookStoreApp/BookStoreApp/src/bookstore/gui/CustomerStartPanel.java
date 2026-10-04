package bookstore.gui;

import bookstore.model.Book;
import bookstore.model.BookStore;
import bookstore.model.Customer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * CustomerStartPanel represents the main screen for a logged-in customer.
 * It allows the customer to:
 * - View available books
 * - Select books to purchase
 * - Buy books (with or without redeeming points)
 * - Logout
 */
public class CustomerStartPanel extends JPanel {

    // Reference to main frame for navigation between screens
    private final MainFrame frame;

    // Reference to the BookStore model for accessing books
    private final BookStore store;

    // Label displaying welcome message, points, and status
    private final JLabel welcomeLabel;

    // Table model for managing book data in the JTable
    private final DefaultTableModel tableModel;

    // Table displaying books and selection checkboxes
    private final JTable table;

    /**
     * Constructor initializes UI components and layout.
     * @param frame reference to MainFrame for screen switching
     * @param store reference to BookStore model
     */
    public CustomerStartPanel(MainFrame frame, BookStore store) {
        this.frame = frame;
        this.store = store;

        // Use BorderLayout to organize components (top, center, bottom)
        setLayout(new BorderLayout());

        // Welcome label at the top
        welcomeLabel = new JLabel();
        add(welcomeLabel, BorderLayout.NORTH);

        // Table model with 3 columns: name, price, and selection checkbox
        tableModel = new DefaultTableModel(new Object[]{"Book Name", "Book Price", "Select"}, 0) {

            // Ensure checkbox column (column 2) is treated as Boolean
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 2) return Boolean.class;
                return String.class;
            }
        };

        // Create table and add it inside a scroll pane
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel for action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton buyButton = new JButton("Buy");
        JButton redeemButton = new JButton("Redeem points and Buy");
        JButton logoutButton = new JButton("Logout");

        bottomPanel.add(buyButton);
        bottomPanel.add(redeemButton);
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        buyButton.addActionListener(e -> handleBuy(false)); // normal purchase
        redeemButton.addActionListener(e -> handleBuy(true)); // purchase with redemption
        logoutButton.addActionListener(e -> frame.logout()); // return to login screen
    }

    /**
     * Refreshes the panel with current customer info and book list.
     * Called whenever this screen is shown.
     */
    public void refreshData() {

        // Get currently logged-in customer
        Customer currentCustomer = frame.getCurrentCustomer();

        // Update welcome message with username, points, and status
        if (currentCustomer != null) {
            welcomeLabel.setText("Welcome " + currentCustomer.getUsername()
                    + ". You have " + currentCustomer.getPoints()
                    + " points. Your status is " + currentCustomer.getStatusName());
        }

        // Clear existing table rows
        tableModel.setRowCount(0);

        // Populate table with books from the store
        for (Book b : store.getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), String.valueOf(b.getPrice()), false});
        }
    }

    /**
     * Handles buying logic.
     * @param redeem true if customer chooses to redeem points, false otherwise
     */
    private void handleBuy(boolean redeem) {

        // Get current customer
        Customer currentCustomer = frame.getCurrentCustomer();
        if (currentCustomer == null) return;

        // List to store selected books
        List<Book> selectedBooks = new ArrayList<>();

        // Iterate through table rows to find selected books
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            // Check if checkbox is selected
            Boolean selected = (Boolean) tableModel.getValueAt(i, 2);

            if (selected != null && selected) {

                // Get book name from table
                String bookName = (String) tableModel.getValueAt(i, 0);

                // Find actual Book object from store
                Book b = store.findBookByName(bookName);

                if (b != null) {
                    selectedBooks.add(b);
                }
            }
        }

        // If no books selected, show error message
        if (selectedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one book.");
            return;
        }

        double totalCost;

        // Perform purchase based on whether points are redeemed
        if (redeem) {
            totalCost = currentCustomer.redeemPointsAndBuy(selectedBooks);
        } else {
            totalCost = currentCustomer.buyBooks(selectedBooks);
        }

        // Switch to cost panel and display results
        frame.showCustomerCostPanel(totalCost);
    }
}