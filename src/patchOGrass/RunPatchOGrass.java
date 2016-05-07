package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * I made this file for us have a space to test visualizations of an algorithm.
 * Created by Steven on 4/7/16.
 */
public class RunPatchOGrass extends GraphicsProgram {

    public void init() {

        Field field = new Field(10, 15);
        //field.addBorder();
        add(field);

        int[][] matrix = field.getFieldMatrix();

//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//
//                System.out.println("i = "+ i + ", j = " + j + ". Value = " + matrix[i][j]);
//            }
//        }


        //field.surveyGrassBinaryBF();
//
//
//        field.removeBorder();
        field.findMaxSubmatrix1sDP();

//        Histogram histogram = new Histogram(matrix[0]);
//        add(histogram);

    }



}
