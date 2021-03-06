package patchOGrass;

import acm.graphics.GCompound;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class that creates a field consisting of blades of grass. This class also has the algorithms we coded as visualizations.
 * Created by Steven on 4/7/16.
 */
public class Field extends GCompound {



    private int[][] fieldMatrix;
    private Border border;
    public static final int GAP_SPACING = 30;


    public Field(int M, int N) {
        Random rand = new Random();
        fieldMatrix = new int[M][N];
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                double randNum = rand.nextDouble();
                if (randNum > .8) {
                    fieldMatrix[i][j] = 0;
                }  else {
                    fieldMatrix[i][j] = 1;
                }
                Blade newBlade = new Blade(j * (Blade.WIDTH + GAP_SPACING) + GAP_SPACING, i * (Blade.HEIGHT + GAP_SPACING) + GAP_SPACING, fieldMatrix[i][j]);
                add(newBlade);
            }
        }
    }



    /**
     * Visualization.
     * Searches through the a binary field of grass with a brute-force approach and finds the largest
     * submatrix rectangle of exclusively 1s.
     * @return A two dimensional array of floats representing the optimal subfield of grass.
     */
    public HashMap<String, Integer> surveyGrassBinaryBF(){

        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("x", 0); // x coordinate of upper left entry
        results.put("y", 0); // y coordinate of upper right entry

        int maxArea = 0;
        int currentArea;

        border = new Border(0,0);
        add(border);

        for (int y = 0; y < (fieldMatrix.length); y++){
            for (int x = 0; x < (fieldMatrix[0].length); x++) {
                for (int h = 1; h <= fieldMatrix.length - y; h++) {
                    for (int w = 1; w <= fieldMatrix[0].length - x; w++) {

                        border.reSizeBorder(h, w);
                        border.setLocation(x * (Blade.WIDTH + GAP_SPACING), y * (Blade.HEIGHT + GAP_SPACING));

                        pause(5);

                        currentArea = sumGrass(x, y, h, w);

//                        System.out.println("Current Best: " + maxArea);
                        if ((currentArea > maxArea) && (currentArea == (h * w))){
                            maxArea = currentArea;
                            results.put("height", h);
                            results.put("width", w);
                            results.put("x", x);
                            results.put("y", y);

                        }
                    }
                }
            }
        }
//        System.out.println("Best x,y is " + results.get("x") + ", " + results.get("y"));
//        System.out.println("Best height, width is " + results.get("height") + ", " + results.get("width"));
        border.reSizeBorder(results.get("height"), results.get("width"));
        border.setLocation(results.get("x") * (Blade.WIDTH + GAP_SPACING), results.get("y") * (Blade.HEIGHT + GAP_SPACING));
        pause(2000);

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
    private int sumGrass(int currentI, int currentJ, int m, int n){
        int total = 0;
        for (int i = currentI; i < currentI + n; i++){
            for (int j = currentJ; j < currentJ + m; j++){
                total += fieldMatrix[j][i];
            }
        }
        return total;
    }


    /**
     * Visualization.
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
        Histogram histogram;


        for (int y = 0; y < fieldMatrix.length; y++) { // Iterate
            // Update frontier
            for (int x = 0; x < fieldMatrix[0].length; x++) { //
                if (fieldMatrix[y][x] == 0) { // Make frontier value at index 0 regardless of what it was before.
                    frontier[x] = 0;
                } else {
                    frontier[x] += 1; // Increment frontier value at index x by 1.
                }
            }



            // Calculate area based on frontier after iterating through each entry of the current row.
            int[] maxHist = findMaxRectangleHistogram(frontier); // Find max rectangle of frontier histogram.

            int area = findArea(maxHist);

            int currX = maxHist[0];
            int currY = y-maxHist[2]+1;
            int currH = maxHist[2];
            int currW = maxHist[1];

            System.out.println("area = " + area);
            System.out.println("x = " + currX);
            System.out.println("y = " + currY);



            histogram = new Histogram(frontier);
            add(histogram, GAP_SPACING/2 , ((y-1)*((Blade.HEIGHT + GAP_SPACING))) - histogram.getHeight() + histogram.LABEL_HEIGHT);

            border.reSizeBorder(currH, currW);
            border.setLocation(currX * (Blade.WIDTH + GAP_SPACING), currY * (Blade.HEIGHT + GAP_SPACING));
            pause(4000);
            remove(histogram);

            if (area > maxArea) {
                results.put("height", currH);
                results.put("width", currW);
                results.put("x", currX);
                results.put("y", currY); // upper left y value is (y+1) - height of rectangle.
                maxArea = area;
            }

        }

        border.reSizeBorder(results.get("height"), results.get("width"));
        border.setLocation(results.get("x") * (Blade.WIDTH + GAP_SPACING), results.get("y") * (Blade.HEIGHT + GAP_SPACING));

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
        if (border != null) {
            remove(border);
        }
    }
}
