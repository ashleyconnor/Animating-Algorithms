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
        
        knapsackModel = createModelData();
        setColumnHeaders();
        knapsackTableModel = new DefaultTableModel(knapsackModel, columnHeaders);
        table.setModel(knapsackTableModel);
        
    }
    
        @Override
      public void run() {
            try {
                // *** initialize
                //intialiseModel();
                numItems = value.size() - 1;
                
                sleepTime = speed * 100;
                int actionTime = speed * 5;

                //MESSAGE: Zero out the first row
                textArea.setText("Zero out the first row.");
                Thread.sleep(1);
                
                //zero out first row
                for (int w = 0; w <= capacity; w++) {
                    knapsackTableModel.setValueAt(new Integer(0), 0, w);
                    Thread.sleep(actionTime);
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
                        
                        int a = (Integer) knapsackTableModel.getValueAt(k - 1, w - weight.get(k));
                        int b = (Integer) knapsackTableModel.getValueAt(k - 1, w);

                        if (value.get(k) + a > b) {
                            //knapsackModel[k][w] = value.get(k) + knapsackModel[k - 1][w - weight.get(k)];
                            
                            int c = (Integer) knapsackTableModel.getValueAt(k - 1, w - weight.get(k));
                            
                            knapsackTableModel.setValueAt(new Integer(value.get(k) + c),k,w);

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

                        //MESSAGE: We take hte lar
                    }
                }
            
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
            
            //Clear arrays for new values
            value.clear();
            weight.clear();
            //printModel();
    }
      
      //why there is no standard library method to do this!!!?
      public Integer[][] createModelData() {
          
          knapsackModel = new Integer[value.size()][capacity + 1];
          
          return knapsackModel;
      }
      
      public void setColumnHeaders() {
          
          columnHeaders = new String[capacity + 1];
          
          for (int i = 0; i <= capacity; i++) {
              
              columnHeaders[i] = i + "";
          }
      }
      
      public void intialiseModel() {
          
          numItems = value.size() - 1;
          
          for (int i = 0; i < knapsackModel.length; i++) {
              for (int j = 0; j < knapsackModel[i].length; j++) {
                  knapsackModel[i][j] = new Integer(0);
              }
          }
      }
      
      public void printModel() {
          
          for (int i = 0; i < knapsackModel.length; i++) {
              for (int j = 0; j < knapsackModel[i].length; j++) {
                  System.out.println(knapsackModel[i][j]);
              }
          }
      }
      
    public void setSpeed(int speed) {
        
        this.speed = speed;
    }
}