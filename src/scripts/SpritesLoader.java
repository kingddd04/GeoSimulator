package scripts;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritesLoader {
	private Image skyTileImage;
	private Image surfaceTileImage;
	private Image crustTileImage;
	private Image bedrockTileImage;
	private Image[] tilesImages;
	
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
	
	public void rockyPlanetTilesetCreator() {
		try {
			skyTileImage = ImageIO.read(new File("src/sprites/RockySkyTile.png"));
			surfaceTileImage = ImageIO.read(new File("src/sprites/RockySurfaceTile.png"));
			crustTileImage =  ImageIO.read(new File("src/sprites/RockyCrustTile.png"));
			bedrockTileImage = ImageIO.read(new File("src/sprites/BedrockTile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void desertPlanetTilesetCreator() {
		try {
			skyTileImage = ImageIO.read(new File("src/sprites/DesertSkyTile.png"));
			surfaceTileImage = ImageIO.read(new File("src/sprites/DesertSurfaceTile.png"));
			crustTileImage =  ImageIO.read(new File("src/sprites/DesertCrustTile.png"));
			bedrockTileImage = ImageIO.read(new File("src/sprites/BedrockTile.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void volcanicPlanetTilesetCreator() {
		try {
			skyTileImage = ImageIO.read(new File("src/sprites/VolcanicSkyTile.png"));
			surfaceTileImage = ImageIO.read(new File("src/sprites/VolcanicSurfaceTile.png"));
			crustTileImage =  ImageIO.read(new File("src/sprites/VolcanicCrustTile.png"));
			bedrockTileImage = ImageIO.read(new File("src/sprites/BedrockTile.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gardenPlanetTilesetCreator() {
		try {
			skyTileImage = ImageIO.read(new File("src/sprites/SkyTile.png"));
			surfaceTileImage = ImageIO.read(new File("src/sprites/GardenSurfaceTile.png"));
			crustTileImage =  ImageIO.read(new File("src/sprites/GardenCrustTile.png"));
			bedrockTileImage = ImageIO.read(new File("src/sprites/BedrockTile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void icyPlanetTilesetCreator() {
		try {
			surfaceTileImage = ImageIO.read(new File("src/sprites/SurfaceIceTile.png"));
			skyTileImage = ImageIO.read(new File("src/sprites/ColdSkyTile.png"));
			crustTileImage =  ImageIO.read(new File("src/sprites/DeepIceTile.png"));
			bedrockTileImage = ImageIO.read(new File("src/sprites/BedrockTile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image[] getTilesImages() {
		return tilesImages;		
	}
	
	public static void main(String[] args) {
	    int testPlanetTypeId = 4; // Puoi cambiare questo valore per testare diversi tipi di pianeta

	    SpritesLoader loader = new SpritesLoader(testPlanetTypeId);

	    System.out.println("Tileset caricato per il tipo di pianeta ID: " + testPlanetTypeId);

	    // Controllo se le immagini sono state caricate
	    for (int i = 0; i < loader.tilesImages.length; i++) {
	        System.out.println("Tile " + i + ": " + (loader.tilesImages[i] != null ? "Caricata" : "Non caricata"));
	    }
	}

}
