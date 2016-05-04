package patchOGrass;



import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static patchOGrass.PatchOGrass.*;


/**
 * A class to test the methods in PatchOGrass using JUnit testing.
 * Created by Steven on 5/3/16.
 */

public class TestPatchOGrass {


    @Test
    public void testFindMaxSubmatrix1sDP() {



        int[][] grass = makeBinaryMatrix(100, 100, 0.8);

        HashMap bestPatch = findMaxSubmatrix1sDP(grass);
        int area = (int) bestPatch.get("height")* (int) bestPatch.get("width");

        assertTrue(area > 30);


    }




    @Test
    public void testFindMaxSubmatrix1sBF() {


        int[][] grass = makeBinaryMatrix(10, 10, 0.8);

        HashMap bestPatch = findMaxSubmatrix1sBF(grass);
        int area = (int) bestPatch.get("height")* (int) bestPatch.get("width");

        assertTrue(area > 0);


    }

    @Test
    public void testFindMaxRectangleHistogram() {


        int[] hist = new int[] {1,0,2,3};


        int[] results = findMaxRectangleHistogram(hist);

        int area = results[1]*results[2];

        assertTrue(area == 4);


    }



}
