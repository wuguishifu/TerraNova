package com.bramerlabs.terra_nova.main;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.TextureRenderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.engine.objects.textured.test.Box;
import com.bramerlabs.engine.objects.untextured.shapes.Cube;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    // the object to inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    // shaders to use
    private Shader defaultShader, lightShader, textureShader;

    // renderers to use
    private Renderer renderer;
    private TextureRenderer textureRenderer;

    // the position of light
    Vector3f lightPosition = new Vector3f(1.0f, 2.0f, 3.0f);

    // test objects
    private Cube cube;
    private Cube lightCube;
    private Box box;

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
        window.create();

        // initialize shaders
        defaultShader = new Shader( // the main shader
                "/shaders/default/vertex.glsl",
                "/shaders/default/fragment.glsl").create();
        lightShader = new Shader(
                "/shaders/light/vertex.glsl",
                "/shaders/light/fragment.glsl").create();
        textureShader = new Shader(
                "/shaders/texture/vertex.glsl",
                "/shaders/texture/fragment.glsl").create();

        // initialize renderers
        renderer = new Renderer(window, lightPosition);
        textureRenderer = new TextureRenderer(window, lightPosition);

        // initialize the camera
        camera = new Camera(new Vector3f(0), new Vector3f(0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // initialize objects
        cube = new Cube(new Vector3f(0), new Vector3f(0), new Vector3f(1), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
        cube.createMesh();

        lightCube = new Cube(lightPosition, new Vector3f(0), new Vector3f(0.5f), new Vector4f(1.0f));
        lightCube.createMesh();

        box = new Box(new Vector3f(0), new Vector3f(0), new Vector3f(0.5f), "textures/box/container2_512x512.png");
        box.createMesh();

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
//        renderer.renderMesh(cube, camera, defaultShader);
        renderer.renderMesh(lightCube, camera, lightShader);
        textureRenderer.renderMesh(box, camera, textureShader);

        // swap buffers at the end
        window.swapBuffers();
    }

    private void close() {
        // release the window
        window.destroy();

        // release the objects
        cube.destroy();
        lightCube.destroy();

        // release the shaders
        defaultShader.destroy();
    }
}