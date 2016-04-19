package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * Created by Steven on 4/7/16.
 */
public class RunPatchOGrass extends GraphicsProgram {




    public void init() {

        Field field = new Field(25, 25);
        //field.addBorder();
        add(field);

        float[][] matrix = field.getFieldMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                System.out.println("i = "+ i + ", j = " + j + ". Value = " + matrix[i][j]);
            }
        }


        field.surveyGrassBF(10,10);



    }



}
