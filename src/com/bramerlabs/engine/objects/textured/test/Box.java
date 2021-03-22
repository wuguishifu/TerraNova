package com.bramerlabs.engine.objects.textured.test;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.untextured.RenderObject;

public class Box extends RenderObject {

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Box(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    /**
     * default constructor
     *
     * @param position - the position of the box
     * @param rotation - the rotation of the box
     * @param scale - the scale of this object
     * @param pathToTexture - the path to the texture this object is made of
     */
    public Box(Vector3f position, Vector3f rotation, Vector3f scale, String pathToTexture, String pathToSpecularMap) {
        super(new Mesh(new Vertex[] {
                // front face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0, 1), new Vector3f(0, 0, 1)), // 0, 1
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1, 1), new Vector3f(0, 0, 1)), // 1, 1
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1, 0), new Vector3f(0, 0, 1)), // 1, 0
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0, 0), new Vector3f(0, 0, 1)), // 0, 0

                // back face
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0, 1), new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1, 1), new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1, 0), new Vector3f(0, 0, -1)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0, 0), new Vector3f(0, 0, -1)),

                // right face
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0, 1), new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(0, 0), new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1, 1), new Vector3f(1, 0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1, 0), new Vector3f(1, 0, 0)),

                // left face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1, 1), new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1, 0), new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0, 1), new Vector3f(-1, 0, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0, 0), new Vector3f(-1, 0, 0)),

                // top face
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1, 1), new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0, 1), new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1, 0), new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0, 0), new Vector3f(0, 1, 0)),

                // bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1, 1), new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0, 1), new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1, 0), new Vector3f(0, -1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0, 0), new Vector3f(0, -1, 0)),
        }, new int[] {
                // front face
                0, 1, 2,
                2, 3, 0,

                // back face
                5, 4, 7,
                5, 7, 6,

                // right face
                8, 10, 11,
                8, 11, 9,

                // left face
                14, 12, 13,
                14, 13, 15,

                // top face
                17, 16, 18,
                17, 18, 19,

                // bottom face
                20, 22, 23,
                20, 23, 21,
        }, new Material(pathToTexture,pathToSpecularMap)), position, rotation, scale);
    }


}
