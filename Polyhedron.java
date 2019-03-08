
/** ********************************************************************************
 * File: Polyhedron.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/8/19
 *
 * Purpose: Represents a 3D shape. Holds it's color values and sides.
 ********************************************************************************* */

import java.util.ArrayList;

public class Polyhedron {

    private ArrayList<Polygon> sides;

    /**
     * Method: Polyhedron()
     * Purpose: Default that makes a blank polyhedron with no sides.
     */
    Polyhedron() {
        this(new ArrayList<Polygon>());
    }

    /**
     * Method: Polyhedron(ArrayList<Polygon> sides)
     * Purpose: Makes a new polyhedron with specific sides.
     */
    Polyhedron(ArrayList<Polygon> sides) {
        this.sides = sides;
    }

    /**
     * Method: getSides()
     * Purpose: Gets the list of sides of this polyhedron.
     * @return A list of the sides.
     */
    public ArrayList<Polygon> getSides() {
        return sides;
    }

    /**
     * Method: setSides(ArrayList<Polygon> sides)
     * Purpose: Sets the list of sides to a give list.
     * @param sides The list of sides to set the polyhedron's sides.
     */
    public void setSides(ArrayList<Polygon> sides) {
        this.sides = sides;
    }

    /**
     * Method: addSide(Polygon polygon)
     * Purpose: Add a side to the polyhedron.
     * @param polygon 
     */
    public void addSide(Polygon polygon) {
        sides.add(polygon);
    }
}
