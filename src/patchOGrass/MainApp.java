package patchOGrass;

import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class is the Main Application for the GUI of our algorithm visualizations.
 * Created by Steven on 4/21/16.
 */
public class MainApp extends GraphicsProgram {


    private Field field;
    private Border border;

    public void init() {


        setSize(700, 700); // a reasonable size to hold the viz

        JButton start = new JButton("Resize");
        start.setSize(80, 40);

        JButton restart = new JButton("Reset");
        start.setSize(80, 40);

        JButton bfButton = new JButton("Run Brute Force Method");
        bfButton.setSize(80, 40);

        JButton dpButton = new JButton("Run Dynamic Programming Method");
        dpButton.setSize(80, 40);



        add(start, SOUTH);
        add(restart, SOUTH);
        add(bfButton, SOUTH);
        add(dpButton, SOUTH);

        addActionListeners();  // for the button



        // TODO: Add visualization space

        field = new Field(5, 5);
        //field.addBorder();
        add(field);


        // Temporary to test border sizing
//           border = new Border(5, 5);
//        add(border);


//        int[][] matrix = field.getFieldMatrix();
//
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//
//                System.out.println("i = "+ i + ", j = " + j + ". Value = " + matrix[i][j]);
//            }
//        }
//
//
        field.surveyGrassBinaryBF();


    }





    // TODO: Add button response logic






    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Resize")) {
            border.reSizeBorder(border.getM()+5, border.getN()+5);
        } if (cmd.equals("Reset")) {
            border.reSizeBorder(5, 5);
        } if (cmd.equals("Run Brute Force Method")) {
            field.surveyGrassBinaryBF();
        }
    }





}
