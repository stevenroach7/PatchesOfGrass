package patchOGrass;


import acm.graphics.*;


import java.awt.*;


/**
 * Created by Steven on 4/7/16.
 */


public class Blade extends GRect {


    private int bladeQuality;

    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;


    public Blade(double x, double y, int bladeQuality) {

        super(x, y, WIDTH, HEIGHT);
        this.bladeQuality = bladeQuality;

        if (bladeQuality == 1) {
            setColor(new Color(0, 153, 0));
        } else {
            setColor(new Color(255, 255, 255));
        }
        setFilled(true);


    }


    public int getBladeQuality() {
        return bladeQuality;
    }

    public void setBladeQuality(int bladeQuality) {
        this.bladeQuality = bladeQuality;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blade blade = (Blade) o;

        return bladeQuality == blade.bladeQuality;

    }

    @Override
    public int hashCode() {
        return bladeQuality;
    }
}
