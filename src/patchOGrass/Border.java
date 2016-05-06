package patchOGrass;

import acm.graphics.*;

import java.awt.*;

/**
 * Created by Steven on 4/15/16.
 */

public class Border extends GCompound {


    private GRect top;
    private GRect bottom;
    private GRect left;
    private GRect right;


    private int M;
    private int N;


    /**
     * Constructor for the Border Class. Initializes instance variables representing the sides of the Border.
     * @param M, rows in Border
     * @param N, columns in Border
     */
    public Border(int M, int N) {
        super();
        this.M = M;
        this.N = N;

        top = createSide(true, M, N);
        bottom = createSide(true, M, N);
        left = createSide(false, M, N);
        right = createSide(false, M, N);

        add(top, 0, 0);
        add(bottom, 0, left.getHeight()-Field.GAP_SPACING);
        add(left, 0, 0);
        add(right, top.getWidth()-Field.GAP_SPACING, 0);
    }


    /**
     * Creates the sides
     * @param orient, true is horizontal, false is vertical orientation.
     * @param M, rows in Border
     * @param N, columns in Border
     * @return
     */
    private GRect createSide(boolean orient, int M, int N) {
        int height = M * (Blade.HEIGHT + Field.GAP_SPACING) + Field.GAP_SPACING;
        int width = N * (Blade.WIDTH + Field.GAP_SPACING) + Field.GAP_SPACING;

        GRect side;
        if (orient) {
            side = new GRect(width, Field.GAP_SPACING);
        } else {
            side = new GRect(Field.GAP_SPACING, height);
        }
        side.setColor(Color.RED);
        side.setFilled(true);
        return side;
    }



    /**
     * Resizes the Border.
     * @param M, number of rows in updated border
     * @param N, number of columns in updated border
     */
    public void reSizeBorder(int M, int N) {


        this.M = M;
        this.N = N;

        remove(top);
        remove(bottom);
        remove(left);
        remove(right);

        top = createSide(true, M, N);
        bottom = createSide(true, M, N);
        left = createSide(false, M, N);
        right = createSide(false, M, N);

        add(top, 0, 0);
        add(bottom, 0, left.getHeight()-Field.GAP_SPACING);
        add(left, 0, 0);
        add(right, top.getWidth()-Field.GAP_SPACING, 0);


    }





    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }



}
