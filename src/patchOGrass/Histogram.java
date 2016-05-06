package patchOGrass;

import acm.graphics.GCompound;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Created by Matt on 5/5/2016.
 */
public class Histogram extends GCompound {

    private int M;
    private int N;

    public Histogram(int[] frontier, int M, int N){
        super();
        this.M = M;
        this.N = N;
    }

    private GRect createColumn(boolean orient, int M, int N) {
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
}
