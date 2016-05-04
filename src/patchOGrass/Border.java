package patchOGrass;

import acm.graphics.*;

import java.awt.*;

/**
 * Created by Steven on 4/15/16.
 */

public class Border extends GCompound {

    public Border(int N, int M) {
        super();

        GRect top = createSide(true, N, M);
        GRect bottom = createSide(true, N, M);
        GRect left = createSide(false, N, M);
        GRect right = createSide(false, N, M);

        add(top, 0, 0);
        add(bottom, 0, left.getHeight()-Field.GAP_SPACING);
        add(left, 0, 0);
        add(right, top.getWidth()-Field.GAP_SPACING, 0);
    }


    public GRect createSide(boolean orient, int N, int M) {
        int width = N * (Blade.WIDTH + Field.GAP_SPACING) + Field.GAP_SPACING;
        int height = M * (Blade.HEIGHT + Field.GAP_SPACING) + Field.GAP_SPACING;
        GRect side;
        if (orient) {
            side = new GRect(width, Field.GAP_SPACING);
        } else {
            side = new GRect(Field.GAP_SPACING, height);
        }
        side.setColor(Color.BLUE);
        side.setFilled(true);
        return side;
    }


    // TODO: Make method to change border size


}
