package scripts;

import java.util.Arrays;

/**
 * The {@code ChunkMatrixGenerator} class is responsible for generating a 
 * terrain chunk matrix based on a procedurally generated heightmap.
 * This class uses a pseudorandom number generator with a combination of Knuth's 
 * multiplicative constant and a mixing algorithm inspired by MurmurHash3 to create
 * a heightmap. The heightmap is then used to generate a matrix of terrain types,
 * where each cell in the chunk matrix is set to a specific terrain type based on its height.
 * <p>
 * The generated chunk matrix is represented as a 2D array of Strings, where "T" and "S" 
 * indicate different terrain types.
 * </p>
 * 
 * @author Davide Di Stefano
 * @version 1.0.0
 * @since 1.0.0
 */
public class ChunkMatrixGenerator {

    private int[] heightmap; //An array holding height values for the terrain.

    private int heightModifier; // a modifier for the height of the terrain

    /**
     * Constructs a new {@code ChunkMatrixGenerator} using the specified height modifier.
     *
     * @param heightModifier the value to be added to the generated height values
     */
    public ChunkMatrixGenerator(int heightModifier) {
        this.heightModifier = heightModifier;
        heightmap = new int[25];
    }

    /**
     * Generates a pseudorandom height value between 1 and 100 using the given seed,
     * global chunk index, and local chunk position.
     * <p>
     * The method combines the seed and chunk positions with fixed multipliers, performs an
     * aggressive mixing of bits, and finally maps the result into the desired range.
     * </p>
     *
     * @param seed         the seed used for pseudo-random number generation
     * @param globalChunkX the global chunk index (used as part of the hash)
     * @param localChunkX  the local chunk position (used as part of the hash)
     * @return an integer value between 1 and 100 representing the pseudorandom height
     */
    public int pseudorandomHeightGenerator(int seed, int globalChunkX, int localChunkX)  {
        final long A = 2654435761L; // Knuth's multiplicative constant
        final long B = 2246822519L;
        final long C = 3266489917L;

        // Combine seed and chunk values using XOR with multiplicative constants
        long combined = (seed * A) ^ (globalChunkX * B) ^ (localChunkX * C);

        // Aggressive mixing inspired by MurmurHash3
        combined ^= (combined >> 16);
        combined = (combined * 0x85ebca6bL) & 0xFFFFFFFFL; // Masking to 32 bits
        combined ^= (combined >> 13);
        combined = (combined * 0xc2b2ae35L) & 0xFFFFFFFFL;
        combined ^= (combined >> 16);

        // Normalize result to the range 1 to 100
        int randomNumber = (int) ((Math.abs(combined) % 100) + 1);
        return randomNumber;
    }

    /**
     * Generates a heightmap based on the provided seed, total number of chunks,
     * and the current chunk index.
     * <p>
     * For indices 1 through 23 (inclusive), the height values are generated using the 
     * current chunk index. The first and last indices of the heightmap are handled specially 
     * depending on whether the current chunk is the first or the last chunk.
     * </p>
     *
     * @param seed              the seed used for procedural generation
     * @param totalChunksNumber the total number of chunks for the planet circumference
     * @param currentChunkX     the currently processed chunk index
     */
    public void heightmapGenerator(int seed, int totalChunksNumber, int currentChunkX) {
        for (int i = 1; i < 24; i++) {
            heightmap[i] = pseudorandomHeightGenerator(seed, currentChunkX, i);
        }
        if (currentChunkX == 1) {
            heightmap[0] = pseudorandomHeightGenerator(seed, totalChunksNumber, currentChunkX);
            heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, currentChunkX + 1);
        } else if (currentChunkX == totalChunksNumber) {
            heightmap[0] = pseudorandomHeightGenerator(seed, currentChunkX - 1, currentChunkX);
            heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, 1);
        } else {
            heightmap[0] = pseudorandomHeightGenerator(seed, currentChunkX - 1, currentChunkX);
            heightmap[24] = pseudorandomHeightGenerator(seed, currentChunkX, currentChunkX + 1);
        }
    }

    /**
     * Creates a chunk matrix (terrain map) using the generated heightmap.
     * <p>
     * The method first generates a heightmap using {@link #heightmapGenerator(int, int, int)}.
     * Then it uses the heightmap values to determine the target height at discrete intervals
     * across the chunk. The current height is adjusted gradually to approach the target height,
     * and for each x-coordinate in the chunk, each y-coordinate is set to "T" if it is at or above the 
     * current height or to "S" otherwise.
     * </p>
     *
     * @param seed              the seed used for procedural generation
     * @param currentChunkX     the index of the current chunk being processed
     * @param totalChunksNumber the total number of chunks for the planet circumference
     * @param chunkHeight       the number of rows (vertical size) in the chunk matrix
     * @param chunkLenght       the number of columns (horizontal size) in the chunk matrix
     * @param chunkMap          a 2D {@code String} array representing the terrain of the current chunk;
     *                          this array is modified by the method
     */
    public void chunkMatrixCreator(int seed, int currentChunkX, int totalChunksNumber, int chunkHeight, int chunkLenght, String[][] chunkMap) {
        heightmapGenerator(seed, totalChunksNumber, currentChunkX);
        int targetHeight = 0;
        int currentHeight = heightmap[0] + heightModifier;
        int step = 0;
        int heightmapIndex = 0;

        for (int x = 0; x < chunkLenght; x++) {
            if (x % 20 == 0) {
                heightmapIndex = x / 20;
                if (heightmapIndex != 0) {
                    targetHeight = heightmap[heightmapIndex] + heightModifier;
                }
            }
            if (currentHeight < targetHeight) {
                if (targetHeight - currentHeight > 20 && heightmapIndex == 24) {
                    step = (targetHeight / 20) + 2;
                    currentHeight += step;
                } else {
                    currentHeight++; // Increases current height gradually
                }
            } else if (currentHeight > targetHeight) {
                if (currentHeight - targetHeight > 20 && heightmapIndex == 24) {
                    step = (targetHeight / 20) + 2;
                    currentHeight -= step;
                } else {
                    currentHeight--; // Decreases current height gradually
                }
            }
            // Populate the chunkMap for column x using the current height
            for (int y = 0; y < chunkHeight; y++) {
                if (y >= currentHeight) {
                    chunkMap[y][x] = "T";
                } else {
                    chunkMap[y][x] = "S";
                }
            }
        }
    }

    /**
     * Prints the current heightmap values to standard output.
     */
    public void printtHeightmap() {
        System.out.println(Arrays.toString(heightmap));
    }

    /**
     * A simple test driver for the {@code ChunkMatrixGenerator} class.
     * <p>
     * This main method creates an instance of {@code ChunkMatrixGenerator} with a specified height modifier,
     * then generates a chunk matrix using a sample seed and chunk indices.
     * </p>
     *
     * @param argStrings command-line arguments (not used)
     */
    public static void main(String[] argStrings) {
        ChunkMatrixGenerator sChunkMatrixGenerator = new ChunkMatrixGenerator(50);
        String[][] chunkMatrix = new String[250][500];
        sChunkMatrixGenerator.chunkMatrixCreator(328, 2, 0, 250, 500, chunkMatrix);
    }
}
