package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * I made this file for us have a space to test visualizations of an algorithm.
 * Created by Steven on 4/7/16.
 */
public class RunPatchOGrass extends GraphicsProgram {

    public void init() {

        Field field = new Field(10, 10);
        //field.addBorder();
        add(field);

        int[][] matrix = field.getFieldMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                System.out.println("i = "+ i + ", j = " + j + ". Value = " + matrix[i][j]);
            }
        }
        Histogram histogram = new Histogram(matrix[0], 10, 5);
        add(histogram);

        field.surveyGrassBinaryBF();



    }



}
