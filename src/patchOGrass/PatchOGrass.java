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

//        benchmarkDPTall();
//        benchmarkDPWide();
//        benchmarkSquare();
//        benchmarkBF();
//        benchmarkDP();


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
//        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("x") + "," + results.get("y") + ").");
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
//        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("j") + "," + results.get("i") + ").");
//        System.out.println("Area: " + best);
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


    /**
     * This method iteratively runs the findMaxSubmatrix1s methods for the dynamic programming method and the brute force method.
     * It times how long each method takes to compute for square matrices of increasing input sizes. All matrices include approximately
     * 80% 1's and the rest 0's. It times each method for an input size 10 times and prints information about the average results.
     */
    private static void benchmarkSquare() {

        for (int i = 10; i <= 150; i+=10) {

            long bfTime = 0;
            long dpTime = 0;
            int area = 0;

            for (int j = 0; j < 10; j++) {

                int[][] grass = makeBinaryMatrix(i, i, 0.8);


                long startTime = System.currentTimeMillis();
                HashMap bestPatch = findMaxSubmatrix1sBF(grass);
                long endTime = System.currentTimeMillis();
                bfTime+= (endTime - startTime);

                area+= (int) bestPatch.get("height") *  (int) bestPatch.get("width");

                long startTime1 = System.currentTimeMillis();
                HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
                long endTime1 = System.currentTimeMillis();
                dpTime+= (endTime1 - startTime1);

            }


            System.out.println(i + " by " + i + " Matrix");
            System.out.println("Area: " + area/10.);
            System.out.println("BF: " + bfTime/10.);
            System.out.println("DP: " + dpTime/10.);
            System.out.println();

        }

    }


    /**
     * This method iteratively runs the findMaxSubmatrix1sBF method.
     * It times how long the method takes to compute for square matrices of increasing input sizes. All matrices include approximately
     * 80% 1's and the rest 0's. It times the method for an input size 10 times and prints information about the average results.
     */
    private static void benchmarkBF() {


        for (int i = 5; i <= 80; i+=5) {

            long bfTime = 0;
            int area = 0;

            for (int j = 0; j < 10; j++) {

                int[][] grass = makeBinaryMatrix(i, i, 0.8);


                long startTime = System.currentTimeMillis();
                HashMap bestPatch = findMaxSubmatrix1sBF(grass);
                long endTime = System.currentTimeMillis();
                bfTime+= (endTime - startTime);

                area+= (int) bestPatch.get("height") *  (int) bestPatch.get("width");



            }


            System.out.println(i + " by " + i + " Matrix");
            System.out.println("Area: " + area/10.);
            System.out.println("BF: " + bfTime/10.);
            System.out.println();

        }

    }


    /**
     * This method iteratively runs the findMaxSubmatrix1sDP method.
     * It times how long the method takes to compute for square matrices of increasing input sizes. All matrices include approximately
     * 80% 1's and the rest 0's. It times the method for an input size 10 times and prints information about the average results.
     */
    private static void benchmarkDP() {


        for (int i = 500; i <= 15000; i+=500) {

            long dpTime = 0;
            int area = 0;

            for (int j = 0; j < 10; j++) {

                int[][] grass = makeBinaryMatrix(i, i, 0.8);


                long startTime1 = System.currentTimeMillis();
                HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
                long endTime1 = System.currentTimeMillis();
                dpTime+= (endTime1 - startTime1);

                area+= (int) bestPatchDP.get("height") *  (int) bestPatchDP.get("width");

            }


            System.out.println(i + " by " + i + " Matrix");
            System.out.println("Area: " + area/10.);
            System.out.println("DP: " + dpTime/10.);
            System.out.println();

        }

    }




    /**
     * This method iteratively runs the findMaxSubmatrix1sDP method.
     * It times how long the method takes to compute for tall matrices of increasing input sizes.
     * All input matrices have 4 times as many rows as columns and include approximately 80% 1's and the rest 0's.
     * It times the method for an input size 10 times and prints information about the average results.
     */
    private static void benchmarkDPTall() {


        for (int i = 200; i <= 4000; i+=200) {

            long dpTime = 0;
            int area = 0;

            for (int j = 0; j < 10; j++) {

                int[][] grass = makeBinaryMatrix(i*4, i, 0.8);


                long startTime1 = System.currentTimeMillis();
                HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
                long endTime1 = System.currentTimeMillis();
                dpTime+= (endTime1 - startTime1);

                area+= (int) bestPatchDP.get("height") *  (int) bestPatchDP.get("width");

            }


            System.out.println((i*4) + " by " + i + " Matrix");
            System.out.println("Area: " + area/10.);
            System.out.println("DP: " + dpTime/10.);
            System.out.println();

        }

    }




    /**
     * This method iteratively runs the findMaxSubmatrix1sDP method.
     * It times how long the method takes to compute for wide matrices of increasing input sizes.
     * All input matrices have 4 times as many columns as rows and include approximately 80% 1's and the rest 0's.
     * It times the method for an input size 10 times and prints information about the average results.
     */
    private static void benchmarkDPWide() {

        for (int i = 200; i <= 4000; i+=200) {

            long dpTime = 0;
            int area = 0;

            for (int j = 0; j < 10; j++) {

                int[][] grass = makeBinaryMatrix(i, i*4, 0.8);


                long startTime1 = System.currentTimeMillis();
                HashMap bestPatchDP = findMaxSubmatrix1sDP(grass);
                long endTime1 = System.currentTimeMillis();
                dpTime+= (endTime1 - startTime1);

                area+= (int) bestPatchDP.get("height") *  (int) bestPatchDP.get("width");

            }


            System.out.println(i + " by " + (i*4) + " Matrix");
            System.out.println("Area: " + area/10.);
            System.out.println("DP: " + dpTime/10.);
            System.out.println();

        }

    }




}



