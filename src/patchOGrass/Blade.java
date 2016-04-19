package patchOGrass;


import acm.graphics.*;

import java.awt.*;


/**
 * Created by Steven on 4/7/16.
 */


public class Blade extends GRect {


    private float bladeHeight;

    private static final Color[] COLORS = new Color[] {new Color(204, 255, 153), new Color(153, 255, 153),
            new Color(102, 255, 102), new Color(51, 255, 51), new Color(0, 255, 0), new Color(76, 153, 0),
            new Color(0, 153, 0), new Color(51, 102, 0), new Color(0, 102, 0), new Color(0, 51, 0)};
    private static final double[] RANGE = new double[] {.1, .2, .3, .4, .5, .6, .7, .8, .9, 1};
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;


    public Blade(double x, double y, float bladeHeight) {

        super(x, y, WIDTH, HEIGHT);
        this.bladeHeight = bladeHeight;
        setFilled(true);
        colorGrass();




    }


    private void colorGrass() {
        int i = getIndex();
        setColor(COLORS[i]);
    }



    /**
     * Given a frequency, it returns the index corresponding
     * to the height of the blade.
     * @return an index corresponding to a bladeHeight.
     */
    private int getIndex() {
        int i = 0;
        while (RANGE[i] < bladeHeight)
            i++;
        return i;
    }




    public float getBladeHeight() {
        return bladeHeight;
    }

    public void setBladeHeight(float bladeHeight) {
        this.bladeHeight = bladeHeight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blade blade = (Blade) o;

        return Float.compare(blade.bladeHeight, bladeHeight) == 0;

    }


    @Override
    public String toString() {
        return "Blade{" +
                "bladeHeight=" + bladeHeight +
                '}';
    }

    @Override
    public int hashCode() {
        return (bladeHeight != +0.0f ? Float.floatToIntBits(bladeHeight) : 0);
    }
}