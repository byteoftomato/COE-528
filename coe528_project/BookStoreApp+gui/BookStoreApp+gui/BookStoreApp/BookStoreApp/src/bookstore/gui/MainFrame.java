package bookstore.gui;

import bookstore.model.BookStore;
import bookstore.model.Customer;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * MainFrame is the main window of the application.
 * It controls:
 * - Screen navigation using CardLayout
 * - Data loading and saving
 * - Managing the currently logged-in customer
 */
public class MainFrame extends JFrame {

    // Model containing books and customers
    private final BookStore store;

    // Layout manager used to switch between different screens (panels)
    private final CardLayout cardLayout;

    // Main container holding all panels
    private final JPanel mainPanel;

    // Stores the currently logged-in customer
    private Customer currentCustomer;

    // All application screens (panels)
    private final LoginPanel loginPanel;
    private final OwnerStartPanel ownerStartPanel;
    private final OwnerBooksPanel ownerBooksPanel;
    private final OwnerCustomersPanel ownerCustomersPanel;
    private final CustomerStartPanel customerStartPanel;
    private final CustomerCostPanel customerCostPanel;

    /**
     * Constructor initializes the application window,
     * loads data, and sets up all screens.
     */
    public MainFrame() {
        super("BookStore App");

        // Initialize model and load data from files
        store = new BookStore();
        try {
            store.loadData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not load data files.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Initialize CardLayout for switching screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create all panels/screens
        loginPanel = new LoginPanel(this, store);
        ownerStartPanel = new OwnerStartPanel(this, store);
        ownerBooksPanel = new OwnerBooksPanel(this, store);
        ownerCustomersPanel = new OwnerCustomersPanel(this, store);
        customerStartPanel = new CustomerStartPanel(this, store);
        customerCostPanel = new CustomerCostPanel(this);

        // Add panels to main container with identifiers
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(ownerStartPanel, "OWNER_START");
        mainPanel.add(ownerBooksPanel, "OWNER_BOOKS");
        mainPanel.add(ownerCustomersPanel, "OWNER_CUSTOMERS");
        mainPanel.add(customerStartPanel, "CUSTOMER_START");
        mainPanel.add(customerCostPanel, "CUSTOMER_COST");

        // Set main panel as the content of the frame
        setContentPane(mainPanel);

        // Window settings
        setSize(900, 600);
        setLocationRelativeTo(null); // center window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /**
         * Window listener to handle closing the app.
         * Ensures data is saved before exiting.
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    store.saveData(); // save books and customers to file
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Could not save data files.",
                            "Save Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                System.exit(0);
            }
        });

        // Show login screen initially
        showScreen("LOGIN");
    }

    /**
     * Switches between panels using CardLayout.
     * @param screenName the identifier of the panel to show
     */
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    /**
     * Shows Owner Books panel and refreshes table data.
     */
    public void showOwnerBooksPanel() {
        ownerBooksPanel.refreshTable();
        showScreen("OWNER_BOOKS");
    }

    /**
     * Shows Owner Customers panel and refreshes table data.
     */
    public void showOwnerCustomersPanel() {
        ownerCustomersPanel.refreshTable();
        showScreen("OWNER_CUSTOMERS");
    }

    /**
     * Sets the currently logged-in customer.
     * @param customer the customer who logged in
     */
    public void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    /**
     * Returns the currently logged-in customer.
     * @return current customer
     */
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    /**
     * Shows Customer Start panel and updates displayed data.
     */
    public void showCustomerStartPanel() {
        customerStartPanel.refreshData();
        showScreen("CUSTOMER_START");
    }

    /**
     * Shows Customer Cost panel after purchase.
     * @param totalCost the calculated cost of the transaction
     */
    public void showCustomerCostPanel(double totalCost) {
        customerCostPanel.updateLabels(totalCost, currentCustomer);
        showScreen("CUSTOMER_COST");
    }

    /**
     * Logs out the current user and returns to login screen.
     */
    public void logout() {
        currentCustomer = null;
        showScreen("LOGIN");
    }
}