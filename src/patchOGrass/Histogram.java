package patchOGrass;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Created by Matt on 5/5/2016.
 */
public class Histogram extends GCompound {

    public static final int HISTGAP = 200;

    private int M;
    private int N;


    public Histogram(int[] frontier, int M){
        super();
        this.M = M;

        for (int i=0; i<frontier.length; i++){
            GRect column = createColumn(frontier[i]);
            add(column);
            column.setLocation(HISTGAP + (i* Blade.WIDTH + i*Field.GAP_SPACING), (M * Blade.HEIGHT)-(frontier[i] * Blade.HEIGHT) + Field.GAP_SPACING);
        }

        GLabel row = new GLabel(frontierToString(frontier));
        add(row);
        row.setLocation(HISTGAP, (M *  Blade.HEIGHT) + Blade.HEIGHT + Field.GAP_SPACING);
    }

    /**
     * creates a column to be used by the Dynamic Programming approach's histogram representation.
     * @param h height of individual column based on the
     * @return
     */
    private GRect createColumn(int h) {
        int height = h * (Blade.HEIGHT);
        int width = Blade.WIDTH + Field.GAP_SPACING;

        GRect column = new GRect(width, height);
        column.setColor(Color.BLUE);
        column.setFilled(true);
        return column;
    }

    private String frontierToString(int[] frontier){
        String row = " ";
        for (int i = 0; i<frontier.length; i++){
            row+=(frontier[i] + "   ");
            System.out.println(row);
        }

        return row;
    }
}
