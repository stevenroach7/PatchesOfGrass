package patchOGrass;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;


import java.awt.*;


/**
 * Created by Matt on 5/5/2016.
 */
public class Histogram extends GCompound {

    public static final int LABEL_HEIGHT = 5 * Blade.HEIGHT;

    private int M;
    private int N;


    public Histogram(int[] frontier){
        super();
        this.M = max(frontier);
        this.N = frontier.length;

        //int histGap = (N* (Field.GAP_SPACING + Blade.WIDTH));
        int histGap = 0;



        for (int i=0; i<frontier.length; i++){
            GRect column = createColumn(frontier[i]);
            add(column);

            column.setLocation(histGap + (i * (Blade.WIDTH + Field.GAP_SPACING)), (M - frontier[i])*(Blade.HEIGHT + Field.GAP_SPACING));
        }

        GLabel row = new GLabel(frontierToString(frontier),histGap + (N* (Field.GAP_SPACING + Blade.WIDTH)), LABEL_HEIGHT);
        row.setFont("Helvetica-12");
        add(row);

        row.setLocation(histGap, (M*(Blade.HEIGHT + Field.GAP_SPACING)) + Field.GAP_SPACING + Blade.HEIGHT);
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
        int height = (h*(Blade.HEIGHT + Field.GAP_SPACING));
        int width = Blade.WIDTH + Field.GAP_SPACING;

        GRect column = new GRect(width, height);
        column.setColor(Color.BLACK); // TODO: Change color?

        //column.setFilled(true);
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
            row+=(frontier[i] + "          ");
        }
        return row;
    }
}
