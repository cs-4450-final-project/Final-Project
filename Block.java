
/** ********************************************************************************
 * File: Block.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/26/19
 *
 * Purpose: Represents a single block. Holds its type, state, and location.
 ********************************************************************************* */
public class Block {

    private boolean isActive;
    private BlockType blockType;
    private float x, y, z;

    /**
     * Constructor: Block(BlockType blockType) Purpose: Create a new block of
     * block type.
     *
     * @param blockType The type of block to be created.
     */
    public Block(BlockType blockType) {
        this.blockType = blockType;

        if (blockType != null) {
            switch (blockType) {
                case GRASS:
                    blockType.setBlockID(0);
                    break;
                case SAND:
                    blockType.setBlockID(1);
                    break;
                case WATER:
                    blockType.setBlockID(2);
                    break;
                case DIRT:
                    blockType.setBlockID(3);
                    break;
                case STONE:
                    blockType.setBlockID(4);
                    break;
                case BEDROCK:
                    blockType.setBlockID(5);
                    break;
                case DEFAULT:
                    blockType.setBlockID(-1);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Enum: BlockType Purpose: Specify the type of the block. Also contains its
     * block id;
     */
    public enum BlockType {
        GRASS, SAND, DIRT, STONE, WATER, BEDROCK, DEFAULT;

        private int blockID;

        public int getBlockID() {
            return blockID;
        }

        public void setBlockID(int blockID) {
            this.blockID = blockID;
        }

        public BlockType getType(int id) {
            switch (id) {
                case 0:
                    return GRASS;
                case 1:
                    return SAND;
                case 2:
                    return WATER;
                case 3:
                    return DIRT;
                case 4:
                    return STONE;
                case 5:
                    return BEDROCK;
                default:
                    return DEFAULT;
            }
        }
    }

    /**
     * Method: setCoords(x, y, z) Purpose: Set the x, y, and z values of the
     * block based on its offset of the chunk.
     *
     * @param x The x offset of the block.
     * @param y The y offset of the block.
     * @param z The z offset of the block.
     */
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Method: getCoods() Returns the x, y, and z values as a float[].
     *
     * @return A float array of the {x, y, z} values.
     */
    public float[] getCoords() {
        return new float[]{x, y, z};
    }

    /**
     * Method: isActive() Purpose: Checks if the block is active. If not, it's
     * not rendered.
     *
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method: setActive(boolean isActive) Purpose: Sets the block to either
     * active or inactive.
     *
     * @param isActive The boolean to set the state of the block.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Method: getID() Purpose: Returns the id of the block given from its block
     * type.
     *
     * @return
     */
    public int getID() {
        return blockType.getBlockID();
    }
}
