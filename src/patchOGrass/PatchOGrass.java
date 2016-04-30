package patchOGrass;

import java.util.*;

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

//        int[][] matrix = new int[5][5];
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                if ((i) < 4 ) {
//                    matrix[i][j] = 1;
//                } else {
//                    matrix[i][j] = 0;
//                }
//            }
//        }
//
//
//        for (int i=0; i<results.length; i++) {
//            System.out.println(results[i]);
//        }




        int[][] grass = makeBinaryGrass(10,10);
        System.out.println(" ");
        HashMap bestPatch = surveyBinaryGrassBF(grass);


        HashMap bestPatchDP = findMaxSubmatrix1s(grass);


    }







    /**
     * Takes a binary matrix represented as a 2D array and finds the largest submatrix containing all 1's.
     * @param matrix, a 2D array with only 0's or 1's as entries
     * @return a Map holding an upper left coordinate, a height, and width. 
     */
    public static HashMap<String, Integer>  findMaxSubmatrix1s(int[][] matrix) {

        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("x", 0);
        results.put("y", 0);


        int[] frontier = new int[matrix.length];
        int maxArea = 0;
        //int[] bestCorners = new int[]{0,0,0,0,0,0,0,0};

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

            //int[] corners = findCorners(maxHist[0]+maxHist[2], y, maxHist[1], maxHist[2]);
            int area = findArea(maxHist);

            if (area > maxArea) {
                maxArea = area;
                results.put("height", maxHist[2]);
                results.put("width", maxHist[1]);
                results.put("x", maxHist[0]);
                results.put("y", y - maxHist[2]+1);
            }
        }
        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("x") + "," + results.get("y") + ").");
        return results;
    }

    private static int findArea(int[] histResults) {
        return histResults[1] * histResults[2];
    }

//    private static HashMap<String, Integer> findCorners(int x, int y, int width, int height) {
//        new
//    }



    private static int[] findMaxRectangleHistogram(int[] histValues) {

        Deque<Integer> stack = new LinkedList<>();
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
     *@param N, an int height of the field of grass.
     * @param M, an int width of the field of grass
     * @return A two dimensional array of binary 1s and 0s representing the field of grass.
     */
    public static int[][] makeBinaryGrass(int N, int M){
        Random rand = new Random();
        int[][] field = new int[N][M];
        for (int i = 0; i<N; i++){
            for (int j=0; j<M; j++){
                int oneOrZero = rand.nextInt(10) % 2;
                System.out.print(oneOrZero + " ");
                field[i][j] = oneOrZero;
            }
            System.out.println(" ");
        }
        return field;
    }

    public static int sumBinaryGrass(int[][] field, int currentI, int currentJ, int h, int w){
        int total = 0;
        for (int i = currentI; i< currentI + h; i++){
            for (int j = currentJ; j< currentJ + w; j++){
                total+=field[i][j];
            }
        }
        //System.out.print(" adds to : " + total);
        //System.out.println(" ");
        return total;
    }

    public static HashMap<String, Integer> surveyBinaryGrassBF(int[][] field){
        HashMap<String, Integer> results = new HashMap<String, Integer>();
        results.put("height", 0);
        results.put("width", 0);
        results.put("i", 0);
        results.put("j", 0);
        int best = 0;
        int current;
        int totLoops = 0;
        for (int i=0; i<field.length;i++){
            for (int j=0; j<field[0].length; j++){
                for (int h=1; h<= field.length - i; h++){
                    for (int w=1; w<= field[0].length - j; w++){
                        totLoops++;
                        //System.out.println(i + " " + j + " " + h + " " + w);
                        current = sumBinaryGrass(field, i, j, h, w);
                        if (current == (h * w)){
                            if (current > best){
                                results.put("height", h);
                                results.put("width", w);
                                results.put("i", i);
                                results.put("j", j);
                                best = h * w;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(totLoops);
        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("j") + "," + results.get("i") + ").");
        return results;
    }











}



