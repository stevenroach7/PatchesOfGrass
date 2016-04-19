package patchOGrass;

import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Steven on 4/16/16.
 */
public class VisualProgram extends GraphicsProgram {


    private Field field;


//    public static void main(String[] args) {
//        VisualProgram vizProgram = new VisualProgram();
//        vizProgram.init();
//
//    }


    public void init() {

        // Add Buttons
        JButton runBF = new JButton("Brute Force");
        runBF.setSize(80, 40);
        add(runBF, 0, 0);

        // Add Field
       field = new Field(25, 25);
        add(field, 10, 200);

        addActionListeners();

//        runBF.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                field.surveyGrassBF(5, 5);
//            }
//        });


    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Run Brute Force Visualization")) {
            field.surveyGrassBF(5, 5);
            System.out.println("Test");
        }
    }



}
