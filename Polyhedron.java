
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jikemsa
 */
public class Polyhedron {
    private ArrayList<Polygon> sides;
    Polyhedron(){
        sides = new ArrayList<>();
    }
    Polyhedron(ArrayList<Polygon> s){
        sides = s;
    }
    public ArrayList<Polygon> getSides(){
        return sides;
    }
    public void setSides(ArrayList<Polygon> s){
        sides = s;
    }
    public void addSide(Polygon p){
        sides.add(p);
    }
}
