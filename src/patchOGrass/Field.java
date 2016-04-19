package patchOGrass;

import acm.graphics.GCompound;

import java.util.Random;

/**
 * Created by Steven on 4/7/16.
 */
public class Field extends GCompound {

    private float[][] fieldMatrix;
    private Border border;
    public static final int GAP_SPACING = 5;


    public Field(int N, int M) {
        Random rand = new Random();
        fieldMatrix = new float[N][M];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                float randomNum = rand.nextFloat();
                randomNum = (float) ( Math.round(randomNum * 1000d) / 1000d);
                fieldMatrix[i][j] = randomNum;
                //fieldMatrix[i][j] = (float) i / (float) 10;
//                Blade newBlade = new Blade(i * (Blade.WIDTH + GAP_SPACING), j * (Blade.HEIGHT + GAP_SPACING), randomNum);
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


    public float[][] getFieldMatrix() {
        return fieldMatrix;
    }
}
