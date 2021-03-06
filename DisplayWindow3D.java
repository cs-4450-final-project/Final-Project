
/** ********************************************************************************
 * File: DisplayWindow3D.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 4/29/19
 *
 * Purpose: To display the screen and its objects.
 ********************************************************************************* */
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

public class DisplayWindow3D {

    private FPCameraController camera;
    private DisplayMode displayMode;
    private Chunk chunk;
    private FloatBuffer lightPosition0, lightPosition1, lightDirection1;
    private FloatBuffer whiteLight, rainbowLight;
    private float timer, timerLSD;

    /**
     * Constructor: DisplayWindow3D() Purpose: Initializes the camera, list of
     * shapes, and initializes the objects to be drawn.
     */
    public DisplayWindow3D() {
        camera = new FPCameraController(0f, -130f, 0f);
    }

    /**
     * Method: start() : To create, initialize GL, and render the window of the
     * display.
     */
    public void start() {
        try {
            createWindow();
            initGL();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
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

        for (DisplayMode d1 : d) {
            if (d1.getWidth() == 640 && d1.getHeight() == 480 && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
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
        timer = 0;
        timerLSD = 0;
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //GLU.gluPerspective(100.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnable(GL_LIGHTING);//enables our lighting
        glShadeModel(GL_SMOOTH);
        initLightArrays();
    }

    /**
     * Method: initLightArrays() Purpose: Initializes the light arrays.
     */
    private void initLightArrays() {
        lightPosition0 = BufferUtils.createFloatBuffer(4);
        lightPosition0.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        lightPosition1 = BufferUtils.createFloatBuffer(4);
        lightPosition1.put(60.0f).put(-160.0f).put(0.0f).put(0.0f).flip();
        lightDirection1 = BufferUtils.createFloatBuffer(4);
        lightDirection1.put(-60.0f).put(-160.0f).put(0.0f).put(0.0f).flip();
    }

    private void updateLights() {
        timer++;
        rainbowLight = BufferUtils.createFloatBuffer(4);
        rainbowLight.put((float) (0.5 + .5 * Math.cos(Math.toRadians(timer)))).put((float) (0.5 + .5 * Math.cos(Math.toRadians(timer * 3)))).put((float) (0.5 + .5 * Math.cos(Math.toRadians(timer * 5)))).put(0.0f).flip();

        lightPosition1 = BufferUtils.createFloatBuffer(4);
        lightPosition1.put((float) (240.0 * Math.cos(Math.toRadians(timer)))).put(-160.0f).put((float) (240.0 * Math.sin(Math.toRadians(timer)))).put(0.0f).flip();
        lightDirection1 = BufferUtils.createFloatBuffer(4);
        lightDirection1.put((float) (-Math.cos(Math.toRadians(timer)))).put(0).put((float) (-Math.sin(Math.toRadians(timer)))).put(0.0f).flip();
    }

    /**
     * Method: gameLoop() Purpose: For the camera controls. Use WASD to move,
     * crouch to go down, space to go up. Press right click to change FOV. Press
     * TAB to reload the chunk.
     */
    private void gameLoop() {
        chunk = new Chunk(-60, 30, -60);

        float dx, dy;
        float mouseXZSens = 0.10f;
        float mouseYSens = 0.10f;
        float movementSpeed = 0.35f;

        Mouse.setGrabbed(true);
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
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

            if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
                chunk = new Chunk(-60, 30, -60);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                camera.moveDown(movementSpeed);
            }
            if (Mouse.isButtonDown(GL_ONE)) {
                timerLSD++;
            }
            camera.yaw(dx * mouseXZSens);
            camera.pitch(dy * mouseYSens);
            updateLights();
            glLight(GL_LIGHT0, GL_POSITION, lightPosition0); //sets our light’s position
            glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
            glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
            glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
            glEnable(GL_LIGHT0);//enables light0

            glLight(GL_LIGHT1, GL_POSITION, lightPosition1); //sets our light’s position
            glLight(GL_LIGHT1, GL_SPECULAR, rainbowLight);//sets our specular light
            glLight(GL_LIGHT1, GL_DIFFUSE, rainbowLight);//sets our diffuse light
            glLight(GL_LIGHT1, GL_AMBIENT, rainbowLight);//sets our ambient light
            glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 45.0f);
            glLight(GL_LIGHT1, GL_SPOT_DIRECTION, lightDirection1);

            glEnable(GL_LIGHT1);

            glLoadIdentity();

            GLU.gluPerspective(105.0f - 45.0f * (float) Math.cos(Math.toRadians(timerLSD)), (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 300.0f);

            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //draw here
            chunk.render();

            //Draw buffer to screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
}
