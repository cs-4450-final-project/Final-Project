
/** ********************************************************************************
 * File: Chunk.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/26/19
 *
 * Purpose: Represents a chunk, which will hold a number of blocks at once.
 ********************************************************************************* */
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunk {

    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] blocks;
    private int vboVertexHandle;
    private int vboColorHandle;
    private int startX, startY, startZ;
    private Random rand;

    /**
     * Constructor: Creates the array of chunks with a random number of each of
     * the block types. Sets up the glGenBuffers and creates a mesh.
     *
     * @param startX The starting x value.
     * @param startY The starting y value.
     * @param startZ The starting z value.
     */
    public Chunk(int startX, int startY, int startZ) {
        rand = new Random();
        float currFloat = rand.nextFloat();

        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) { //grass, sand, water, dirt, stone, bedrock
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if (currFloat < 0.4f) {
                        blocks[x][y][z] = new Block(Block.BlockType.GRASS);
                    } else if (0.4f <= currFloat && currFloat < 0.5f) {
                        blocks[x][y][z] = new Block(Block.BlockType.SAND);
                    } else if (0.5f <= currFloat && currFloat < 0.6f) {
                        blocks[x][y][z] = new Block(Block.BlockType.WATER);
                    } else if (0.6f <= currFloat && currFloat < 0.7f) {
                        blocks[x][y][z] = new Block(Block.BlockType.DIRT);
                    } else if (0.7f <= currFloat && currFloat < 0.8f) {
                        blocks[x][y][z] = new Block(Block.BlockType.STONE);
                    } else if (0.8f <= currFloat && currFloat < 1) {
                        blocks[x][y][z] = new Block(Block.BlockType.BEDROCK);
                    } else {
                        blocks[x][y][z] = new Block(Block.BlockType.DEFAULT);
                    }
                }
            }
        }
        vboColorHandle = glGenBuffers();
        vboVertexHandle = glGenBuffers();
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }

    /**
     * Method: render() Purpose: Renders the scene.
     */
    public void render() {
        glPushMatrix();
        glPushMatrix(); //TODO: 2 push matrix?

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);

        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        glPopMatrix();
    }

    /**
     * Method: rebuildMesh(float startX, float startY, float startZ) Purpose:
     * Rebuilds the mesh.
     *
     * @param startX The starting x value.
     * @param startY The starting y value.
     * @param startZ The starting z value.
     */
    public void rebuildMesh(float startX, float startY, float startZ) {
        vboColorHandle = glGenBuffers();
        vboVertexHandle = glGenBuffers();

        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);

        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        for (float x = 0; x < CHUNK_SIZE; x++) {
            for (float z = 0; z < CHUNK_SIZE; z++) {
                for (float y = 0; y < CHUNK_SIZE; y++) {
                    VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float) (y * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)), (float) (startZ + z * CUBE_LENGTH)));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(blocks[(int) x][(int) y][(int) z])));
                }
            }
        }
        VertexColorData.flip();
        VertexPositionData.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData,
                GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);

        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Method: createCube(float x, float y, float z) Purpose: Create a cube
     * based on the x, y, and z values with the offset.
     *
     * @param x The x value for the cube.
     * @param y The y value for the cube.
     * @param z The z value for the cube.
     * @return
     */
    private float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[]{
            // TOP QUAD 
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,// RIGHT QUADx + offset, y + offset, z, x + offset, y + offset, z -CUBE_LENGTH, x + offset, y -offset, z -CUBE_LENGTH,x + offset, y -offset, z };
        };
    }

    /**
     * Method: createCubeVertexCol(float[] CubeColorArray) Purpose: Creates a
     * column based on the cube vertex.
     *
     * @param cubeColorArray The array of the cube color to use.
     * @return An array of cube vertex columns.
     */
    private float[] createCubeVertexCol(float[] cubeColorArray) {
        float[] cubeColors = new float[cubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = cubeColorArray[i % cubeColorArray.length];
        }
        return cubeColors;
    }

    /**
     * Method: getCubeColor(Block block) Purpose: Gets the color of the cube.
     *
     * @param block The block to get the color of the cube from.
     * @return The color of the cube as a float[] of rgb values.
     */
    private float[] getCubeColor(Block block) {
        switch (block.getID()) { //grass, sand, water, dirt, stone, bedrock
            case 0: //grass
                return new float[]{0, 1, 0};
            case 1: //sand
                return new float[]{0.1f, 0, 0};
            case 2: //water
                return new float[]{0.1f, 0.1f, 0.1f};
            case 3: //dirt
                return new float[]{0, 0f, 1f};
            case 4: //stone
                return new float[]{1, 0, 1};
            case 5: //bedrock
                return new float[]{1, 1, 1};
            default:
                return new float[]{0, 0, 0};
        }
    }
}
