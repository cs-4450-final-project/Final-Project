
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
public class Polygon {
    private ArrayList<Vector3Float> vertexPoints;
    private float red, green, blue;
    Polygon(){
        
    }
    Polygon(float r, float g, float b){
        red = r;
        green = g;
        blue = b;
        vertexPoints = new ArrayList<>();
    }
    Polygon(float r, float g, float b, ArrayList<Vector3Float> vp){
        red = r;
        green = g;
        blue = b;
        vertexPoints = vp;
    }
    public float getRed(){
        return red;
    }
    public float getGreen(){
        return green;
    }
    public float getBlue(){
        return blue;
    }
    public void setRed(float r){
        red = r;
    }
    public void setGreen(float g){
        green = g;
    }
    public void setBlue(float b){
        blue = b;
    }
    public ArrayList<Vector3Float> getPoints(){
        return vertexPoints;
    }
    public void setPoints(ArrayList<Vector3Float> vp){
        vertexPoints = vp;
    }
    public void addPoint(Vector3Float v){
        vertexPoints.add(v);
    }
    
}
