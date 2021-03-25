package com.bramerlabs.terra_nova.main.objects;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.engine.math.noise.SimplexNoiseOctave;
import com.bramerlabs.engine.math.shapes_2d.Icosahedron;
import com.bramerlabs.engine.math.shapes_2d.Triangle;
import com.bramerlabs.engine.objects.untextured.RenderObject;

import java.util.Random;

public class Tree extends RenderObject {

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Tree(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    /**
     * procedurally generates a random tree
     * @param seed - the seed used to generate the tree
     * @return - a new instance of a tree generated from the seed with the base of the trunk at the origin
     */
    public static Tree getInstance(int seed) {
//        SimplexNoiseOctave noise = new SimplexNoiseOctave(seed);
        Random random = new Random(seed);

        Icosahedron ico = new Icosahedron(random.nextFloat());

        float height = 0.5f + random.nextFloat();

        Vector3f[] vertices = new Vector3f[12];

        for (int i = 0; i < ico.getVertices().length; i++) {
            float dx = 0.3f * random.nextFloat();
            float dy = 0.3f * random.nextFloat();
            float dz = 0.3f * random.nextFloat();
            vertices[i] = Vector3f.add(ico.getVertices()[i], dx, dy + height, dz);
        }

        float tx = 0, ty = 0, tz = 0;
        for (int i = 0; i < vertices.length; i++) {
            tx += vertices[i].x;
            ty += vertices[i].y;
            tz += vertices[i].z;
        }
        float mx = tx / 12.0f;
        float my = ty / 12.0f;
        float mz = tz / 12.0f;
        Vector3f trunkPosition = new Vector3f(mx, my, mz);

        Triangle[] leafTriangles = Icosahedron.getTriangles(vertices);
        Triangle[] trunkTriangles = createTrunkTriangles(trunkPosition, random);

        Vector4f leafColor = new Vector4f(Vector3f.divide(new Vector3f(0, 205, 0), new Vector3f(255)), 1.0f);
        Vector4f trunkColor = new Vector4f(Vector3f.divide(new Vector3f(166, 123, 81), new Vector3f(255)), 1.0f);

        Vertex[] meshVertices = new Vertex[leafTriangles.length * 3 + trunkTriangles.length * 3];
        int index = 0;
        for (int i = 0; i < leafTriangles.length; i++) {
            Vector3f normal = Vector3f.cross(Vector3f.subtract(leafTriangles[i].getV2(), leafTriangles[i].getV1()), Vector3f.subtract(leafTriangles[i].getV3(), leafTriangles[i].getV1()));
            meshVertices[3 * i    ] = new Vertex(leafTriangles[i].getV1(), leafColor, normal);
            meshVertices[3 * i + 1] = new Vertex(leafTriangles[i].getV2(), leafColor, normal);
            meshVertices[3 * i + 2] = new Vertex(leafTriangles[i].getV3(), leafColor, normal);
            index ++;
        }
        for (int i = index; i < trunkTriangles.length + leafTriangles.length; i++) {
            int j = i - leafTriangles.length;
            Vector3f normal = Vector3f.cross(Vector3f.subtract(trunkTriangles[j].getV2(), trunkTriangles[j].getV1()), Vector3f.subtract(trunkTriangles[j].getV3(), trunkTriangles[j].getV1()));
            meshVertices[3 * i    ] = new Vertex(trunkTriangles[j].getV1(), trunkColor, normal);
            meshVertices[3 * i + 1] = new Vertex(trunkTriangles[j].getV2(), trunkColor, normal);
            meshVertices[3 * i + 2] = new Vertex(trunkTriangles[j].getV3(), trunkColor, normal);
        }

        int[] indices = new int[meshVertices.length];
        for (int i = 0; i < meshVertices.length; i++) {
            indices[i] = i;
        }

        Mesh mesh = new Mesh(meshVertices, indices);

        return new Tree(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }

    private static Triangle[] createTrunkTriangles(Vector3f position, Random random) {
        float radius = random.nextFloat()/2.0f;
        float[] x = {radius, -radius, -radius, radius};
        float[] z = {radius, radius, -radius, -radius};
        float y1 = 0, y2 = position.getY();
        Vector3f[] v1 = new Vector3f[4];
        Vector3f[] v2 = new Vector3f[4];
        for (int i = 0; i < x.length; i++) {
            v1[i] = new Vector3f(x[i], y1, z[i]);
            v2[i] = new Vector3f(x[i] + position.getX(), y2, z[i] + position.getZ());
        }
        for (int i = 0; i < v1.length; i++) {
            float dx = 0.2f * random.nextFloat();
            float dz = 0.2f * random.nextFloat();
            v1[i].add(dx, 0, dz);
            dx = 0.2f * random.nextFloat();
            dz = 0.2f * random.nextFloat();
            v2[i].add(dx, 0, dz);
        }
        return new Triangle[]{
                new Triangle(v1[0], v2[1], v1[1]),
                new Triangle(v1[0], v2[0], v2[1]),
                new Triangle(v1[1], v2[2], v1[2]),
                new Triangle(v1[1], v2[1], v2[2]),
                new Triangle(v1[2], v2[3], v1[3]),
                new Triangle(v1[2], v2[2], v2[3]),
                new Triangle(v1[3], v2[0], v1[0]),
                new Triangle(v1[3], v2[3], v2[0]),
        };
    }
}