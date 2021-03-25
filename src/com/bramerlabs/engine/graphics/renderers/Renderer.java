package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.untextured.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    // the window to render to
    private final Window window;

    // the position of the light
    private final Vector3f lightPosition;

    // the light color
    private Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

    /**
     * default constructor
     * @param window - the specified window to render to
     */
    public Renderer(Window window, Vector3f lightPosition) {
        this.window = window;
        this.lightPosition = lightPosition;
    }

    /**
     * renders the mesh
     * @param object - the object to be rendered
     * @param camera - the camera perspective
     */
    public void renderMesh(RenderObject object, Camera camera, Shader shader) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    /**
     * sets the light color
     * @param lightColor - the new color
     */
    public void setLightColor(Vector3f lightColor) {
        this.lightColor = lightColor;
    }

    /**
     * getter method
     * @return - the light color
     */
    public Vector3f getLightColor() {
        return this.lightColor;
    }
}