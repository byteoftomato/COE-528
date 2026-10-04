package bookstore.gui;

import bookstore.model.Customer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * CustomerCostPanel displays the final transaction details
 * after a customer completes a purchase.
 * It shows:
 * - Total cost of selected books
 * - Remaining points and customer status
 * - Logout option
 */
public class CustomerCostPanel extends JPanel {

    // Reference to the main frame to allow navigation (e.g., logout)
    private final MainFrame frame;

    // Label to display total cost of the purchase
    private final JLabel totalCostLabel;

    // Label to display updated points and customer status
    private final JLabel pointsStatusLabel;

    /**
     * Constructor initializes the layout and UI components.
     * @param frame reference to MainFrame for navigation control
     */
    public CustomerCostPanel(MainFrame frame) {
        this.frame = frame;

        // Use GridBagLayout for flexible component positioning
        setLayout(new GridBagLayout());

        // Constraints object to control placement and spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // spacing between components

        // Initialize labels with default placeholder text
        totalCostLabel = new JLabel("Total Cost: ");
        pointsStatusLabel = new JLabel("Points: , Status: ");

        // Logout button allows user to return to login screen
        JButton logoutButton = new JButton("Logout");

        // Add total cost label at row 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(totalCostLabel, gbc);

        // Add points/status label at row 1
        gbc.gridy = 1;
        add(pointsStatusLabel, gbc);

        // Add logout button at row 2
        gbc.gridy = 2;
        add(logoutButton, gbc);

        // Action listener for logout button
        // Calls MainFrame method to switch back to login screen
        logoutButton.addActionListener(e -> frame.logout());
    }

    /**
     * Updates the labels with actual transaction data.
     * This method is called after a purchase is completed.
     *
     * @param totalCost the final cost after purchase (and possible redemption)
     * @param customer the current customer object containing updated points/status
     */
    public void updateLabels(double totalCost, Customer customer) {

        // Display total cost
        totalCostLabel.setText("Total Cost: " + totalCost);

        // Display updated points and membership status
        pointsStatusLabel.setText("Points: " + customer.getPoints()
                + ", Status: " + customer.getStatusName());
    }
}