package scripts;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PlanetGenerationManager implements KeyListener{
	
	ChunkMatrixGenerator chunkGenerator;
	PlanetarySimulatorGUI gui;
	
	private final int tileSize = 2;
	private final int chunkLenght = 500;
	private final int chunkHeight = 250;
	
	private int totalChunkNumber = 0;
	private int currentChunkX = 1;
	private Image[] tilesImages;
	private int seed;
	private int heightModifier = 50;
	private String[][] chunkMatrix;
	
	
	public PlanetGenerationManager(int kmPlanetCircumference, int seed, Byte planetType){
		this.seed = seed;
		totalChunkNumber = kmPlanetCircumference;
		chunkMatrix = new String[chunkHeight][chunkLenght];
		
		SpritesLoader spritesLoader = new SpritesLoader(planetType);
		tilesImages = spritesLoader.getTilesImages();
		chunkGenerator = new ChunkMatrixGenerator(heightModifier);
		chunkGenerator.chunkMatrixCreator(seed, currentChunkX ,totalChunkNumber, chunkHeight, chunkLenght , chunkMatrix);
		
		
		ImageIcon appIcon = new ImageIcon(MainMenuGUI.class.getResource("/sprites/GeoSimulatorIcon.png"));
        Image appImage = appIcon.getImage();
		String windowName = windowNameSelector(planetType);
		
		gui = new PlanetarySimulatorGUI(chunkHeight, chunkLenght, tileSize, tilesImages, chunkMatrix);
		gui.setFocusable(true);
		gui.requestFocusInWindow();
		
		gui.addKeyListener(this);
		JFrame planetDisplayFrame = new JFrame(windowName);
		planetDisplayFrame.add(gui);
		planetDisplayFrame.pack();
		planetDisplayFrame.setResizable(false);
		planetDisplayFrame.setVisible(true);
		planetDisplayFrame.setIconImage(appImage);
	
		gui.updateChunkMap(chunkMatrix);		
	}
	
	public void chunkGenerator(byte direction) {
	    int newChunk = currentChunkX + direction;
	    
	    if (newChunk == 0) {
	        newChunk = totalChunkNumber;  
	    } else if (newChunk == totalChunkNumber+1) {
	        newChunk = 1;                 
	    }
	    currentChunkX = newChunk;
		chunkGenerator.chunkMatrixCreator(seed, currentChunkX,  totalChunkNumber, chunkHeight, chunkLenght, chunkMatrix);
		gui.updateChunkMap(chunkMatrix);
		
	}
	
	public String windowNameSelector(Byte planeType) {
		
		String planetName = "";
		
		switch (planeType){
		case 1:
			planetName = "Rocky Planet";
			break;
		case 2:
			planetName = "Desert Planet";
			break;
		case 3:
			planetName = "Volcanic Planet";
			break;
		case 4:
			planetName = "Garden Planet";
			break;
		case 5:
			planetName = "Icy Planet";
			break;
		}
		return planetName;
	}
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

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
