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
            GRect column = createColumn(frontier[i]);
            add(column);
            column.setLocation(400 + (i* Blade.WIDTH + Field.GAP_SPACING * i), (M * Blade.HEIGHT)-(frontier[i] * Blade.HEIGHT) + Field.GAP_SPACING);
        }
    }

    /**
     * creates a column to be used by the Dynamic Programming approach's histogram representation.
     * @param h height of individual column based on the
     * @return
     */
    private GRect createColumn(int h) {
        int height = h * (Blade.HEIGHT) + Field.GAP_SPACING;
        int width = Blade.WIDTH;

        GRect column = new GRect(width, height);
        column.setColor(Color.BLUE);
        column.setFilled(true);
        return column;
    }


}
