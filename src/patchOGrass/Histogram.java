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


    public Histogram(int[] frontier){
        super();
        this.M = frontier.length;

        for (int i=0; i<frontier.length; i++){
            GRect column = createColumn(frontier[i]);
            add(column);
            column.setLocation(HISTGAP + (i* Blade.WIDTH + i*Field.GAP_SPACING), (M * Blade.HEIGHT + 2*Field.GAP_SPACING)-(frontier[i] * Blade.HEIGHT));
        }

        GLabel row = new GLabel(frontierToString(frontier));
        add(row);
        row.setLocation(HISTGAP, M *  (Blade.HEIGHT + Field.GAP_SPACING));
    }

    /**
     * creates a column to be used by the Dynamic Programming approach's histogram representation.
     * @param h height of individual column based on the value of some input array element
     * @return a Grectangle that represents the integer value of a given input array element
     */
    private GRect createColumn(int h) {
        int height = h * (Blade.HEIGHT);
        int width = Blade.WIDTH + Field.GAP_SPACING;

        GRect column = new GRect(width, height);
        column.setColor(Color.CYAN);
        column.setFilled(true);
        return column;
    }

    /**
     * creates the string for GLabel which shows the value of each Histogram height
     * @param frontier array of integer values to be shown beneath its histogram representation.
     * @return string of frontier's values spaced out to correlate with its histogram
     */
    private String frontierToString(int[] frontier){
        String row = " ";
        for (int i = 0; i<frontier.length; i++){
            row+=(frontier[i] + "   ");
        }
        return row;
    }
}
