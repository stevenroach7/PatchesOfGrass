package patchOGrass;

import java.util.Random;

/**
 * Created by Steven on 4/5/16.
 * * Created by Matt on 3/31/2016.
 */

public class PatchOGrass {

    public static void main(String arg[]){
        float[][] grass = makeGrass(5,5);
        System.out.println(" ");
        float[][] bestPatch = surveyGrass(grass, 2, 2);
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
        for (int i=0; i<=(field.length-n-1); i++){ // TODO: Fix these indices
            for(int j=0; j<=(field[0].length-m-1); j++){
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
        for (int i = currentI; i<=n-1; i++){
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



