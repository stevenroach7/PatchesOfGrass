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
     * Searches through the field of grass with a brute-force approach and finds the n by m subfield
     * of grass with the highest average height.
     * @param n, an int height of the subfield of grass
     * @param m, an int width of the subfield of grass
     * @return A two dimensional array of floats representing the optimal subfield of grass.
     */
    public float[][] surveyGrassBF(int n, int m){
        float currentBest = 0;
        float currentPatch;
        int besti = 0;
        int bestj = 0;

        border = new Border(n, m);
        add(border);

        for (int i = 0; i <= (fieldMatrix.length-n); i++){
            for(int j = 0; j <= (fieldMatrix[0].length-m); j++) {

                border.setLocation(i * (Blade.WIDTH + GAP_SPACING), j * (Blade.HEIGHT + GAP_SPACING));
                pause(50);

                currentPatch = sumGrass(i, j, n, m);

                System.out.println("Current Patch: " + currentPatch);
                System.out.println("Current Best: " + currentBest);
                if (currentPatch > currentBest){
                    currentBest = currentPatch;
                    besti = i;
                    bestj = j;
                }
            }
        }
        System.out.println("Best i,j is " + besti + ", " + bestj);
        border.setLocation(besti*(Blade.WIDTH + GAP_SPACING), bestj*(Blade.HEIGHT + GAP_SPACING));
        //System.out.println("best patch found at [" + besti +"][" + bestj + "] with a total of " + currentBest);
        return focusGrass(besti, bestj, n, m);
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

        border = new Border(0, 0);
        add(border);

        for (int i = 0; i < (fieldMatrix.length); i++){
            for(int j = 0; j < (fieldMatrix[0].length); j++) {
                for (int h = 1; h <= fieldMatrix.length - i; h++) {
                    for (int w = 1; w <= fieldMatrix[0].length - j; w++) {
                        int subMatrixSum = sumBinaryMatrix(fieldMatrix, i, j, h, w);
                        border.reSizeBorder((h ), (w));
                        border.setLocation(i * (Blade.WIDTH + GAP_SPACING), j * (Blade.HEIGHT + GAP_SPACING));
                        pause(50);

                        currentPatch = sumGrass(i, j, h, w);

                        //System.out.println("Current Patch: " + currentPatch);
                        System.out.println("Current Best: " + currentBest);
                        if (currentPatch > currentBest){
                            currentBest = currentPatch;
                            besti = i;
                            bestj = j;
                        }
                    }
                }
            }
        }
        System.out.println("Best i,j is " + besti + ", " + bestj);
        border.setLocation(besti*(Blade.WIDTH + GAP_SPACING), bestj*(Blade.HEIGHT + GAP_SPACING));
        //System.out.println("best patch found at [" + besti +"][" + bestj + "] with a total of " + currentBest);
        return focusGrass(besti, bestj, 0, 0);
    }



    // TODO: Adapt these static methods to not be static and use the border to create the BF visualization.
    // Model the findMaxSubmatrix1sBF method after the surveyGrassBF method above.

    /**
     * Returns the sum of entries of a submatrix given a matrix and specifications of the submatrix.
     * @param matrix, a 2D array representing the full matrix.
     * @param currentI, upper left x coordinate of submatrix.
     * @param currentJ, upper left y coordinate of submatrix.
     * @param h, height of submatrix.
     * @param w, width of submatrix.
     * @return total, an int sum of entries of the submatrix.
     */
    protected static int sumBinaryMatrix(int[][] matrix, int currentI, int currentJ, int h, int w){
        int total = 0;
        for (int i = currentI; i < currentI + h; i++){
            for (int j = currentJ; j < currentJ + w; j++){
                total+= matrix[i][j];
            }
        }
        return total;
    }



    /**
     * Takes a binary matrix represented as a 2D array and finds the largest submatrix containing all 1's using a Brute Force approach.
     * This method finds every submatrix in matrix and returns a representation of the largest submatrix consisting only of 1's.
     * Runs in O(m^3*n^3) time where m is the number of rows and n is the number of columns.
     * @param matrix, a 2D array with only 0's or 1's as entries.
     * @return a Map holding an upper left coordinate, a height, and width.
     */
    public static HashMap<String, Integer> findMaxSubmatrix1sBF(int[][] matrix){
        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("i", 0);
        results.put("j", 0);
        int best = 0;
        int subMatrixSum;
        for (int i = 0; i < matrix.length;i++){
            for (int j = 0; j < matrix[0].length; j++){
                for (int h = 1; h <= matrix.length - i; h++){
                    for (int w = 1; w <= matrix[0].length - j; w++){
                        subMatrixSum = sumBinaryMatrix(matrix, i, j, h, w);
                        if (subMatrixSum == (h * w)) { // submatrix consists of only ones if and only if the sum of all elements equals h*w.
                            if (subMatrixSum > best) { // subMatrixSum is equal to area since all entries are one.
                                results.put("height", h);
                                results.put("width", w);
                                results.put("i", i);
                                results.put("j", j);
                                best = subMatrixSum;
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("j") + "," + results.get("i") + ").");
//        System.out.println("Area: " + best);
        return results;
    }


    /**
     * Sums the lengths of a subfield of grass.
     * @param currentI, an int representing the vertical coordinate of the upper left corner of a subfield of grass.
     * @param currentJ, an int representing the horizontal coordinate of the upper left corner of a subfield of grass.
     * @param n, an int height of the subfield of grass.
     * @param m, an int width of the subfield of grass.
     * @return total, a float sum of the lengths of the subfield of grass.
     */
    public float sumGrass(int currentI, int currentJ, int n, int m){
        float total = 0;
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
