package patchOGrass;

import acm.graphics.GCompound;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Created by Matt on 5/5/2016.
 */
public class Histogram extends GCompound {

    public static final int HISTGAP = 100;

    private int M;
    private int N;


    public Histogram(int[] frontier, int M, int N){
        super();
        this.M = M;
        this.N = N;

        for (int i=0; i<frontier.length; i++){
            GRect column = createColumn(M);
            add(column);
            column.setLocation(0,100);
        }
    }

    /**
     *
     * @param M height of individual column
     * @return
     */
    private GRect createColumn(int M) {
        int height = M * (Blade.HEIGHT + Field.GAP_SPACING) + Field.GAP_SPACING;
        int width = (Blade.WIDTH + Field.GAP_SPACING) + Field.GAP_SPACING;

        GRect column = new GRect(width, height);
        column.setColor(Color.GREEN);
        column.setFilled(true);
        return column;
    }


}
