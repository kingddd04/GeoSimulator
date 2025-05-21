package scripts;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The {@code SpritesLoader} class is responsible for loading and managing sprite images
 * for different planet types in the GeoSimulator application.
 * <p>
 * Depending on the planet type passed to the constructor, an appropriate tileset of images is loaded.
 * These images include the sky, surface, crust, and bedrock tiles. The images are loaded as resources
 * using the class loader, making them accessible within packaged applications such as JARs or EXEs.
 * </p>
 * <p>
 * Planet Type Identifiers:
 * <ul>
 *   <li>1 - Rocky Planet</li>
 *   <li>2 - Desert Planet</li>
 *   <li>3 - Volcanic Planet</li>
 *   <li>4 - Garden Planet</li>
 *   <li>5 - Icy Planet</li>
 * </ul>
 * </p>
 * 
 * @author Davide Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpritesLoader {
	
    private Image skyTileImage;  // 
    private Image surfaceTileImage;
    private Image crustTileImage;
    private Image bedrockTileImage;
    private Image[] tilesImages;

    /**
     * Constructs a new {@code SpritesLoader} and loads sprite images based on the specified planet type.
     * <p>
     * Depending on the identifier passed, the appropriate tileset creator method is called.
     * </p>
     *
     * @param planetTypeIds the identifier representing the planet type
     */
    public SpritesLoader(int planetTypeIds) {
        switch (planetTypeIds) {
            case 1:
                rockyPlanetTilesetCreator();
                break;
            case 2:
                desertPlanetTilesetCreator();
                break;
            case 3:
                volcanicPlanetTilesetCreator();
                break;
            case 4:
                gardenPlanetTilesetCreator();
                break;
            case 5:
                icyPlanetTilesetCreator();
                break;
        }
        tilesImages = new Image[]{skyTileImage, surfaceTileImage, crustTileImage, bedrockTileImage};
    }

    /**
     * Loads an image resource from the given path using the class loader.
     *
     * @param path the resource path to the image file
     * @return the loaded {@code Image} if successful; {@code null} otherwise
     */
    private Image loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads sprite images for the rocky planet tileset.
     */
    public void rockyPlanetTilesetCreator() {
        skyTileImage = loadImage("/sprites/RockySkyTile.png");
        surfaceTileImage = loadImage("/sprites/RockySurfaceTile.png");
        crustTileImage = loadImage("/sprites/RockyCrustTile.png");
        bedrockTileImage = loadImage("/sprites/BedrockTile.png");
    }

    /**
     * Loads sprite images for the desert planet tileset.
     */
    public void desertPlanetTilesetCreator() {
        skyTileImage = loadImage("/sprites/DesertSkyTile.png");
        surfaceTileImage = loadImage("/sprites/DesertSurfaceTile.png");
        crustTileImage = loadImage("/sprites/DesertCrustTile.png");
        bedrockTileImage = loadImage("/sprites/BedrockTile.png");
    }

    /**
     * Loads sprite images for the volcanic planet tileset.
     */
    public void volcanicPlanetTilesetCreator() {
        skyTileImage = loadImage("/sprites/VolcanicSkyTile.png");
        surfaceTileImage = loadImage("/sprites/VolcanicSurfaceTile.png");
        crustTileImage = loadImage("/sprites/VolcanicCrustTile.png");
        bedrockTileImage = loadImage("/sprites/BedrockTile.png");
    }

    /**
     * Loads sprite images for the garden planet tileset.
     */
    public void gardenPlanetTilesetCreator() {
        skyTileImage = loadImage("/sprites/SkyTile.png");
        surfaceTileImage = loadImage("/sprites/GardenSurfaceTile.png");
        crustTileImage = loadImage("/sprites/GardenCrustTile.png");
        bedrockTileImage = loadImage("/sprites/BedrockTile.png");
    }

    /**
     * Loads sprite images for the icy planet tileset.
     */
    public void icyPlanetTilesetCreator() {
        skyTileImage = loadImage("/sprites/ColdSkyTile.png");
        surfaceTileImage = loadImage("/sprites/SurfaceIceTile.png");
        crustTileImage = loadImage("/sprites/DeepIceTile.png");
        bedrockTileImage = loadImage("/sprites/BedrockTile.png");
    }

    /**
     * Retrieves the array of loaded tile images.
     *
     * @return an array of {@code Image} objects representing the tile images
     */
    public Image[] getTilesImages() {
        return tilesImages;
    }

    /**
     * A simple test driver for the {@code SpritesLoader} class.
     * <p>
     * This main method instantiates a {@code SpritesLoader} for a test planet type and
     * prints out whether each tile image was successfully loaded.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int testPlanetTypeId = 4; // Change this value to test different planet types
        SpritesLoader loader = new SpritesLoader(testPlanetTypeId);

        System.out.println("Tileset loaded for planet type ID: " + testPlanetTypeId);
        // Check if the images were loaded
        for (int i = 0; i < loader.tilesImages.length; i++) {
            System.out.println("Tile " + i + ": " + (loader.tilesImages[i] != null ? "Loaded" : "Not loaded"));
        }
    }
}