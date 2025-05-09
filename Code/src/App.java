import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import screens.LoginScreen;

/**
 * Main entry point for the Event Management System
 */
public class App {
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application with the login screen
        SwingUtilities.invokeLater(() -> {
            new LoginScreen();
        });
    }
}
