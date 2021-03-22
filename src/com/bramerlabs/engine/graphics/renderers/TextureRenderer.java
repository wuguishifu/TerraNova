package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.untextured.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class TextureRenderer {

    // the window to render to
    private final Window window;

    // the position of the light
    private final Vector3f lightPosition;

    // the light color
    private final Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

    /**
     * default constructor
     * @param window - the window to render to
     * @param lightPosition - the position of the light source
     */
    public TextureRenderer(Window window, Vector3f lightPosition) {
        this.window = window;
        this.lightPosition = lightPosition;
    }

    /**
     * renders an object
     * @param object - the object to render
     * @param camera - the camera position
     * @param shader - the shader to use to render
     */
    public void renderMesh(RenderObject object, Camera camera, Shader shader) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.2f);
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

}
