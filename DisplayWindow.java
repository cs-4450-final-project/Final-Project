/** ********************************************************************************
 * File: DisplayWindow.java
 * Authors: Chloe Mei Stabinsky, Hunter Swanson, Brian Cho
 * Class: CS 4450 - Computer Graphics
 *
 * Assignment: Final Project
 * Date last modified: 3/4/19
 *
 * Purpose: To display the screen.
 ********************************************************************************* */

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;

public class DisplayWindow {
    public DisplayWindow() {
    }
    
    /**
     * Method: start()
     * Purpose: To create, initialize GL, and render the window of
     * the display.
     */
    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            System.out.println("Window creation failed!");
        }
    }

    /**
     * Method: createWindow()
     * Purpose: It creates the window and its title.
     * Default size for program 1 is 640x480.
     *
     * @throws Exception
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);

        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Final Project - Team Hippity Hoppity");
        Display.create();
    }

    /**
     * Method: initGL()
     * Purpose: Starts up and uses GL for the background of the
     * display.
     */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0.0, 640.0, 0.0, 480.0, 1.0, -1.0);

        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    /**
     * Method: render()
     * Purpose: Renders the objects for the display. 
     * NOTE: Use keys 1, 2, and 3 to change the color of the shapes!
     */
    private void render() {
        while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glPointSize(1);

                glBegin(GL_POINTS);


                glEnd();

                Display.update();
                Display.sync(60);

            } catch (Exception e) {

            }
        }
        Display.destroy();
    }
}
