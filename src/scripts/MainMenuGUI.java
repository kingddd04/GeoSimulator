package scripts;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code MainMenuGUI} class represents the graphical user interface
 * for selecting planetary generation configurations in GeoSimulator.
 * <p>
 * This class allows users to:
 * <ul>
 *   <li>Select a planet type from predefined options</li>
 *   <li>Specify a planet's circumference in kilometers</li>
 *   <li>Set a seed value for procedural generation</li>
 *   <li>Initiate the planet generation process</li>
 * </ul>
 * The UI components are structured using Swing and include buttons, text fields,
 * and informational labels.
 * </p>
 *
 * @author Davide Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainMenuGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField circumferenceKmField;  // input field for circumference value
    private Byte planetTypeId = 0;            // indicates the type of the planet
    private int planetCircumferenceKm = 0;   // an integer to indicate the circumference of the planet
    private int seed;               // an integer used for the terrain generation
    private JTextField seedField;  // input field for seed value

    /**
     * Constructs the main menu GUI, setting up all UI components.
     */
    public MainMenuGUI() {
        setTitle("GeoSimulator_by_Kingddd04");

        // Load application icon
        ImageIcon appIcon = new ImageIcon(MainMenuGUI.class.getResource("/sprites/GeoSimulatorIcon.png"));
        setIconImage(appIcon.getImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);

        // Main panel
        JPanel GeneralPanel = new JPanel();
        getContentPane().add(GeneralPanel, BorderLayout.CENTER);
        GeneralPanel.setLayout(new GridLayout(6, 1, 0, 0));

        // Title label
        JLabel title = new JLabel("Choose your planet Configuration!");
        title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        GeneralPanel.add(title);

        // Planet selection icons
        JPanel planetsIconLabel = new JPanel();
        GeneralPanel.add(planetsIconLabel);
        planetsIconLabel.setLayout(new GridLayout(1, 5, 0, 0));

        planetsIconLabel.add(new JLabel(new ImageIcon(MainMenuGUI.class.getResource("/sprites/RockyPlanet.png"))));
        planetsIconLabel.add(new JLabel(new ImageIcon(MainMenuGUI.class.getResource("/sprites/DesertPlanet.png"))));
        planetsIconLabel.add(new JLabel(new ImageIcon(MainMenuGUI.class.getResource("/sprites/VolcanicPlanet.png"))));
        planetsIconLabel.add(new JLabel(new ImageIcon(MainMenuGUI.class.getResource("/sprites/GardenPlanet.png"))));
        planetsIconLabel.add(new JLabel(new ImageIcon(MainMenuGUI.class.getResource("/sprites/IcyPlanet.png"))));

        // Planet selection buttons
        JPanel planetsButtonPanel = new JPanel();
        GeneralPanel.add(planetsButtonPanel);
        planetsButtonPanel.setLayout(new GridLayout(0, 5, 0, 0));

        addPlanetButton(planetsButtonPanel, "Rocky Planet", (byte) 1);
        addPlanetButton(planetsButtonPanel, "Desert Planet", (byte) 2);
        addPlanetButton(planetsButtonPanel, "Volcanic Planet", (byte) 3);
        addPlanetButton(planetsButtonPanel, "Garden Planet", (byte) 4);
        addPlanetButton(planetsButtonPanel, "Icy Planet", (byte) 5);

        // Planet circumference input
        JPanel textInputPanel = new JPanel();
        GeneralPanel.add(textInputPanel);
        textInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        textInputPanel.add(new JLabel("Insert the planet circumference size (in km) → "));
        circumferenceKmField = new JTextField(10);
        textInputPanel.add(circumferenceKmField);

        // Seed input
        JPanel seedInput = new JPanel();
        GeneralPanel.add(seedInput);

        seedInput.add(new JLabel("Insert here the seed of your planet → "));
        seedField = new JTextField(10);
        seedInput.add(seedField);

        // Confirmation button
        JPanel confirmGenerationPanel = new JPanel();
        GeneralPanel.add(confirmGenerationPanel);

        JButton enterButton = new JButton("Generate Planet!");
        enterButton.addActionListener(e -> {
            if (checkInput()) {
                bootGeneratorManager();
                dispose();
            }
        });
        confirmGenerationPanel.add(enterButton);
    }

    /**
     * Creates a button for selecting a planet type and adds it to the panel.
     *
     * @param panel the panel to add the button to
     * @param name the button label representing the planet type
     * @param id the identifier for the planet type
     */
    private void addPlanetButton(JPanel panel, String name, byte id) {
        JButton button = new JButton(name);
        button.addActionListener(e -> planetTypeId = id);
        panel.add(button);
    }

    /**
     * Validates user input for planet type, circumference, and seed.
     * Displays an error message if any value is invalid.
     *
     * @return {@code true} if all inputs are valid, {@code false} otherwise
     */
    public boolean checkInput() {
        if (planetTypeId == 0) {
            JOptionPane.showMessageDialog(this, "Please select a planet type!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            planetCircumferenceKm = Integer.parseInt(circumferenceKmField.getText().trim());
            if (planetCircumferenceKm <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical value for planet circumference!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            seed = Integer.parseInt(seedField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical value for the seed!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Creates a {@code PlanetGenerationManager} instance using the validated input parameters.
     */
    public void bootGeneratorManager() {
        new PlanetGenerationManager(planetCircumferenceKm, seed, planetTypeId);
    }

    /**
     * The main entry point to launch the GUI.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}