package patchOGrass;

import acm.program.GraphicsProgram;

/**
 * Created by Matt on 5/6/2016.
 */
public class FixingHistogram extends GraphicsProgram{

        public void init() {

            Field field = new Field(5, 5);
            //field.addBorder();
            add(field);

            int[][] matrix = field.getFieldMatrix();


            Histogram histogram = new Histogram(matrix[0],5, 5);
            int[] testArray = {5, 4, 3, 2, 1};
            Histogram test = new Histogram(testArray, 5, 5);
            add(test);
//        field.surveyGrassBinaryBF();
//
//
//        field.removeBorder();
            //field.findMaxSubmatrix1sDP();



        }
}
