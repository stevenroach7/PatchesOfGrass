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



        int[][] grass = makeBinaryMatrix(50, 50, 1);

        HashMap bestPatch = findMaxSubmatrix1sDP(grass);
        assertTrue((int) bestPatch.get("height") == 50);
        assertTrue((int) bestPatch.get("width") == 50);

        grass = makeBinaryMatrix(50, 50, 0);
        bestPatch = findMaxSubmatrix1sDP(grass);
        assertTrue((int) bestPatch.get("height") == 0);
        assertTrue((int) bestPatch.get("width") == 0);



    }



    @Test
    public void testFindMaxSubmatrix1sBF() {


        int[][] grass = makeBinaryMatrix(50, 50, 1);

        HashMap bestPatch = findMaxSubmatrix1sBF(grass);
        assertTrue((int) bestPatch.get("height") == 50);
        assertTrue((int) bestPatch.get("width") == 50);

        grass = makeBinaryMatrix(50, 50, 0);
        bestPatch = findMaxSubmatrix1sBF(grass);
        assertTrue((int) bestPatch.get("height") == 0);
        assertTrue((int) bestPatch.get("width") == 0);


    }



    @Test
    public void testFindMaxSubmatrix1s() {


        int[][] grass = makeBinaryMatrix(50, 50, .8);

        HashMap bestPatchBF = findMaxSubmatrix1sBF(grass);
        HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
        assertEquals(bestPatchBF, bestPatchDP);


        grass = makeBinaryMatrix(40, 10, .8);

        bestPatchBF = findMaxSubmatrix1sBF(grass);
        bestPatchDP = findMaxSubmatrix1sDP(grass);
        assertEquals(bestPatchBF, bestPatchDP);



        grass = makeBinaryMatrix(10, 40, .8);

        bestPatchBF = findMaxSubmatrix1sBF(grass);
        bestPatchDP = findMaxSubmatrix1sDP(grass);
        assertEquals(bestPatchBF, bestPatchDP);


    }









    @Test
    public void testFindMaxRectangleHistogram() {


        int[] hist = new int[] {1,0,2,3};
        int[] results = findMaxRectangleHistogram(hist);
        int area = results[1]*results[2];
        assertTrue(area == 4);


        hist = new int[] {1,0,0,3};
        results = findMaxRectangleHistogram(hist);
        area = results[1]*results[2];
        assertTrue(area == 3);


        hist = new int[] {1,1,1,3};
        results = findMaxRectangleHistogram(hist);
        area = results[1]*results[2];
        assertTrue(area == 4);


        hist = new int[] {1,0,0,0};
        results = findMaxRectangleHistogram(hist);
        area = results[1]*results[2];
        assertTrue(area == 1);


        hist = new int[] {0,0,0,0};
        results = findMaxRectangleHistogram(hist);
        area = results[1]*results[2];
        assertTrue(area == 0);

        hist = new int[] {2,2,2,3};
        results = findMaxRectangleHistogram(hist);
        area = results[1]*results[2];
        assertTrue(area == 8);


    }



}
