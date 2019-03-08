/** ********************************************************************************
 * File: FPCameraController.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/8/19
 *
 * Purpose: Controls the camera in a first person space.
 ********************************************************************************* */

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class FPCameraController {

    private Vector3f position = null;
    private Vector3f lPosition = null;
    private Vector3f me = null;
    private float yaw, pitch;

    /**
     * Constructor: FPCameraController(float x, float y, float z)
     * Purpose: Make a new camera at a specific location.
     * @param x The x of the location.
     * @param y The y of the location.
     * @param z The z of the location.
     */
    public FPCameraController(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);

        lPosition.setX(0f);
        lPosition.setY(15f);
        lPosition.setZ(0f);
    }

    /**
     * Method: yaw(float amount)
     * Purpose: Alter the yaw by an amount.
     * @param amount The amount to adjust yaw by.
     */
    public void yaw(float amount) {
        yaw += amount;
    }

    /**
     * Method: pitch(float amount)
     * Purpose: Alter the pitch by an amount.
     * @param amount The amount to adjust pitch by.
     */
    public void pitch(float amount) {
        pitch -= amount;
    }

    /**
     * Method: walkForwards(float distance)
     * Purpose: Walk forwards a distance.
     * @param distance The distance to walk.
     */
    public void walkFowards(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    /**
     * Method: walkBackwards(float distance)
     * Purpose: Walk backwards a distance.
     * @param distance The distance to walk.
     */
    public void walkBackwards(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw)));

        position.setX(position.getX() + xOffset);
        position.setZ(position.getZ() - zOffset);
    }

    /**
     * Method: strafeLeft(float distance)
     * Purpose: Walk sideways left a distance.
     * @param distance The distance to strafe.
     */
    public void strafeLeft(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw - 90)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw - 90)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    /**
     * Method: strafeRight(float distance)
     * Purpose: Walk sideways right a distance.
     * @param distance The distance to strafe.
     */
    public void strafeRight(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw + 90)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw + 90)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    /**
     * Method: moveUp(float distance)
     * Purpose: Move up a distance.
     * @param distance The distance to move up.
     */
    public void moveUp(float distance) {
        position.setY(position.getY() - distance);
    }

    /**
     * Method: moveDown(float distance)
     * Purpose: Move down a distance.
     * @param distance The distance to move down.
     */
    public void moveDown(float distance) {
        position.setY(position.getY() + distance);
    }

    /**
     * Method: lookThrough()
     * Purpose: The alter the looking camera by its yaw, pitch, and position.
     */
    public void lookThrough() {
        //rotate pitch about x
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);

        //rotate yaw about y
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);

        //translate to position vector's location
        glTranslatef(position.getX(), position.getY(), position.getZ());

    }
    
}
