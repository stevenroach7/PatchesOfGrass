package patchOGrass;

import java.util.*;

/**
 * This class contains methods for solving the problem of finding the largest submatrix of all 1's in a binary matrix.
 * It contains implementations of two different algorithms that solve this problem.
 * One algorithm is a brute force approach while the other uses Dynamic Programming.
 * This class also contains methods to test and benchmark our algorithms.
 * Created by Matt and Steven on 4/5/16.
 */

public class PatchOGrass {




    public static void main(String arg[]){



        benchmarkAlgorithms();

//        int[][] grass = makeBinaryMatrix(150, 100, 0.8);
//
//        System.out.println(" ");
//
//        long startTime = System.currentTimeMillis();
//        HashMap bestPatch = findMaxSubmatrix1sBF(grass);
//        long endTime = System.currentTimeMillis();
//        long bfTime = endTime - startTime;
//
//        System.out.println("BF took " + bfTime + " milliseconds");
//
//
//        long startTime1 = System.currentTimeMillis();
//        HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
//        long endTime1 = System.currentTimeMillis();
//        long dpTime = endTime1 - startTime1;
//        System.out.println("DP took " + dpTime + " milliseconds");
//
//        System.out.println("Difference was " + (bfTime - dpTime));

    }



    /**
     * Takes a binary matrix represented as a 2D array and finds the largest submatrix containing all 1's using a Dynamic Programming approach.
     * This method uses a frontier representing a histogram of potential heights and computes the largest rectangle in the histogram after each row.
     * Runs in O(m*n) time where m is the number of rows and n is the number of columns.
     * @param matrix, a 2D array with only 0's or 1's as entries.
     * @return a Map holding an upper left coordinate, a height, and width. 
     */
    public static HashMap<String, Integer>  findMaxSubmatrix1sDP(int[][] matrix) {

        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("x", 0); // x coordinate of upper left entry
        results.put("y", 0); // y coordinate of upper right entry

        int[] frontier = new int[matrix[0].length];
        int maxArea = 0;

        for (int y = 0; y < matrix.length; y++) { // Iterate
            // Update frontier
            for (int x = 0; x < matrix[0].length; x++) { //
                if (matrix[y][x] == 0) { // Make frontier value at index 0 regardless of what it was before.
                    frontier[x] = 0;
                } else {
                    frontier[x] += 1; // Increment frontier value at index i by 1.
                }
            }
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
        }
        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("x") + "," + results.get("y") + ").");
        return results;
    }


    /**
     * Takes an output array from findMaxRectangleHistogram and computes the area of the rectangle.
     * @param histResults, index 0 has x coordinate, index 1 has width, and index 2 has height.
     * @return an int area or the rectangle.
     */
    private static int findArea(int[] histResults) {
        return histResults[1] * histResults[2];
    }


    /**
     * Takes an array of ints representing a histogram and finds the largest rectangle in the histogram.
     * This method uses a stack to keep track of heights that could possible form a rectangle as it moves across the histogram.
     * Runs in O(n) time where n is the length of histValues.
     * @param histValues, an array with each entry specifying a height for that index bar of the histogram.
     * @return maxInfo, an array where index 0 has x coordinate, index 1 has width, and index 2 has height.
     */
    private static int[] findMaxRectangleHistogram(int[] histValues) {

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




    /**
     * Returns the sum of entries of a submatrix given a matrix and specifications of the submarix.
     * @param matrix, a 2D array representing the full matrix.
     * @param currentI, upper left x coordinate of submatrix.
     * @param currentJ, upper left y coordinate of submatrix.
     * @param h, height of submatrix.
     * @param w, width of submatrix.
     * @return total, an int sum of entries of the submatrix.
     */
    public static int sumBinaryMatrix(int[][] matrix, int currentI, int currentJ, int h, int w){
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
        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("j") + "," + results.get("i") + ").");
        System.out.println("Area: " + best);
        return results;
    }






    /**
     * Creates an MxN field of grass with each blade
     * @param M, an int height of the field of grass.
     * @param N, an int width of the field of grass
     * @return A two dimensional array of binary 1s and 0s representing the field of grass.
     */
    private static int[][] makeBinaryMatrix(int M, int N, double pct){
        Random rand = new Random();
        int[][] matrix = new int[M][N];
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){

                double randNum = rand.nextDouble();
                if (randNum > pct) {
                    matrix[i][j] = 0;
                    //System.out.print(0 + " ");
                } else {
                    matrix[i][j] = 1;
                    //System.out.print(1 + " ");
                }
            }
            //System.out.println(" ");
        }
        return matrix;
    }




    private static void benchmarkAlgorithms() {




        for (int i = 1000; i <= 300000; i+= 1000) {

            int[][] grass = makeBinaryMatrix(i, i, 0.8);

            System.out.println("Matrix is " + i + " by " + i + ".");

//            long startTime = System.currentTimeMillis();
//            HashMap bestPatch = findMaxSubmatrix1sBF(grass);
//            long endTime = System.currentTimeMillis();
//            long bfTime = endTime - startTime;
//
//            System.out.println("BF took " + bfTime + " milliseconds");

            long startTime1 = System.currentTimeMillis();
            HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
            long endTime1 = System.currentTimeMillis();
            long dpTime = endTime1 - startTime1;

            System.out.println("DP took " + dpTime + " milliseconds");
            System.out.println(" ");
//            System.out.println("Difference was " + (bfTime - dpTime) + " milliseconds.");
//            System.out.println(" ");

        }

    }




}



