
/** ********************************************************************************
 * File: CameraPosition.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/7/19
 *
 * Purpose: A class that holds 3 floats that represents a camera's x, y, and z values.
 * Same as the Vector3Float class from the slides.
 ********************************************************************************* */
public class Vector3Float {

    private float x, y, z;

    public Vector3Float(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

}
