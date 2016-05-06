package patchOGrass;

import acm.graphics.GCompound;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Steven on 4/7/16.
 */
public class Field extends GCompound {

    private int[][] fieldMatrix;
    private Border border;
    public static final int GAP_SPACING = 5;


    public Field(int N, int M) {
        Random rand = new Random();
        fieldMatrix = new int[N][M];
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                double randNum = rand.nextDouble();
                if (randNum > .8) {
                    fieldMatrix[i][j] = 0;
                }  else {
                    fieldMatrix[i][j] = 1;
                }
                Blade newBlade = new Blade(i * (Blade.WIDTH + GAP_SPACING) + GAP_SPACING, j * (Blade.HEIGHT + GAP_SPACING) + GAP_SPACING, fieldMatrix[i][j]);
                add(newBlade);
            }
        }
    }


    public void addBorder() {
        border = new Border(5,9);
        add(border);
    }


    /**
     * Searches through the a binary field of grass with a brute-force approach and finds the largest
     * submatrix rectangle of exclusively 1s
     * @return A two dimensional array of floats representing the optimal subfield of grass.
     */
    public float[][] surveyGrassBinaryBF(){
        float currentBest = 0;
        float currentPatch;
        int besti = 0;
        int bestj = 0;
        int bestw = 0;
        int besth = 0;

        border = new Border(0, 0);
        add(border);

        for (int i = 0; i < (fieldMatrix.length); i++){
            for (int j = 0; j < (fieldMatrix[0].length); j++) {
                for (int h = 1; h <= fieldMatrix.length - i; h++) {
                    for (int w = 1; w <= fieldMatrix[0].length - j; w++) {
                        int subMatrixSum = sumGrass(i, j, h, w);
                        border.reSizeBorder(h, w);
                        border.setLocation(j * (Blade.WIDTH + GAP_SPACING), i * (Blade.HEIGHT + GAP_SPACING));
                        pause(10);

                        currentPatch = sumGrass(i, j, h, w);

                        //System.out.println("Current Patch: " + currentPatch);
                        System.out.println("Current Best: " + currentBest);
                        if ((currentPatch > currentBest) && (currentPatch == (h * w))){
                            currentBest = currentPatch;
                            besti = i;
                            bestj = j;
                            besth = h;
                            bestw = w;
                        }
                    }
                }
            }
        }
        System.out.println("Best i,j is " + besti + ", " + bestj);
        border.reSizeBorder(bestw, besth);
        border.setLocation(besti*(Blade.WIDTH + GAP_SPACING), bestj*(Blade.HEIGHT + GAP_SPACING));
        //System.out.println("best patch found at [" + besti +"][" + bestj + "] with a total of " + currentBest);
        return focusGrass(besti, bestj, 0, 0);
    }


    // Model the findMaxSubmatrix1sBF method after the surveyGrassBF method above.

    /**
     * Sums the lengths of a subfield of grass.
     * @param currentI, an int representing the vertical coordinate of the upper left corner of a subfield of grass.
     * @param currentJ, an int representing the horizontal coordinate of the upper left corner of a subfield of grass.
     * @param n, an int height of the subfield of grass.
     * @param m, an int width of the subfield of grass.
     * @return total, a float sum of the lengths of the subfield of grass.
     */
    public int sumGrass(int currentI, int currentJ, int n, int m){
        int total = 0;
        for (int i = currentI; i < currentI + n; i++){
            for (int j = currentJ; j < currentJ + m; j++){
                total += fieldMatrix[i][j];
            }
        }
        return total;
    }

    /**
     * Returns a subfield of grass.
     * @param currentI, an int representing the vertical coordinate of the upper left corner of a subfield of grass.
     * @param currentJ, an int representing the horizontal coordinate of the upper left corner of a subfield of grass.
     * @param n,  an int height of the subfield of grass.
     * @param m,  an int width of the subfield of grass.
     * @return patch, a two dimensional array of floats representing the subfield of grass.
     */
    public float[][] focusGrass(int currentI, int currentJ, int n, int m) {
        float[][] patch = new float[n][m];
        for (int i = currentI; i <= n-1; i++) {
            System.arraycopy(fieldMatrix[i], currentJ, patch[i], currentJ, m - currentJ);
        }
        return patch;
    }




    public int[][] getFieldMatrix() {
        return fieldMatrix;
    }
}
