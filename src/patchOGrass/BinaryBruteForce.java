package patchOGrass;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Matt on 4/27/2016.
 */
public class BinaryBruteForce {

    /**
     * Brute Force for Binary Patch of Grass problem.
     * results are some sort of sequence, as best I can understand it:
     * Given n=3 & m-3 it is the Sum of :
     * (m x n) + ((n-1) x m) + ((m-1 x n) + ((n-1) x (m-1)) + ((n-2) x m) + ((m-2) x n)
     * + ((m-2) x (n-1)) + ((n-2) x (m-1)) + ((n-2) + (m-2))
     *
     */


    public static void main(String arg[]){
        int[][] grass = makeBinaryGrass(10,10);
        System.out.println(" ");
        HashMap bestPatch = surveyBinaryGrassBF(grass);
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
        System.out.println(" " + results.get("height") + " by " + results.get("width") + ": starting at (" + results.get("i") + "," + results.get("j") + ").");
        return results;
    }


}
