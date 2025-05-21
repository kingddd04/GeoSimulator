package scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;          // For planet circumference
    private Byte planetTypeId = 0;
    private int planetCircumferenceKm = 0;
    private int seed;                      // To store the seed value
    private JTextField textField_1;        // For seed input

    public MainMenuGUI() {
        setTitle("GeoSimulator_by_Kingddd04");
        
        ImageIcon appIcon = new ImageIcon(MainMenuGUI.class.getResource("/sprites/GeoSimulatorIcon.png"));
        Image appImage = appIcon.getImage();
        setIconImage(appImage);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        
        JPanel GeneralPanel = new JPanel();
        getContentPane().add(GeneralPanel, BorderLayout.CENTER);
        GeneralPanel.setLayout(new GridLayout(6, 1, 0, 0));
        
        JLabel title = new JLabel("Choose your planet Configuration!");
        title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        GeneralPanel.add(title);
        
        JPanel planetsIconLabel = new JPanel();
        GeneralPanel.add(planetsIconLabel);
        planetsIconLabel.setLayout(new GridLayout(1, 5, 0, 0));
        
        ImageIcon rockyPlanetIcon = new ImageIcon("src/sprites/RockyPlanet.png");
        JLabel lblNewLabel_1 = new JLabel(rockyPlanetIcon);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        planetsIconLabel.add(lblNewLabel_1);
        
        ImageIcon desertPlanetIcon = new ImageIcon("src/sprites/DesertPlanet.png");
        JLabel lblNewLabel_2 = new JLabel(desertPlanetIcon);
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        planetsIconLabel.add(lblNewLabel_2);
        
        ImageIcon volcanicPlanetIcon = new ImageIcon("src/sprites/VolcanicPlanet.png");
        JLabel lblNewLabel_3 = new JLabel(volcanicPlanetIcon);
        planetsIconLabel.add(lblNewLabel_3);
        
        ImageIcon gardenPlanetIcon = new ImageIcon("src/sprites/GardenPlanet.png");
        JLabel lblNewLabel_4 = new JLabel(gardenPlanetIcon);
        planetsIconLabel.add(lblNewLabel_4);
        
        ImageIcon icyPlanetIcon = new ImageIcon("src/sprites/IcyPlanet.png");
        JLabel lblNewLabel_5 = new JLabel(icyPlanetIcon);
        planetsIconLabel.add(lblNewLabel_5);
        
        JPanel planetsButtonPanel = new JPanel();
        GeneralPanel.add(planetsButtonPanel);
        planetsButtonPanel.setLayout(new GridLayout(0, 5, 0, 0));
        
        JButton btnNewButton_1 = new JButton("Rocky Planet");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planetTypeId = 1;
            }
        });
        planetsButtonPanel.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Desert Planet");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planetTypeId = 2;
            }
        });
        planetsButtonPanel.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Volcanic Planet");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planetTypeId = 3;
            }
        });
        planetsButtonPanel.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("Garden Planet");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planetTypeId = 4;
            }
        });
        planetsButtonPanel.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Icy Planet");
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                planetTypeId = 5;
            }
        });
        planetsButtonPanel.add(btnNewButton_5);
        
        JPanel textInputPanel = new JPanel();
        GeneralPanel.add(textInputPanel);
        textInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel_6 = new JLabel("Insert the planet circumference size (in km) → ");
        textInputPanel.add(lblNewLabel_6);
        
        textField = new JTextField();
        textInputPanel.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_7 = new JLabel("<html>"
        	    + "Circumference Parameter Expanation:<br>"
        	    + "The software generates planets procedurally, no memory or disk storage is used.<br>"
        	    + "Each pixel is <b>2m</b>, and the chunk size is <b>500</b>.<br>"
        	    + "Each window (chunk) represents <b>1 km</b>.<br>"
        	    + "Can simulate effortlessly a planet with Earth circumference (<b>40,000 km</b>)."
        	    + "</html>");
        textInputPanel.add(lblNewLabel_7);
        
        JPanel seedInput = new JPanel();
        GeneralPanel.add(seedInput);
        
        JLabel lblNewLabel = new JLabel("Insert here the seed of your planet  → ");
        seedInput.add(lblNewLabel);
        
        textField_1 = new JTextField();
        seedInput.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_8 = new JLabel("<html>"
        	    + "<b>Seed Parameter explanation:</b><br>"
        	    + "Each seed is a <i>unique</i> identifier that defines how the planet is generated.<br>"
        	    + "Once set, the seed ensures <b>persistent</b> and reproducible world generation.<br>"
        	    + "No matter when it is used, the same seed will always create the same terrain."
        	    + "</html>");
        seedInput.add(lblNewLabel_8);
        
        JPanel confirmGenerationPanel = new JPanel();
        GeneralPanel.add(confirmGenerationPanel);
        
        JButton enterButton = new JButton("Generate Planet!");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkInput()) {
                    bootGeneratorManager();
                    dispose();
                }
            }
        });
        confirmGenerationPanel.add(enterButton);
    }

    public boolean checkInput() {
        // Check if a planet type has been selected.
        if (planetTypeId == 0) {
            JOptionPane.showMessageDialog(this, "Please select a planet type!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate and record planet circumference.
        String inputText = textField.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid planet circumference!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int circumference = Integer.parseInt(inputText);
            if (circumference <= 0) {
                JOptionPane.showMessageDialog(this, "Planet circumference must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            planetCircumferenceKm = circumference;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical value for planet circumference!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate and record the seed.
        String seedText = textField_1.getText().trim();
        if (seedText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid seed value!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            seed = Integer.parseInt(seedText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical value for the seed!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    public void bootGeneratorManager() {
        // Now that our inputs (planetCircumferenceKm, seed, and planetTypeId) are validated,
        // create the PlanetGenerationManager passing these values.
        @SuppressWarnings("unused")
		PlanetGenerationManager planetGenerationManager =  new PlanetGenerationManager(planetCircumferenceKm, seed, planetTypeId);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenuGUI().setVisible(true);
            }
        });
    }
}
