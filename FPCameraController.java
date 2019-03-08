
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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {

    private Vector3Float position = null;
    private Vector3Float lPosition = null;
    private Vector3Float me = null;
    private float yaw, pitch;

    public FPCameraController(float x, float y, float z) {
        position = new Vector3Float(x, y, z);
        lPosition = new Vector3Float(x, y, z);

        lPosition.setX(0f);
        lPosition.setY(15f);
        lPosition.setZ(0f);
    }

    public void yaw(float amount) {
        yaw += amount;
    }

    public void pitch(float amount) {
        pitch -= amount;
    }

    public void walkFowards(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    public void walkBackwards(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw)));

        position.setX(position.getX() + xOffset);
        position.setZ(position.getZ() - zOffset);
    }

    public void strafeLeft(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw - 90)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw - 90)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    public void strafeRight(float distance) {
        float xOffset = distance * ((float) Math.sin(Math.toRadians(yaw + 90)));
        float zOffset = distance * ((float) Math.cos(Math.toRadians(yaw + 90)));

        position.setX(position.getX() - xOffset);
        position.setZ(position.getZ() + zOffset);
    }

    public void moveUp(float distance) {
        position.setY(position.getY() - distance);
    }

    public void moveDown(float distance) {
        position.setY(position.getY() + distance);
    }

    public void lookThrough() {
        //rotate pitch about x
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);

        //rotate yaw about y
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);

        //translate to position vector's location
        glTranslatef(position.getX(), position.getY(), position.getZ());

    }

}
