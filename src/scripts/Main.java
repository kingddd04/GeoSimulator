package scripts;

import java.awt.EventQueue;

/**
 * The Main class serves as the entry point for the GeoSimulator application.
 * It initializes and displays the MainMenuGUI on the Event Dispatch Thread (EDT).
 * <p>
 * This ensures that all GUI updates occur on the proper thread according
 * to Swing's single-thread rule.
 * </p>
 * 
 * @author Davide
 * Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class Main {

    /**
     * The main method which launches the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MainMenuGUI().setVisible(true);
        });
    }
}