
/** ********************************************************************************
 * File: Polyhedron.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/8/19
 *
 * Purpose: Represents a 2D shape. Holds it's color values and vector points.
 ********************************************************************************* */

import java.util.ArrayList;

public class Polyhedron {

    private ArrayList<Polygon> sides;

    Polyhedron() {
        this(new ArrayList<Polygon>());
    }

    Polyhedron(ArrayList<Polygon> sides) {
        this.sides = sides;
    }

    public ArrayList<Polygon> getSides() {
        return sides;
    }

    public void setSides(ArrayList<Polygon> sides) {
        this.sides = sides;
    }

    public void addSide(Polygon polygon) {
        sides.add(polygon);
    }
}
