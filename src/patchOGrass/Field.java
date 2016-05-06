package patchOGrass;

import acm.graphics.GCompound;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
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
        int currentBest = 0;
        int currentPatch;
        int besti = 0;
        int bestj = 0;
        int bestw = 0;
        int besth = 0;

        border = new Border(0,0);
        add(border);


        for (int i = 0; i < (fieldMatrix.length); i++){
            for (int j = 0; j < (fieldMatrix[0].length); j++) {
                for (int h = 1; h <= fieldMatrix.length - i; h++) {
                    for (int w = 1; w <= fieldMatrix[0].length - j; w++) {

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





    /**
     * Takes a binary matrix represented as a 2D array and finds the largest submatrix containing all 1's using a Dynamic Programming approach.
     * This method uses a frontier representing a histogram of potential heights and computes the largest rectangle in the histogram after each row.
     * Runs in O(m*n) time where m is the number of rows and n is the number of columns.
     * @return a Map holding an upper left coordinate, a height, and width.
     */
    public HashMap<String, Integer>  findMaxSubmatrix1sDP() {

        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("x", 0); // x coordinate of upper left entry
        results.put("y", 0); // y coordinate of upper right entry

        int[] frontier = new int[fieldMatrix[0].length];
        int maxArea = 0;

        border = new Border(0,0);
        add(border);
        Histogram histogram = new Histogram(frontier, 0);

        for (int y = 0; y < fieldMatrix.length; y++) { // Iterate
            // Update frontier
            for (int x = 0; x < fieldMatrix[0].length; x++) { //
                if (fieldMatrix[y][x] == 0) { // Make frontier value at index 0 regardless of what it was before.
                    frontier[x] = 0;
                } else {
                    frontier[x] += 1; // Increment frontier value at index i by 1.
                }
            }


            //histogram.update(frontier, LLCorner);

            // Calculate area based on frontier after iterating through each entry of the current row.
            int[] maxHist = findMaxRectangleHistogram(frontier); // Find max rectangle of frontier histogram.

            int area = findArea(maxHist);

            if (area > maxArea) {
                results.put("height", maxHist[2]);
                results.put("width", maxHist[1]);
                results.put("x", maxHist[0]);
                results.put("y", y - maxHist[2]+1); // upper left y value is (y+1) - height of rectangle.
                maxArea = area;
            }

            border.reSizeBorder(results.get("width"), results.get("height")); // TODO: FIX this.
            border.setLocation(results.get("y"), results.get("x"));
            pause(1000);

        }
//        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("x") + "," + results.get("y") + ").");
        return results;
    }


    /**
     * Takes an output array from findMaxRectangleHistogram and computes the area of the rectangle.
     * @param histResults, index 0 has x coordinate, index 1 has width, and index 2 has height.
     * @return an int area or the rectangle.
     */
    protected static int findArea(int[] histResults) {
        return histResults[1] * histResults[2];
    }


    /**
     * Takes an array of ints representing a histogram and finds the largest rectangle in the histogram.
     * This method uses a stack to keep track of heights that could possible form a rectangle as it moves across the histogram.
     * Runs in O(n) time where n is the length of histValues.
     * @param histValues, an array with each entry specifying a height for that index bar of the histogram.
     * @return maxInfo, an array where index 0 has x coordinate, index 1 has width, and index 2 has height.
     */
    protected static int[] findMaxRectangleHistogram(int[] histValues) {

        Deque<Integer> stack = new LinkedList<>();
        int maxArea = 0;
        int[] maxInfo = new int[] {0,0,0}; // Holds an index, a width and a height.

        int i; // Declaring i here allows us to increment i only if the first if statement in the loop is executed.
        for (i = 0; i < histValues.length;) {

            if (stack.isEmpty() || histValues[i] >= histValues[stack.peek()]) // Only increment i if stack is empty or current val is < top stack val
                stack.push(i++);
            else {
                int value = stack.pop();

                int area; // Calculate new area
                boolean emptyStack; // This allows us to know the x coordinate of the max rectangle when we calculate later.
                // Area formula is different depending on if the stack is empty or not.
                if (stack.isEmpty()) {
                    area = histValues[value] * i;
                    emptyStack = true;
                } else {
                    area =  histValues[value] * (i - stack.peek() - 1);
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
        }
        // Process for after the loop has finished and the stack isn't empty yet.
        while (!stack.isEmpty()) {
            int value = stack.pop();

            int area; // Calculate new area
            boolean emptyStack; // This allows us to know the x coordinate of the max rectangle when we calculate later.
            // Area formula is different depending on if the stack is empty or not.
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
        return maxInfo;
    }




















    public int[][] getFieldMatrix() {
        return fieldMatrix;
    }

    public void removeBorder() {
        remove(border);
    }
}
