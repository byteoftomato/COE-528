package bookstore;

import bookstore.gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * App is the entry point of the application.
 * It launches the GUI by creating and displaying the MainFrame.
 */
public class App {

    public static void main(String[] args) {

        /**
         * Ensures that the GUI is created and updated
         * on the Event Dispatch Thread (EDT), which is
         * required for thread-safe Swing applications.
         */
        SwingUtilities.invokeLater(() -> {

            // Create main application window
            MainFrame frame = new MainFrame();

            // Make the window visible to the user
            frame.setVisible(true);
        });
    }
}