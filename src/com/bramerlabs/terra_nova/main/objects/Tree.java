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
        Random random = new Random(seed);
        SimplexNoiseOctave noise = new SimplexNoiseOctave(seed);

        // generate the skeleton
        int n = 1 + random.nextInt(5);
        Vector3f origin = new Vector3f(0, 0, 0);
        Vector3f[] trunkSkeleton = new Vector3f[n];
        trunkSkeleton[0] = origin;
        for (int i = 1; i < n; i++) {
            Vector3f nextVertex = Vector3f.add(trunkSkeleton[i-1], random.nextFloat(), random.nextFloat(), random.nextFloat());
            if (nextVertex.y < trunkSkeleton[i-1].y) {
                break;
            } else {
                trunkSkeleton[i] = nextVertex;
            }
        }
        for (Vector3f vector3f : trunkSkeleton) {
            System.out.println(vector3f);
        }

        // randomly create points around the skeleton
        return null;
    }
}