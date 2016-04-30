package patchOGrass;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * Created by Steven on 4/5/16.
 * * Created by Matt on 3/31/2016.
 */

public class PatchOGrass {




    public static void main(String arg[]){
//        float[][] grass = makeGrass(5,5);
//        System.out.println(" ");
//        float[][] bestPatch = surveyGrass(grass, 2, 2);

//        int[] test = new int[]{2,4,7,0, 4};
//        int[] answer = findMaxRectangleHistogram(test);
//        System.out.println(answer[0] + " " + answer[1] + " " + answer[2]);

        int[][] matrix = new int[5][5];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ((i) < 4 ) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }

        int[] results = findMaxSubmatrix1s(matrix);
        for (int i=0; i<results.length; i++) {
            System.out.println(results[i]);
        }





    }


    /**
     * Takes a binary matrix represented as a 2D array and finds the largest submatrix containing all 1's.
     * @param matrix, a 2D array with only 0's or 1's as entries
     * @return a Map holding an upper left coordinate, a height, and width. 
     */
    public static int[] findMaxSubmatrix1s(int[][] matrix) {

        int[] frontier = new int[matrix.length];
        int maxArea = 0;
        int[] bestCorners = new int[]{0,0,0,0,0,0,0,0};

        for (int y = 0; y < matrix.length; y++) { // TODO: Is this right???
            // Update frontier
            for (int i = 0; i < matrix[y].length; i++) {
                if (matrix[y][i] == 0) { // Make frontier value at index 0 regardless of what it was before.
                    frontier[i] = 0;
                } else {
                    frontier[i] += 1; // Increment frontier value at index i by 1.
                }
            }
            // Frontier represents a histogram
            int[] maxHist = findMaxRectangleHistogram(frontier); // Find max rectangle of frontier histogram
            int[] corners = findCorners(maxHist[0]+maxHist[2], y, maxHist[1], maxHist[2]);
            int area = findArea(maxHist);

            if (area > maxArea) {
                bestCorners = corners;
                maxArea = area;
            }
        }
        System.out.println(maxArea);
        return bestCorners;
    }

    private static int findArea(int[] corners) {
        return corners[1] * corners[2];
    }

    private static int[] findCorners(int x, int y, int width, int height) {
        int[] corners =  new int[8];
        corners[0] = x;
        corners[1] = y;
        corners[2] = x + width;
        corners[3] = y;
        corners[4] = x;
        corners[5] = y + height;
        corners[6] = x + width;
        corners[7] = y + height;
        return corners;
    }



    private static int[] findMaxRectangleHistogram(int[] histValues) {

        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        int[] maxInfo = new int[] {0,0,0}; // Holds an index, a width and a height.

        int i; // Declaring i here allows us to only increment i if the first if statement in the loop is executed.
        for (i = 0; i < histValues.length;) {

            if (stack.isEmpty() || histValues[i] >= histValues[stack.peek()]) // Only increment i if stack is empty or current val is < top stack val
                stack.push(i++);
            else {
                int value = stack.pop();

                int area; // Calculate new area
                boolean emptyStack;
                if (stack.isEmpty()) {
                    area = histValues[value] * i;
                    emptyStack = true;
                } else {
                    area =  histValues[value] * (i - stack.peek() - 1);
                    emptyStack = false;
                }
                if (area > maxArea) { // Decompose this step
                    if (emptyStack) {
                        maxInfo[0] = 0;
                        maxInfo[1] = i;
                        maxInfo[2] = histValues[value];
                    } else {
                        maxInfo[0] = stack.peek() + 1;
                        maxInfo[1] = (i - stack.peek() - 1);
                        maxInfo[2] = histValues[value];
                    }
                    maxArea = area;
                }
            }
        }
        while (!stack.isEmpty()) {
            int value = stack.pop();

            int area; // Calculate new area
            boolean emptyStack;
            if (stack.isEmpty()) {
                area = histValues[value] * histValues.length;
                emptyStack = true;
            } else {
                area =  histValues[value] * (histValues.length - stack.peek() - 1);
                emptyStack = false;
            }
            if (area > maxArea) {
                if (emptyStack) {
                    maxInfo[0] = 0;
                    maxInfo[1] = i;
                    maxInfo[2] = histValues[value];
                } else {
                    maxInfo[0] = stack.peek() + 1;
                    maxInfo[1] = (i - stack.peek() - 1);
                    maxInfo[2] = histValues[value];

                }
                maxArea = area;
            }
        }

        //System.out.println("Max area is " + maxArea);
        return maxInfo;
    }





    /**
     * Creates a field of grass with each blade being a random length between 0 and 1.
     * @param N, an int height of the field of grass.
     * @param M, an int width of the field of grass
     * @return A two dimensional array of floats representing the field of grass.
     */
    public static float[][] makeGrass(int N, int M){
        Random rand = new Random();
        float[][] field = new float[N][M];
        for (int i = 0; i<N; i++){
            for (int j=0; j<M; j++){
                float randomNum = rand.nextFloat();
                randomNum = (float) ( Math.round(randomNum * 1000d) / 1000d);
                System.out.print(randomNum + " ");
                field[i][j] = randomNum;
            }
            System.out.println(" ");
        }
        return field;
    }


    /**
     * Searches through the field of grass with a brute-force approach and finds the n by m subfield
     * of grass with the highest average height.
     * @param field, A two dimensional array of floats representing the field of grass.
     * @param n, an int height of the subfield of grass
     * @param m, an int width of the subfield of grass
     * @return A two dimensional array of floats representing the optimal subfield of grass.
     */
    public static float[][] surveyGrass(float[][] field, int n, int m){
        double currentBest = 0;
        int besti = 0;
        int bestj = 0;
        for (int i=0; i<=(field.length-n); i++){ // TODO: Fix these indices
            for(int j=0; j<=(field[0].length-m); j++){
                float currentPatch = sumGrass(field, i, j, n, m);
                System.out.println(currentPatch);
                if (currentPatch > currentBest){
                    currentBest = currentPatch;
                    besti = i;
                    bestj = j;
                }
            }
        }
        System.out.println("best patch found at [" + besti +"][" + bestj + "] with a total of " + currentBest);
        return focusGrass(field, besti, bestj, n, m);
    }


    /**
     * Sums the lengths of a subfield of grass.
     * @param field, A two dimensional array of floats representing the field of grass.
     * @param currentI, an int representing the vertical coordinate of the upper left corner of a subfield of grass.
     * @param currentJ, an int representing the horizontal coordinate of the upper left corner of a subfield of grass.
     * @param n, an int height of the subfield of grass.
     * @param m, an int width of the subfield of grass.
     * @return total, a float sum of the lengths of the subfield of grass.
     */
    public static float sumGrass(float[][] field, int currentI, int currentJ, int n, int m){
        float total = 0;
        for (int i = currentI; i<=currentI + n; i++){
            for (int j = currentJ; j<=m-1; j++){
                total+=field[i][j];
            }
        }
        return total;
    }


    /**
     * Returns a subfield of grass.
     * @param field, A two dimensional array of floats representing the field of grass.
     * @param currentI, an int representing the vertical coordinate of the upper left corner of a subfield of grass.
     * @param currentJ, an int representing the horizontal coordinate of the upper left corner of a subfield of grass.
     * @param n,  an int height of the subfield of grass.
     * @param m,  an int width of the subfield of grass.
     * @return patch, a two dimensional array of floats representing the subfield of grass.
     */
    public static float[][] focusGrass(float[][] field, int currentI, int currentJ, int n, int m) {
        float[][] patch = new float[n][m];
        for (int i = currentI; i <= n-1; i++) {
            System.arraycopy(field[i], currentJ, patch[i], currentJ, m - currentJ);
        }
        return patch;
    }
}



