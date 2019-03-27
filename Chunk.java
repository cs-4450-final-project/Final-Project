
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
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {

    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] blocks;
    private int vboVertexHandle;
    private int vboColorHandle;
    private int startX, startY, startZ;
    private Random rand;

    private int vboTextureHandle;
    private Texture texture;

    /**
     * Constructor: Creates the array of chunks with a random number of each of
     * the block types. Sets up the glGenBuffers and creates a mesh.
     *
     * @param startX The starting x value.
     * @param startY The starting y value.
     * @param startZ The starting z value.
     */
    public Chunk(int startX, int startY, int startZ) {
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("terrain3.png"));
            System.out.println("Texture loaded!");
        } catch (IOException e) {
            System.out.print("Texture cannot be found!");
        }

        rand = new Random();
        float currFloat;

        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

        for (int x = 0; x < CHUNK_SIZE; x++) { //grass, sand, water, dirt, stone, bedrock
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    currFloat = rand.nextFloat();
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
        vboTextureHandle = glGenBuffers(); //along with our other VBOs

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
//        glPushMatrix(); //TODO: 2 push matrix?

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);

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

        vboTextureHandle = glGenBuffers();

        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer vertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer vertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);

        for (float x = 0; x < CHUNK_SIZE; x++) {
            for (float z = 0; z < CHUNK_SIZE; z++) {
                for (float y = 0; y < CHUNK_SIZE; y++) {
                    vertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float) (y * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)), (float) (startZ + z * CUBE_LENGTH)));
                    vertexColorData.put(createCubeVertexCol(getCubeColor(blocks[(int) x][(int) y][(int) z])));
                    vertexTextureData.put(createTexCube((float) 0, (float) 0, blocks[(int) (x)][(int) (y)][(int) (z)]));
                }
            }
        }
        vertexColorData.flip();
        vertexPositionData.flip();
        vertexTextureData.flip();

        //for vertices
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexPositionData,
                GL_STATIC_DRAW);

        //for colors
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);

        glBufferData(GL_ARRAY_BUFFER, vertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        //for textures
        glBindBuffer(GL_ARRAY_BUFFER, vboTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexTextureData,
                GL_STATIC_DRAW);
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
        return new float[]{1, 1, 1};
    }

    /**
     * Method: createTexCube(float x, float y, Block block)
     * Purpose: Applies the texture to the cube based on what block type it is.
     * @param x The x value of the cube.
     * @param y The y value of the cube.
     * @param block The type of block it is.
     * @return An array of coordinates needed to texture the cube.
     */
    private float[] createTexCube(float x, float y, Block block) {
        float offset = (512f / 16) / 512f;
        
        switch (block.getID()) {
            case 0: //grass
                return grassTexture(x, y, offset);
            case 1:
                return sandTexture(x, y, offset);
            case 2:
                return waterTexture(x, y, offset);
            case 3:
                return dirtTexture(x, y, offset);
            case 4:
                return stoneTexture(x, y, offset);
            case 5:
                return bedrockTexture(x, y, offset);
            default:
                return defaultTexture(x, y, offset);
        }
    }

    /**
     * Method: grassTexture(float x, float y, float offset) 
     * Purpose: Apply the grass texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] grassTexture(float x, float y, float offset) {
        return new float[]{ 
            // BOTTOM QUAD(DOWN=+Y) x: 2,3 y: 0,1
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            // TOP! x: 0,1 y: 0,1 OK
            x + offset * 1, y + offset * 1,
            x + offset * 0, y + offset * 1,
            x + offset * 0, y + offset * 0,
            x + offset * 1, y + offset * 0,
            // FRONT QUAD x: 3,4 y: 0,1
            x + offset * 3, y + offset * 0,
            x + offset * 4, y + offset * 0,
            x + offset * 4, y + offset * 1,
            x + offset * 3, y + offset * 1,
            // BACK QUAD x: 3,4 y: 0,1
            x + offset * 4, y + offset * 1,
            x + offset * 3, y + offset * 1,
            x + offset * 3, y + offset * 0,
            x + offset * 4, y + offset * 0,
            // LEFT QUAD x: 3,4 y: 0,1
            x + offset * 3, y + offset * 0,
            x + offset * 4, y + offset * 0,
            x + offset * 4, y + offset * 1,
            x + offset * 3, y + offset * 1,
            // RIGHT QUAD x: 3,4 y: 0,1
            x + offset * 3, y + offset * 0,
            x + offset * 4, y + offset * 0,
            x + offset * 4, y + offset * 1,
            x + offset * 3, y + offset * 1};
    }

    /**
     * Method: sandTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] sandTexture(float x, float y, float offset) { //OK
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y) x: 2,3 y: 1,2
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2,
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            // TOP!
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2,
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            // FRONT QUAD
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2,
            // BACK QUAD
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2,
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            // LEFT QUAD
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2,
            // RIGHT QUAD
            x + offset * 2, y + offset * 1,
            x + offset * 3, y + offset * 1,
            x + offset * 3, y + offset * 2,
            x + offset * 2, y + offset * 2};
    }

    /**
     * Method: waterTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] waterTexture(float x, float y, float offset) {
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y) x: 3,4 y:4,5
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5,
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            // TOP!
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5,
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            // FRONT QUAD
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5,
            // BACK QUAD
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5,
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            // LEFT QUAD
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5,
            // RIGHT QUAD
            x + offset * 3, y + offset * 4,
            x + offset * 4, y + offset * 4,
            x + offset * 4, y + offset * 5,
            x + offset * 3, y + offset * 5};
    }

    /**
     * Method: dirtTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] dirtTexture(float x, float y, float offset) {
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y) x: 2,3 y: 0,1
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            // TOP!
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            // FRONT QUAD
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            // BACK QUAD
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            // LEFT QUAD
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1,
            // RIGHT QUAD
            x + offset * 2, y + offset * 0,
            x + offset * 3, y + offset * 0,
            x + offset * 3, y + offset * 1,
            x + offset * 2, y + offset * 1};
    }

    /**
     * Method: stoneTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] stoneTexture(float x, float y, float offset) {
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y) x: 1,2 y: 0,1
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1,
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            // TOP!
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1,
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            // FRONT QUAD
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1,
            // BACK QUAD
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1,
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            // LEFT QUAD
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1,
            // RIGHT QUAD
            x + offset * 1, y + offset * 0,
            x + offset * 2, y + offset * 0,
            x + offset * 2, y + offset * 1,
            x + offset * 1, y + offset * 1};
    }

    /**
     * Method: bedrockTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] bedrockTexture(float x, float y, float offset) {
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y) x: 1,2 y: 1,2
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2,
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            // TOP!
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2,
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            // FRONT QUAD
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2,
            // BACK QUAD
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2,
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            // LEFT QUAD
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2,
            // RIGHT QUAD
            x + offset * 1, y + offset * 1,
            x + offset * 2, y + offset * 1,
            x + offset * 2, y + offset * 2,
            x + offset * 1, y + offset * 2};
    }

    /**
     * Method: defaultTexture(float x, float y, float offset) 
     * Purpose: Apply the texture to the block.
     * @param x The x value on the texture to apply to the block.
     * @param y The y value on the texture to apply to the block.
     * @param offset The offset to get the whole texture from.
     * @return An array of the coordinates of the texture to apply to the block.
     */
    private float[] defaultTexture(float x, float y, float offset) {
        return new float[]{
            // BOTTOM QUAD(DOWN=+Y)x: 1,2 y: 3,4
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4,
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            // TOP!
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4,
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            // FRONT QUAD
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4,
            // BACK QUAD
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4,
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            // LEFT QUAD
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4,
            // RIGHT QUAD
            x + offset * 1, y + offset * 3,
            x + offset * 2, y + offset * 3,
            x + offset * 2, y + offset * 4,
            x + offset * 1, y + offset * 4};
    }
}
