/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ash
 */
public final class KnapsackAlgorithm implements Runnable {
    
      int numItems, capacity, speed, sleepTime;
      ArrayList<Integer> value, weight;
      JTable table;
      JTextArea textArea;
      String[] columnHeaders;
      Integer[][] knapsackModel;
      DefaultTableModel knapsackTableModel;

    public KnapsackAlgorithm(JTable table, JTextArea textArea, ArrayList value, ArrayList weight, int capacity, int speed) {
        
        this.table         = table;
        this.textArea      = textArea;
        this.value         = value;
        this.weight        = weight;
        this.capacity      = capacity;          //add one for blank row
        this.speed         = speed;
        
        //Create model to be used by tabls
        knapsackModel = createDataModel();
        setColumnHeaders();
        
        //Assign model to table
        knapsackTableModel = new DefaultTableModel(knapsackModel, columnHeaders);
        table.setModel(knapsackTableModel);
        
    }
    
    @Override
    public void run() {
    try {
        numItems = value.size() - 1;

        sleepTime = speed * 100;
        int actionTime = speed * 5;

        //MESSAGE: Zero out the first row
        textArea.setText("Zero out the first row.");
        Thread.sleep(1);

        for (int w = 0; w <= capacity; w++) {
            knapsackTableModel.setValueAt(new Integer(0), 0, w);
            Thread.sleep(actionTime);   //calm down dear it's an animation
        }

        knapsackTableModel.fireTableDataChanged();

        //MESSAGE: Loop through all X items
        textArea.setText("Loop through all " + numItems + " items");
        //Thread.sleep(sleepTime);

        // *** Now do the work!
        for (int k = 1; k <= numItems; k++) {
            System.out.println("Weight at " + k + ": " + weight.get(k));

            //MESSAGE: The weight of item X is Y

            for (int w = capacity; w >= weight.get(k); w--) {

                //MESSAGE: Check if the item can fit in the weight.


                if (value.get(k) + modelValueAt(k-1, w- weight.get(k)) > modelValueAt(k-1, w)) {
                    //knapsackModel[k][w] = value.get(k) + knapsackModel[k - 1][w - weight.get(k)];

                    knapsackTableModel.setValueAt(new Integer(value.get(k) + modelValueAt(k - 1, w - weight.get(k))),k,w);

                    //MESSAGE: Since X is more than Y we use X

                } else {
                    //knapsackModel[k][w] = knapsackModel[k - 1][w];
                    knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k-1, w), k, w);

                    //MESSAGE: Since Y is bigger than X we use Y
                }
            }
            for (int w = 0; w < weight.get(k); w++) {
                //knapsackModel[k][w] = knapsackModel[k - 1][w];
                knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k-1, w), k, w);

                //MESSAGE: ???
            }
        }

    } catch (InterruptedException ex) {
        
        System.out.println("Interrupted.");
    
    }

        //Clear arrays for new values
        value.clear();
        weight.clear();
    }
      
      //why there is no standard library method to do this!!!?
    
    //Gets the value at column x, row y and returns an int
    private int modelValueAt(int x, int y) {

        return (Integer) knapsackTableModel.getValueAt(x,y);
    }

    //creates a model with capacity * columns and numItems * rows
    private Integer[][] createDataModel() {
          
          knapsackModel = new Integer[value.size()][capacity + 1];
          
          return knapsackModel;
      }
      
    //set the value of the column headers 1...capacity
    private void setColumnHeaders() {

        columnHeaders = new String[capacity + 1];

        for (int i = 0; i <= capacity; i++) {

            columnHeaders[i] = i + "";
        }
    }

    //set the speed of the animation by changing the sleep ms value
    public void setSpeed(int speed) {

        this.speed = speed;
    }
}