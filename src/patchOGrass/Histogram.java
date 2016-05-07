package patchOGrass;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.sun.tools.javac.util.ArrayUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Matt on 5/5/2016.
 */
public class Histogram extends GCompound {

    public static final int HISTGAP = 0;

    private int M;
    private int N;


    public Histogram(int[] frontier){
        super();
        //this.M = frontier.length;
        this.M = max(frontier);
        this.N = frontier.length;

        for (int i=0; i<frontier.length; i++){
            GRect column = createColumn(frontier[i]);
            add(column);

            // Really confused by this line.
            column.setLocation(HISTGAP + (i * Blade.WIDTH + i * Field.GAP_SPACING), (M * Blade.HEIGHT + 2*Field.GAP_SPACING)-(frontier[i] * Blade.HEIGHT));
        }

        GLabel row = new GLabel(frontierToString(frontier));
        row.setFont("Helvetica-12");
        add(row);
        //row.setLocation(HISTGAP, M * (Blade.HEIGHT + Field.GAP_SPACING));
        row.setLocation(HISTGAP, M * (Blade.HEIGHT + Field.GAP_SPACING));
    }


    /**
     * Returns the maximum value in an array of whole numbers
     * @param frontier, array
     * @return int max value
     */
    private int max(int[] frontier) {
        int maxVal = -1;

        for (int val : frontier) {
            if (val > maxVal) {
                maxVal = val;
            }
        }
        return maxVal;
    }

    /**
     * creates a column to be used by the Dynamic Programming approach's histogram representation.
     * @param h height of individual column based on the value of some input array element
     * @return a Grectangle that represents the integer value of a given input array element
     */
    private GRect createColumn(int h) {
        int height = h * (Blade.HEIGHT); // Add Gap Spacing
        int width = Blade.WIDTH + Field.GAP_SPACING;

        GRect column = new GRect(width, height);
        column.setColor(Color.CYAN); // TODO: Change color?
        //column.setFilled(true);
        return column;
    }

    /**
     * creates the string for GLabel which shows the value of each Histogram height
     * @param frontier array of integer values to be shown beneath its histogram representation.
     * @return string of frontier's values spaced out to correlate with its histogram
     */
    private String frontierToString(int[] frontier){
        String row = "";
        for (int i = 0; i<frontier.length; i++){
            row+=(frontier[i] + "  ");
        }
        return row;
    }
}
