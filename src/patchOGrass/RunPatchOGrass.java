package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * GraphicsProgram to run visualizations.
 * Created by Steven on 4/7/16.
 */
public class RunPatchOGrass extends GraphicsProgram {

    public void init() {

        setSize(1400, 1000);

        Field field = new Field(7, 15);
        add(field);


        field.surveyGrassBinaryBF();
        field.removeBorder();
        field.findMaxSubmatrix1sDP();


    }



}
