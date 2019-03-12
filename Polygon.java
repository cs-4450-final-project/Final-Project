
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

    /**
     * Constructor: Polygon()
     * Purpose: Sets the polygon to be red with no points.
     */
    Polygon() {
        this(1, 0, 0);
    }

    /**
     * Constructor: Polygon(float red, float green, float blue)
     * Purpose: Sets the polygon to be a color with no points.
     * @param red The r value of the color.
     * @param green The g value of the color.
     * @param blue The b value of the color.
     */
    Polygon(float red, float green, float blue) {
        this(red, green, blue, new ArrayList<Vector3f>());
    }

    /**
     * Constructor: Polygon(float red, float green, float blue, ArrayList<Vector3f> vertexPoints)
     * Purpose: Sets the polygon to be a color with a set amount of points.
     * @param red The r value of the color.
     * @param green The g value of the color.
     * @param blue The b value of the color.
     * @param vertexPoints A list of points that represent the shape's vertices.
     */

    Polygon(float red, float green, float blue, ArrayList<Vector3f> vertexPoints) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.vertexPoints = vertexPoints;
    }

    /**
     * Method: getRed()
     * Purpose: Gets the red value.
     * @return The red value as a float.
     */
    public float getRed() {
        return red;
    }

    /**
     * Method: getGreen()
     * Purpose: Gets the green value.
     * @return The green value as a float.
     */
    public float getGreen() {
        return green;
    }

    /**
     * Method: getBlue()
     * Purpose: Gets the blue value.
     * @return The blue value as a float.
     */
    public float getBlue() {
        return blue;
    }

    /**
     * Method: setRed(float red)
     * Purpose: Sets the red value.
     * @param red The value to set the red value to.
     */
    public void setRed(float red) {
        this.red = red;
    }

    /**
     * Method: setGreen(float red)
     * Purpose: Sets the green value.
     * @param green The value to set the green value to.
     */
    public void setGreen(float green) {
        this.green = green;
    }

    /**
     * Method: setBlue(float red)
     * Purpose: Sets the blue value.
     * @param blue The value to set the blue value to.
     */
    public void setBlue(float blue) {
        this.blue = blue;
    }

    /**
     * Method: getPoints()
     * Purpose: Get the points of the shape as a list of vertices.
     * @return An array list of points.
     */
    public ArrayList<Vector3f> getPoints() {
        return vertexPoints;
    }

    /**
     * Method: setPoints(ArrayList<Vector3f> vertexPoints)
     * Purpose: Sets the vertices to a new list of vertices.
     * @param vertexPoints The list of vertices to set the current one to.
     */

    public void setPoints(ArrayList<Vector3f> vertexPoints) {
        this.vertexPoints = vertexPoints;
    }

    /**
     * Method: addPoint(Vector3f v)
     * Purpose: Adds a point to the list of vertices.
     * @param vertex The new vertex to add to the list.
     */
    public void addPoint(Vector3f vertex) {
        vertexPoints.add(vertex);
    }

}
