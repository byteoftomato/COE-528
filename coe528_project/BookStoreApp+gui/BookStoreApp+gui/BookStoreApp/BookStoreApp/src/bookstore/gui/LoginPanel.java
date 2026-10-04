package bookstore.gui;

import bookstore.model.BookStore;
import bookstore.model.Customer;
import bookstore.model.Owner;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * LoginPanel represents the login screen of the application.
 * It allows users to enter credentials and determines whether
 * they are an Owner or a Customer.
 */
public class LoginPanel extends JPanel {

    // Reference to main frame for switching screens
    private final MainFrame frame;

    // Reference to BookStore model for accessing customer data
    private final BookStore store;

    // Input fields for username and password
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    /**
     * Constructor initializes layout and UI components.
     * @param frame reference to MainFrame for navigation
     * @param store reference to BookStore model
     */
    public LoginPanel(MainFrame frame, BookStore store) {
        this.frame = frame;
        this.store = store;

        // Use GridBagLayout for flexible positioning of components
        setLayout(new GridBagLayout());

        // Constraints object to control placement and spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // spacing between components

        // Labels for input fields
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Input fields
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Login button
        JButton loginButton = new JButton("Login");

        // Add username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        // Add username input field
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Add password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        // Add password input field
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Add login button (centered across both columns)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // When login button is clicked, attempt login
        loginButton.addActionListener(e -> login());
    }

    /**
     * Handles login logic.
     * Determines whether user is Owner or Customer.
     */
    private void login() {

        // Get input values
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Check if credentials match Owner (admin/admin)
        Owner owner = new Owner();
        if (owner.login(username, password)) {
            clearFields();
            frame.showScreen("OWNER_START"); // switch to owner dashboard
            return;
        }

        // Otherwise, check if user is a valid Customer
        Customer customer = store.findCustomer(username, password);
        if (customer != null) {
            frame.setCurrentCustomer(customer); // store logged-in customer
            clearFields();
            frame.showCustomerStartPanel(); // switch to customer screen
            return;
        }

        // If neither Owner nor Customer, show error message
        JOptionPane.showMessageDialog(this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clears input fields after login attempt.
     */
    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}