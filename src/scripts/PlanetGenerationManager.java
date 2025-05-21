package scripts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Manages the generation and display of a procedurally generated planet.
 * This class handles chunk-based terrain simulation, keyboard navigation,
 * and UI integration for planetary visualization.
 * 
 * @author Davide Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class PlanetGenerationManager implements KeyListener {

    private ChunkMatrixGenerator chunkGenerator;  //object stored for reference across the class
    private PlanetarySimulatorGUI gui;    //object stored for reference across the class
    private JFrame planetDisplayFrame;    // The main window for planet simulation
    private final int tileSize = 2;       // Size of each tile in pixels
    private final int chunkLength = 500;  // Number of tiles per chunk (horizontal)
    private final int chunkHeight = 250;  // Number of tiles per chunk (vertical)
    private int totalChunkNumber = 0;     // Total number of generated chunks
    private int currentChunkX = 1;        // Index of the currently displayed chunk
    private Image[] tilesImages;          // Array storing loaded tile images
    private int seed;                     // Seed for procedural generation
    private int heightModifier = 50;      // Modifier affecting terrain elevation
    private String[][] chunkMatrix;       // 2D array representing terrain structure

    /**
     * Initializes the planetary simulation with given parameters.
     *
     * @param kmPlanetCircumference the circumference of the planet in kilometers
     * @param seed the seed used for procedural generation
     * @param planetType the type of planet to generate
     */
    public PlanetGenerationManager(int kmPlanetCircumference, int seed, Byte planetType) {
        this.seed = seed;
        totalChunkNumber = kmPlanetCircumference;
        chunkMatrix = new String[chunkHeight][chunkLength];

        SpritesLoader spritesLoader = new SpritesLoader(planetType);
        tilesImages = spritesLoader.getTilesImages();
        chunkGenerator = new ChunkMatrixGenerator(heightModifier);
        chunkGenerator.chunkMatrixCreator(seed, currentChunkX, totalChunkNumber, chunkHeight, chunkLength, chunkMatrix);

        ImageIcon appIcon = new ImageIcon(MainMenuGUI.class.getResource("/sprites/GeoSimulatorIcon.png"));
        Image appImage = appIcon.getImage();
        String windowName = windowNameSelector(planetType);

        gui = new PlanetarySimulatorGUI(chunkHeight, chunkLength, tileSize, tilesImages, chunkMatrix);
        gui.setFocusable(true);
        gui.requestFocusInWindow();
        gui.addKeyListener(this);

        // Setup display frame
        planetDisplayFrame = new JFrame(windowName);
        planetDisplayFrame.add(gui);
        planetDisplayFrame.pack();
        planetDisplayFrame.setResizable(false);
        planetDisplayFrame.setVisible(true);
        planetDisplayFrame.setIconImage(appImage);
        planetDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures proper closing behavior

        gui.updateChunkMap(chunkMatrix);
    }

    /**
     * Updates the chunk displayed based on navigation direction.
     *
     * @param direction the direction to move (-1 for left, +1 for right)
     */
    public void chunkGenerator(byte direction) {
        int nextChunkX = currentChunkX + direction;

        if (nextChunkX == 0) {
            nextChunkX = totalChunkNumber;
        } else if (nextChunkX == totalChunkNumber + 1) {
            nextChunkX = 1;
        }
        currentChunkX = nextChunkX;
        chunkGenerator.chunkMatrixCreator(seed, currentChunkX, totalChunkNumber, chunkHeight, chunkLength, chunkMatrix);
        gui.updateChunkMap(chunkMatrix);
    }

    /**
     * Determines the window title based on the selected planet type.
     *
     * @param planetType the identifier for the planet type
     * @return the name of the planet corresponding to the given type
     */
    public String windowNameSelector(Byte planetType) {
        switch (planetType) {
            case 1: return "Rocky Planet";
            case 2: return "Desert Planet";
            case 3: return "Volcanic Planet";
            case 4: return "Garden Planet";
            case 5: return "Icy Planet";
            default: return "Unknown Planet id";
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}

    /**
     * Handles key release events to navigate planetary terrain.
     * Left arrow moves to the previous chunk, right arrow moves to the next chunk.
     *
     * @param e the key event triggered upon release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        byte direction = 0;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = 1;
        }
        chunkGenerator(direction);
    }
}
