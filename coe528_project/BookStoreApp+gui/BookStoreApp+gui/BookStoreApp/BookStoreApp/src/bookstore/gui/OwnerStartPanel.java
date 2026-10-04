package bookstore.gui;

import bookstore.model.BookStore;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * OwnerStartPanel represents the main dashboard for the Owner.
 * It provides navigation to:
 * - Manage Books
 * - Manage Customers
 * - Logout
 */
public class OwnerStartPanel extends JPanel {

    /**
     * Constructor initializes layout and navigation buttons.
     * @param frame reference to MainFrame for screen switching
     * @param store reference to BookStore (not directly used here but passed for consistency)
     */
    public OwnerStartPanel(MainFrame frame, BookStore store) {

        // Use GridBagLayout to vertically center buttons with spacing
        setLayout(new GridBagLayout());

        // Constraints to control spacing and positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // spacing between buttons

        // Buttons for navigation
        JButton booksButton = new JButton("Books");
        JButton customersButton = new JButton("Customers");
        JButton logoutButton = new JButton("Logout");

        // Add Books button (row 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(booksButton, gbc);

        // Add Customers button (row 1)
        gbc.gridy = 1;
        add(customersButton, gbc);

        // Add Logout button (row 2)
        gbc.gridy = 2;
        add(logoutButton, gbc);

        // Button actions (navigation handled by MainFrame)
        booksButton.addActionListener(e -> frame.showOwnerBooksPanel());
        customersButton.addActionListener(e -> frame.showOwnerCustomersPanel());
        logoutButton.addActionListener(e -> frame.logout());
    }
}