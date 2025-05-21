package scripts;

import java.awt.*;
import javax.swing.*;

public class PlanetarySimulatorGUI extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -853606071410406319L;

    private final int tileSize;
    private final int chunkLenght;
	private final int chunkHeight;
    private String[][] chunkMap;
    private Image[] tilesImages;
    
    
    public PlanetarySimulatorGUI(int chunkHeight, int chunkLenght, int tileSize , Image[] spritesImages ,String[][] chunkMap) {
		this.chunkLenght = chunkLenght;
		this.chunkHeight = chunkHeight;
		this.tilesImages = spritesImages;
		this.tileSize = tileSize;
		this.chunkMap = chunkMap;
	
        setPreferredSize(new Dimension(chunkLenght* tileSize, chunkHeight * tileSize));
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    	int bedrockHeight =chunkHeight - chunkHeight / 5;

        for (int r = 0; r < chunkHeight; r++) {
            for (int c = 0; c < chunkLenght; c++) {
            	Image img = null;
            	if(chunkMap[r][c] == "S") {
            		img = tilesImages[0];
            	}else if (chunkMap[r-10][c] == "S" && chunkMap[r][c] == "T") {
            		img = tilesImages[1];
            	}else if (chunkMap[r][c] == "T" && r <=  bedrockHeight) {
            		img = tilesImages[2];
            	}else if(r > bedrockHeight) {
            		img = tilesImages[3];
            	}
                g.drawImage(img, c * tileSize, r * tileSize, tileSize, tileSize, this);
            }
        }
    }
    
    public void updateChunkMap(String[][] chunkMap) {
    	this.chunkMap = chunkMap;
    	repaint();
	}
    
    
}