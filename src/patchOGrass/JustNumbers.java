package patchOGrass;

import java.util.HashMap;

/**
 * Created by Matt on 5/5/2016.
 */
public class JustNumbers {
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
}
