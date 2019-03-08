/** ********************************************************************************
 * File: Polygon.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/8/19
 *
 * Purpose: Represents a 2D shape. Holds it's color values and vector points.
 ********************************************************************************* */

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class Polygon {

    private ArrayList<Vector3f> vertexPoints;
    private float red, green, blue;

    Polygon() {
        this(1, 0, 0);
    }

    Polygon(float red, float green, float blue) {
        this(red, green, blue, new ArrayList<Vector3f>());
    }

    Polygon(float red, float green, float blue, ArrayList<Vector3f> vertexPoints) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.vertexPoints = vertexPoints;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public ArrayList<Vector3f> getPoints() {
        return vertexPoints;
    }

    public void setPoints(ArrayList<Vector3f> vertexPoints) {
        this.vertexPoints = vertexPoints;
    }

    public void addPoint(Vector3f v) {
        vertexPoints.add(v);
    }

}
