package scripts;

import java.awt.*;
import javax.swing.*;

/**
 * The {@code PlanetarySimulatorGUI} class is responsible for rendering the
 * procedural planet terrain using a chunk-based visualization system.
 * <p>
 * It extends {@link JPanel} and utilizes Java 2D graphics for drawing tiles
 * based on a provided chunk matrix. The terrain consists of multiple layers,
 * including sky, surface, crust, and bedrock, represented with image textures.
 * </p>
 * 
 * @author Davide Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class PlanetarySimulatorGUI extends JPanel {

    private static final long serialVersionUID = -853606071410406319L;
    private final int tileSize;    //the size of a side of a painted square
    private final int chunkLenght; //the lenght in pixels of a chunk
    private final int chunkHeight; //the height in pixels of a chunk
    private String[][] chunkMap;  //the matrix that represents the content of a chunk
    private Image[] tilesImages;  //an array that contains the images to print on the jpanel

    /**
     * Constructs the planetary simulation GUI with specified chunk parameters.
     * <p>
     * The simulation panel automatically scales according to the chunk size and
     * terrain features. Images are used to visually represent different terrain types.
     * </p>
     *
     * @param chunkHeight  the number of rows (vertical size) in the chunk matrix
     * @param chunkLenght  the number of columns (horizontal size) in the chunk matrix
     * @param tileSize     the size of each tile in pixels
     * @param spritesImages an array of images representing different terrain layers
     * @param chunkMap     a 2D {@code String} array representing the terrain of the current chunk
     */
    public PlanetarySimulatorGUI(int chunkHeight, int chunkLenght, int tileSize, Image[] spritesImages, String[][] chunkMap) {
        this.chunkLenght = chunkLenght;
        this.chunkHeight = chunkHeight;
        this.tilesImages = spritesImages;
        this.tileSize = tileSize;
        this.chunkMap = chunkMap;

        setPreferredSize(new Dimension(chunkLenght * tileSize, chunkHeight * tileSize));
    }

    /**
     * Overrides the {@link JPanel#paintComponent(Graphics)} method to render terrain chunks.
     * <p>
     * The terrain is drawn tile by tile using a predefined logic that maps terrain data
     * to specific tile images, including sky, surface, crust, and bedrock layers.
     * </p>
     *
     * @param g the {@code Graphics} object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int bedrockHeight = chunkHeight - chunkHeight / 5;

        for (int r = 0; r < chunkHeight; r++) {
            for (int c = 0; c < chunkLenght; c++) {
                Image img = null;

                if ("S".equals(chunkMap[r][c])) {
                    img = tilesImages[0]; // Sky tile
                } else if (r >= 10 && "S".equals(chunkMap[r - 10][c]) && "T".equals(chunkMap[r][c])) {
                    img = tilesImages[1]; // Surface tile
                } else if ("T".equals(chunkMap[r][c]) && r <= bedrockHeight) {
                    img = tilesImages[2]; // Crust tile
                } else if (r > bedrockHeight) {
                    img = tilesImages[3]; // Bedrock tile
                }

                g.drawImage(img, c * tileSize, r * tileSize, tileSize, tileSize, this);
            }
        }
    }

    /**
     * Updates the terrain chunk matrix and triggers a repaint.
     *
     * @param chunkMap the new chunk matrix to be displayed
     */
    public void updateChunkMap(String[][] chunkMap) {
        this.chunkMap = chunkMap;
        repaint();
    }
}