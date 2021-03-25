package com.bramerlabs.terra_nova.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.engine.objects.untextured.shapes.Cube;
import com.bramerlabs.engine.objects.untextured.shapes.Cylinder;
import com.bramerlabs.terra_nova.main.objects.Tree;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    // the object to inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    // shaders to use
    private Shader defaultShader, lightShader;

    // renderers to use
    private Renderer renderer;

    // the position of light
    Vector3f lightPosition = new Vector3f(1.0f, 2.0f, 3.0f);

    // test objects
    private Cube lightCube;
    private Tree tree;

    /**
     * the main runnable method
     * @param args - jvm arguments
     */
    public static void main(String[] args) {
        // start the main thread
        new Main().start();
    }

    public void start() {
        // initialize the new thread
        Thread main = new Thread(this, "Terra Nova");

        // start the thread
        main.start();
    }

    public void run() {
        // initialize the program
        init();

        // application run loop
        while (!window.shouldClose()) {
            update();
            render();
        }

        // clean up
        close();
    }

    private void init() {
        // initialize the window
        Window.setBackgroundColor(Vector3f.divide(new Vector3f(204, 232, 220), new Vector3f(255)));
        window.create();

        // initialize shaders
        defaultShader = new Shader( // the main shader
                "/shaders/default/vertex.glsl",
                "/shaders/default/fragment.glsl").create();
        lightShader = new Shader(
                "/shaders/light/vertex.glsl",
                "/shaders/light/fragment.glsl").create();

        // initialize renderers
        renderer = new Renderer(window, lightPosition);

        // initialize the camera
        camera = new Camera(new Vector3f(0), new Vector3f(0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        lightCube = new Cube(lightPosition, new Vector3f(0), new Vector3f(0.5f), new Vector4f(1.0f));
        lightCube.createMesh();

        tree = Tree.getInstance(61361311);
//        tree = Tree.getInstance(200);
        tree.createMesh();

    }

    private void update() {
        // update the window
        window.update();

        // clear the screen
        GL46.glClearColor(Window.r, Window.g, Window.b, 1.0f);

        // clear the mask bits
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // update game objects

        // update the camera
        camera.updateArcball();
    }

    private void render() {
        // render the objects
        renderer.renderMesh(lightCube, camera, lightShader);
        renderer.renderMesh(tree, camera, defaultShader);

        // swap buffers at the end
        window.swapBuffers();
    }

    private void close() {
        // release the window
        window.destroy();

        // release the objects
        lightCube.destroy();

        // release the shaders
        defaultShader.destroy();
    }
}