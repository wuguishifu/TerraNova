package com.bramerlabs.engine.math.shapes_2d;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;

import java.util.ArrayList;

public class Icosahedron {

    // the golden ratio
    private static final float phi = 1.16180339f;

    // the amount of faces
    private static final int numFaces = 20;

    // list of vertices making up this icosahedron
    private Vector3f[] v;

    /**
     * generates an icosahedron centered around the origin
     * @param radius - the radius of the icosahedron
     */
    public Icosahedron(float radius) {
        this.v = new Vector3f[]{
                new Vector3f( 0.5f * radius, 0,  phi/2 * radius),
                new Vector3f( 0.5f * radius, 0, -phi/2 * radius),
                new Vector3f(-0.5f * radius, 0,  phi/2 * radius),
                new Vector3f(-0.5f * radius, 0, -phi/2 * radius),
                new Vector3f( phi/2 * radius,  0.5f * radius, 0),
                new Vector3f( phi/2 * radius, -0.5f * radius, 0),
                new Vector3f(-phi/2 * radius,  0.5f * radius, 0),
                new Vector3f(-phi/2 * radius, -0.5f * radius, 0),
                new Vector3f(0,  phi/2 * radius, 0.5f * radius),
                new Vector3f(0,  phi/2 * radius,-0.5f * radius),
                new Vector3f(0, -phi/2 * radius, 0.5f * radius),
                new Vector3f(0, -phi/2 * radius,-0.5f * radius)
        };
    }

    /**
     * getter method
     * @return - the vertices in this icosahedron
     */
    public Vector3f[] getVertices() {
        return this.v;
    }

    /**
     * getter method
     * @param vertices - the vertices in the icosahedron
     * @return - a list of triangular faces in the icosahedron
     */
    public static Triangle[] getTriangles(Vector3f[] vertices) {
        Triangle[] triangles = new Triangle[20];
        triangles[0]  = new Triangle(vertices[0],  vertices[2],  vertices[10]);
        triangles[1]  = new Triangle(vertices[0],  vertices[10], vertices[5]);
        triangles[2]  = new Triangle(vertices[0],  vertices[5],  vertices[4]);
        triangles[3]  = new Triangle(vertices[0],  vertices[4],  vertices[8]);
        triangles[4]  = new Triangle(vertices[0],  vertices[8],  vertices[2]);
        triangles[5]  = new Triangle(vertices[3],  vertices[1],  vertices[11]);
        triangles[6]  = new Triangle(vertices[3],  vertices[11], vertices[7]);
        triangles[7]  = new Triangle(vertices[3],  vertices[7],  vertices[6]);
        triangles[8]  = new Triangle(vertices[3],  vertices[6],  vertices[9]);
        triangles[9]  = new Triangle(vertices[3],  vertices[9],  vertices[1]);
        triangles[10] = new Triangle(vertices[2],  vertices[6],  vertices[7]);
        triangles[11] = new Triangle(vertices[2],  vertices[7],  vertices[10]);
        triangles[12] = new Triangle(vertices[10], vertices[7],  vertices[11]);
        triangles[13] = new Triangle(vertices[10], vertices[11], vertices[5]);
        triangles[14] = new Triangle(vertices[5],  vertices[11], vertices[1]);
        triangles[15] = new Triangle(vertices[5],  vertices[1],  vertices[4]);
        triangles[16] = new Triangle(vertices[4],  vertices[1],  vertices[9]);
        triangles[17] = new Triangle(vertices[4],  vertices[9],  vertices[8]);
        triangles[18] = new Triangle(vertices[8],  vertices[9],  vertices[6]);
        triangles[19] = new Triangle(vertices[8],  vertices[6],  vertices[2]);
        return triangles;
    }

    /**
     * generates a list of triangles used to render this icosahedron
     * @param _v - the vertices in this mesh
     * @return - the list of triangles
     */
    public static Mesh generateMesh(Vector3f[] _v, Vector4f color) {
        Triangle[] triangles = new Triangle[20];
        triangles[0]  = new Triangle(_v[0],  _v[2],  _v[10]);
        triangles[1]  = new Triangle(_v[0],  _v[10], _v[5]);
        triangles[2]  = new Triangle(_v[0],  _v[5],  _v[4]);
        triangles[3]  = new Triangle(_v[0],  _v[4],  _v[8]);
        triangles[4]  = new Triangle(_v[0],  _v[8],  _v[2]);
        triangles[5]  = new Triangle(_v[3],  _v[1],  _v[11]);
        triangles[6]  = new Triangle(_v[3],  _v[11], _v[7]);
        triangles[7]  = new Triangle(_v[3],  _v[7],  _v[6]);
        triangles[8]  = new Triangle(_v[3],  _v[6],  _v[9]);
        triangles[9]  = new Triangle(_v[3],  _v[9],  _v[1]);
        triangles[10] = new Triangle(_v[2],  _v[6],  _v[7]);
        triangles[11] = new Triangle(_v[2],  _v[7],  _v[10]);
        triangles[12] = new Triangle(_v[10], _v[7],  _v[11]);
        triangles[13] = new Triangle(_v[10], _v[11], _v[5]);
        triangles[14] = new Triangle(_v[5],  _v[11], _v[1]);
        triangles[15] = new Triangle(_v[5],  _v[1],  _v[4]);
        triangles[16] = new Triangle(_v[4],  _v[1],  _v[9]);
        triangles[17] = new Triangle(_v[4],  _v[9],  _v[8]);
        triangles[18] = new Triangle(_v[8],  _v[9],  _v[6]);
        triangles[19] = new Triangle(_v[8],  _v[6],  _v[2]);

        Vertex[] vertices = new Vertex[triangles.length * 3];
        for (int i = 0; i < triangles.length; i++) {
            Vector3f normal = Vector3f.cross(Vector3f.subtract(triangles[i].getV2(), triangles[i].getV1()), Vector3f.subtract(triangles[i].getV3(), triangles[i].getV1()));
            vertices[3 * i    ] = new Vertex(triangles[i].getV1(), color, normal);
            vertices[3 * i + 1] = new Vertex(triangles[i].getV2(), color, normal);
            vertices[3 * i + 2] = new Vertex(triangles[i].getV3(), color, normal);
        }

        int[] indices = new int[triangles.length * 3];
        for (int i = 0; i < triangles.length * 3; i++) {
            indices[i] = i;
        }

        return new Mesh(vertices, indices);
    }

}