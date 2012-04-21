/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ashley Connor
 */
public final class KnapsackAlgorithm implements Runnable {
    
    int numItems, capacity, speed, actionTime, sleepTime, textTime;
      
    int[] current, compareA, compareB, compareC;
      
    JPanel knapsackGUI;
    ArrayList<Integer> value, weight;
    JTable table;
    JTextArea textArea;
    JList valueInputList;
    String[] columnHeaders;
    Integer[][] knapsackModel;
    DefaultTableModel knapsackTableModel;

    public KnapsackAlgorithm(Knapsack knapsackGUI) {
        
        this.knapsackGUI = knapsackGUI;
        
        table = knapsackGUI.getCalculationsTable();
        textArea = knapsackGUI.getCalculationsTextArea();
        valueInputList = knapsackGUI.getValueInputList();
        value = knapsackGUI.getValueList();
        weight = knapsackGUI.getWeightList();
        capacity = knapsackGUI.getCapacity();
        speed = knapsackGUI.getSpeed();
        
        //set the custom highlight renderer
        table.setDefaultRenderer(Object.class, new CustomRenderer());
        
        //Create model to be used by tabls
        knapsackModel = createDataModel();
        setColumnHeaders();
        
        //Assign model to table
        knapsackTableModel = new DefaultTableModel(knapsackModel, columnHeaders);
        table.setModel(knapsackTableModel);
        
        //Set algorithm speeds
        setSpeed(speed);
        
        //Configure the hightlighted cells
        //initialise all values to -1 so that no colours appear on the table
        current = new int[2];
        current[0] = -1;
        current[1] = -1;
        
        compareA = new int[2];
        compareA[0] = -1;
        compareA[1] = -1;
        
        compareB = new int[2];
        compareB[0] = -1;
        compareB[1] = -1;
        
        compareC = new int[2];
        compareC[0] = -1;
        compareC[1] = -1;
        
    }
    
    @Override
    public void run() {
    try {
        numItems = value.size() - 1;


        //MESSAGE: Zero out the first row
        textArea.setText("First we zero out the first row.");

        for (int w = 0; w <= capacity; w++) {
            knapsackTableModel.setValueAt(new Integer(0), 0, w);
            Thread.sleep(actionTime);   //calm down dear it's an animation
        }
        
            textArea.setText("For each of our " + numItems + " items.");
            Thread.sleep(textTime);

        // *** Now do the work!
        for (int k = 1; k <= numItems; k++) {

            //if the capacity is larger or equal to the weight of the item
            for (int w = capacity; w >= weight.get(k); w--) {
                
                //highlight the current cell we are processing
                current[0] = k;
                current[1] = w;
                table.repaint();

                
                textArea.setText("Item "+ k +" weight: " + weight.get(k) + " is less than the current capacity " + w);
                
                Thread.sleep(textTime);
                
                if (value.get(k) + modelValueAt(k-1, w - weight.get(k)) > modelValueAt(k-1, w)) {
                    
                    //highlight cells to be compared
                    compareA[0] = k-1; 
                    compareA[1] = w - weight.get(k);
                    
                    compareB[0] = k-1;
                    compareB[1] = w;
                    table.repaint();

                    textArea.setText("Item " + k + " value: " + value.get(k) + " + value at (" + (k - 1) + "," + (w - weight.get(k)) + "): " + modelValueAt(k-1, w-weight.get(k)));
                    textArea.append("\nis LARGER than the value at (" + (k-1) + "," + w + "): " + modelValueAt(k-1, w));
                    textArea.append("\nwe therefore use the larger value: " + (value.get(k) + modelValueAt(k-1, w - weight.get(k))));
                    
                    Thread.sleep(textTime);
                    
                    //Set colors for the 2 values in conditional

                    knapsackTableModel.setValueAt(new Integer(value.get(k) + modelValueAt(k - 1, w - weight.get(k))),k,w);
                    
                    //Highlight cell that will be set

                } else {
                    
                    textArea.setText("Item " + k + " value: " + value.get(k) + " + value at (" + (k - 1) + "," + (w - weight.get(k)) + "): " + modelValueAt(k-1, w-weight.get(k)));
                    textArea.append("\nis SMALLER than the value: " + modelValueAt(k-1, w));
                    textArea.append("\nwe therefore use the larger value: " + (modelValueAt(k-1, w)));
                    
                    Thread.sleep(textTime);
                    
                    knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k-1, w), k, w);
                    
                    //Highlight cell that will be set
                }
            }

            textArea.setText("Now we loop through capacities smaller than the items weight\nand reuse the maximum values previously stored.");
            //textArea.append("\n\n");
            
            Thread.sleep(textTime);
            
            for (int w = 0; w < weight.get(k); w++) {
                Thread.sleep(actionTime);
                knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k-1, w), k, w);
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
    
    //Gets the value at column x, row y of the model and returns an int
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
        actionTime = 31250 / speed;
        sleepTime  = 50000 / speed;
        
        if(speed <= 50)
            textTime = 4000;
        else if (speed > 50 && speed < 80)
            textTime = 2000;
        else
            textTime = 1000;
    }
    
class CustomRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel e = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if((row == current[0]) && (column == current[1]))
            e.setBackground(java.awt.Color.RED);
        else if((row == compareA[0]) && (column == compareA[1])) {
            e.setBackground(java.awt.Color.BLUE);
        }
        else if((row == compareB[0]) && (column == compareB[1])) {
            e.setBackground(java.awt.Color.GREEN);
        }
        else if((row == compareC[0]) && (column == compareC[1])) {
            e.setBackground(java.awt.Color.ORANGE);
        }
        else {
            e.setBackground(null);
        }
        return e;
    }
}
}