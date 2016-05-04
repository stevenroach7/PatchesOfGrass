package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * This class is the Main Application for the GUI of our algorithm visualizations.
 * Created by Steven on 4/21/16.
 */
public class MainApp extends GraphicsProgram {


    public void init() {


        // TODO: Add Buttons






        // TODO: Add visualization space

        Field field = new Field(25, 25);
        //field.addBorder();
        add(field);

        int[][] matrix = field.getFieldMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                System.out.println("i = "+ i + ", j = " + j + ". Value = " + matrix[i][j]);
            }
        }


        field.surveyGrassBF(10,10);


    }





    // TODO: Add button response logic








}
