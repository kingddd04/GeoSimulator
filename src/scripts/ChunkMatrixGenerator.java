package scripts;

import java.util.Arrays;

public class ChunkMatrixGenerator {
	private int[] heightmap;
	private int heightModifier;

	
	public ChunkMatrixGenerator(int heightModifier) {
		this.heightModifier = heightModifier;
		heightmap =new int[25];
	}
	public int pseudorandomHeightGenerator(int seed, int globalChunkX, int localChunkX )  {
		final long A = 2654435761L; // Knuth's multiplicative constant
		final long B = 2246822519L;
		final long C = 3266489917L;

		// Initial combination of values
		long combined = (seed * A) ^ (globalChunkX * B) ^ (localChunkX * C);

		// Aggressive mixing (inspired by MurmurHash3)
		combined ^= (combined >> 16);
		combined = (combined * 0x85ebca6bL) & 0xFFFFFFFFL; // Masking to 32 bits
		combined ^= (combined >> 13);
		combined = (combined * 0xc2b2ae35L) & 0xFFFFFFFFL;
		combined ^= (combined >> 16);

		// Generating the final number between 1 and 100
        int randomNumber = (int) ((Math.abs(combined) % 100) + 1);
		return randomNumber;   
	}
	
	public void heightmapGenerator(int seed, int totalChunksNumber, int currentChunkX) {
		for(int i = 1; i < 24; i++ ) {
			heightmap[i] = pseudorandomHeightGenerator(seed,currentChunkX,i );
			}		
		if(currentChunkX == 1) {
			heightmap[0] = pseudorandomHeightGenerator(seed, totalChunksNumber, currentChunkX);
			heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, currentChunkX+1);
		}else if(currentChunkX == totalChunksNumber){
			heightmap[0] = pseudorandomHeightGenerator(seed, currentChunkX-1, currentChunkX);
			heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, 1);
		}else {
			heightmap[0] = pseudorandomHeightGenerator(seed, currentChunkX-1, currentChunkX);
			heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, currentChunkX+1);			
		}
	}
		

	public void chunkMatrixCreator(int seed, int currentChunkX, int totalChunksNumber,  int chunkHeight, int chunkLenght, String[][] chunkMap){
		heightmapGenerator(seed, totalChunksNumber , currentChunkX);		
		int targetHeight = 0;
		int currentHeight = heightmap[0]+ heightModifier;
		
		int step = 0;
		int heightmapIndex = 0;
		
		for(int x = 0; x < chunkLenght; x++) {
        	if(x % 20 == 0) {
        		heightmapIndex = x / 20 ;
        		if(heightmapIndex != 0) {
        			targetHeight = heightmap[heightmapIndex] + heightModifier;
        		}
        	}
            if (currentHeight < targetHeight) {
            	if(targetHeight - currentHeight > 20 && heightmapIndex == 24) {
            		step = (targetHeight / 20)+2;
            		currentHeight+= step;
            	}else {
            		currentHeight++;                   // goes down
				}

                
                
            } else if (currentHeight > targetHeight) {
            	if(currentHeight - targetHeight > 20 && heightmapIndex == 24 ) {
            		step = (targetHeight / 20)+2;
            		currentHeight-= step;
            	}else {
            		currentHeight--;                   // goes up
				}
           
            }
            for (int y = 0; y < chunkHeight; y++ ) {
            	//System.out.println("Trying to access chunkMatrix[" + y + "][" + x + "]");
            	if (y >= currentHeight) {
            		chunkMap[y][x] = "T";
            	} else {
            	    chunkMap[y][x] = "S";
            	}
            }
        }
	}
    
    public void printtHeightmap() {
    	System.out.println(Arrays.toString(heightmap));
	}

	public static void main(String[] argStrings) {
		ChunkMatrixGenerator sChunkMatrixGenerator = new ChunkMatrixGenerator(50) ;
		String[][] chunkMatrix = new String[250][500];
		sChunkMatrixGenerator.chunkMatrixCreator(328, 2, 0, 250, 500, chunkMatrix);
    }

}
