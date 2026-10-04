package bookstore.gui;

import bookstore.model.BookStore;
import bookstore.model.Customer;
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
 * OwnerCustomersPanel allows the Owner to manage customers.
 * It provides functionality to:
 * - View all customers
 * - Add a new customer
 * - Delete an existing customer
 * - Navigate back to the owner dashboard
 */
public class OwnerCustomersPanel extends JPanel {

    // Reference to main frame for navigation
    private final MainFrame frame;

    // Reference to BookStore model for managing customers
    private final BookStore store;

    // Table model for displaying customer data
    private final DefaultTableModel tableModel;

    // Table UI component
    private final JTable table;

    // Input fields for adding a new customer
    private final JTextField usernameField;
    private final JTextField passwordField;

    /**
     * Constructor initializes UI layout and components.
     * @param frame reference to MainFrame
     * @param store reference to BookStore model
     */
    public OwnerCustomersPanel(MainFrame frame, BookStore store) {
        this.frame = frame;
        this.store = store;

        // Use BorderLayout for organizing components
        setLayout(new BorderLayout());

        // Table model with columns: username, password, points
        tableModel = new DefaultTableModel(new Object[]{"Username", "Password", "Points"}, 0) {

            // Prevent direct editing of table cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table and add it to center inside a scroll pane
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /**
         * Top panel for adding a new customer
         * Contains:
         * - Username input
         * - Password input
         * - Add button
         */
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        topPanel.add(usernameField);

        topPanel.add(new JLabel("Password:"));
        passwordField = new JTextField(15);
        topPanel.add(passwordField);

        JButton addButton = new JButton("Add");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        /**
         * Bottom panel for actions:
         * - Delete selected customer
         * - Navigate back
         */
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        backButton.addActionListener(e -> frame.showScreen("OWNER_START"));

        // Populate table initially
        refreshTable();
    }

    /**
     * Refreshes the table with current customer data.
     */
    public void refreshTable() {
        tableModel.setRowCount(0); // clear table

        // Add each customer to the table
        for (Customer c : store.getCustomers()) {
            tableModel.addRow(new Object[]{
                c.getUsername(),
                c.getPassword(),
                c.getPoints()
            });
        }
    }

    /**
     * Handles adding a new customer.
     * Ensures valid input and updates the model.
     */
    private void addCustomer() {

        // Get input values
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.");
            return;
        }

        // New customers start with 0 points (requirement)
        Customer newCustomer = new Customer(username, password, 0);

        // Attempt to add customer (ensures unique username)
        if (store.addCustomer(newCustomer)) {
            refreshTable(); // update table
            usernameField.setText(""); // clear inputs
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Customer could not be added. Username may already exist.");
        }
    }

    /**
     * Handles deleting a selected customer.
     */
    private void deleteCustomer() {

        // Get selected row from table
        int selectedRow = table.getSelectedRow();

        // If no selection, show error
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a customer to delete.");
            return;
        }

        // Get username from selected row
        String username = (String) tableModel.getValueAt(selectedRow, 0);

        // Find corresponding Customer object
        Customer customer = store.findCustomerByUsername(username);

        // Remove customer if found
        if (customer != null) {
            store.removeCustomer(customer);
            refreshTable(); // update table
        }
    }
}