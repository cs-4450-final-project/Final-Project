
/** ********************************************************************************
 * File: DisplayWindow3D.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/7/19
 *
 * Purpose: To display the screen.
 ********************************************************************************* */
import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import org.lwjgl.util.glu.GLU;

public class DisplayWindow3D {

    private FPCameraController camera;
    private DisplayMode displayMode;
    private ArrayList<Polyhedron> shapes;

    private enum Side {
        TOP, BOTTOM, LEFT, RIGHT, BACK, FRONT;
    }

    public DisplayWindow3D() {
        camera = new FPCameraController(0f, 0f, 0f);
        shapes = new ArrayList<>();

        initializeObjects();
    }
    
    
    private void initializeObjects() {
        drawCube(0, 0, 0, 0.5f);
        drawCube(2, 1, 1, 0.5f);
    }

    private void createSide(Vector3Float location, Polyhedron newCube, float sideLength, float r, float b, float g, Side side) {
        Polygon newSide = new Polygon(r, b, g);
        float x = location.getX();
        float y = location.getY();
        float z = location.getZ();

        if (null != side) {
            switch (side) {
                case TOP:
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z + sideLength));
                    newCube.addSide(newSide);
                    break;
                case BOTTOM:
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z - sideLength));
                    break;
                case FRONT:
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z + sideLength));
                    break;
                case BACK:
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z - sideLength));
                    break;
                case LEFT:
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y + sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x - sideLength, y - sideLength, z + sideLength));
                    break;
                case RIGHT:
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z - sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y + sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z + sideLength));
                    newSide.addPoint(new Vector3Float(x + sideLength, y - sideLength, z - sideLength));
                    break;
                default:
                    break;
            }
        }
        newCube.addSide(newSide);
    }

    private void drawCube(float x, float y, float z, float sideLength) {
        Polyhedron newCube = new Polyhedron();
        Vector3Float location = new Vector3Float(x, y, z);

        createSide(location, newCube, sideLength, 1f, 0f, 0f, Side.TOP);
        createSide(location, newCube, sideLength, 0f, 1f, 0f, Side.BOTTOM);
        createSide(location, newCube, sideLength, 1f, 0f, 1f, Side.LEFT);
        createSide(location, newCube, sideLength, 1f, 1f, 0f, Side.RIGHT);
        createSide(location, newCube, sideLength, 1f, 0f, 1f, Side.FRONT);
        createSide(location, newCube, sideLength, 0f, 1f, 1f, Side.BACK);

        shapes.add(newCube);
    }

    /**
     * Method: start() Purpose: To create, initialize GL, and render the window
     * of the display.
     */
    public void start() {
        try {
            createWindow();
            initGL();
            gameLoop();
        } catch (Exception e) {
            System.out.println("Window creation failed!");
        }
    }

    /**
     * Method: createWindow() Purpose: It creates the window and its title.
     * Default size for program 1 is 640x480.
     *
     * @throws Exception
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);

        DisplayMode d[] = Display.getAvailableDisplayModes();

        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }

        Display.setDisplayMode(displayMode);
        Display.setTitle("Final Project - Team Hippity Hoppity");
        Display.create();
    }

    /**
     * Method: initGL() Purpose: Starts up and uses GL for the background of the
     * display.
     */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void gameLoop() {
        camera = new FPCameraController(0f, 0f, 0f);
        float dx, dy, dt, lastTime;
        long time;
        float mouseXZSens = 0.10f;
        float mouseYSens = 0.10f;
        float movementSpeed = 0.35f;

        Mouse.setGrabbed(true);
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;

            dx = Mouse.getDX(); //get dis of mouse movement for x & y
            dy = Mouse.getDY();

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.walkFowards(movementSpeed);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.strafeLeft(movementSpeed);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.walkBackwards(movementSpeed);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                camera.moveUp(movementSpeed);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                camera.moveDown(movementSpeed);
            }
            camera.yaw(dx * mouseXZSens);
            camera.pitch(dy * mouseYSens);
            glLoadIdentity();

            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //draw here
            render();

            //Draw buffer to screen
            Display.update();
            Display.sync(60);

        }
        Display.destroy();
    }

    /**
     * Method: render() Purpose: Renders the objects for the display.
     */
    private void drawPolyhedron(Polyhedron p) {
        ArrayList<Polygon> sides = p.getSides();
        for (Iterator<Polygon> iterator = sides.iterator(); iterator.hasNext();) {
            drawPolygon(iterator.next());
        }
    }

    private void drawPolygon(Polygon p) {
        ArrayList<Vector3Float> s = p.getPoints();
        glColor3f(p.getRed(), p.getGreen(), p.getBlue());
        glBegin(GL_POLYGON);
        for (Iterator<Vector3Float> iterator = s.iterator(); iterator.hasNext();) { //draw each polygon, then fill it appropriately. Changes the color before drawing and doesn't change it until the next polygon
            Vector3Float f = iterator.next();
            glVertex3f(f.getX(), f.getY(), f.getZ());
        }
        glEnd();
    }

    private void render() {
        try {

            for (Iterator<Polyhedron> iterator = shapes.iterator(); iterator.hasNext();) {
                drawPolyhedron(iterator.next());
            }

        } catch (Exception e) {

        }
    }

}
