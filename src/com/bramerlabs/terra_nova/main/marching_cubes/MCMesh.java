package com.bramerlabs.terra_nova.main.marching_cubes;

import com.bramerlabs.engine.math.shapes_2d.Triangle;

import java.util.ArrayList;

public class MCMesh {

    private int cubeCount = 10;

    private int cubeSize = 1;

    private int[] x, y, z;

    private int[][][] cases;

    private ArrayList<Triangle> faces;

    /**
     * main constructor
     */
    public MCMesh() {
        x = new int[cubeCount / cubeSize];
        y = new int[cubeCount / cubeSize];
        z = new int[cubeCount / cubeSize];
        for (int i = 0; i < cubeCount / cubeSize; i++) {
            x[i] = i;
            y[i] = i;
            z[i] = i;
        }
        cases = new int[x.length - 1][y.length - 1][z.length - 1];
    }

    private boolean function(float x, float y, float z) {
        return x * x + y * y + z * z < 4 * 4;
    }

    private void createMesh() {
        for (int i = 0; i < x.length - 1; i++) {
            for (int j = 0; j < y.length - 1; j++) {
                for (int k = 0; k < z.length - 1; k++) {
                    int x1 = x[i], x2 = x[i+1];
                    int y1 = y[j], y2 = y[j+1];
                    int z1 = z[k], z2 = z[k+1];

                    int q1 = function(x2, y2, z2) ? 1 : 0;
                    int q2 = function(x2, y2, y1) ? 10 : 0;
                    int q3 = function(x1, y2, z1) ? 100 : 0;
                    int q4 = function(x1, y2, z2) ? 1000 : 0;
                    int q5 = function(x2, y1, z2) ? 10000 : 0;
                    int q6 = function(x2, y1, z1) ? 100000 : 0;
                    int q7 = function(x1, y1, z1) ? 1000000 : 0;
                    int q8 = function(x1, y1, z2) ? 10000000 : 0;

                    cases[i][j][k] = q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8;
                }
            }
        }
    }
}
