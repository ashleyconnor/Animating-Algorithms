/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ashley Connor
 */
public final class KnapsackAlgorithm implements Runnable {

    int numItems, capacity, speed, actionTime, sleepTime, textTime;
    int[] redCell, cyanCell, greenCell, orangeCell;
    Knapsack knapsackGUI;
    ArrayList<Integer> value, weight, itemsUsed;
    JTable table;
    JTextPane calculationsArea;
    JList valueInputList;
    String[] columnHeaders;
    Integer[][] knapsackModel;
    DefaultTableModel knapsackTableModel;
    private int weightLeft;

    public KnapsackAlgorithm(Knapsack knapsackGUI) {

        this.knapsackGUI = knapsackGUI;

        table = knapsackGUI.getCalculationsTable();
        calculationsArea = knapsackGUI.getCalculationsTextPane();
        valueInputList = knapsackGUI.getValueInputList();
        
        value = knapsackGUI.getValueList();
        weight = knapsackGUI.getWeightList();
        itemsUsed = knapsackGUI.getItemsUsed();
        
        capacity = knapsackGUI.getCapacity();
        speed = knapsackGUI.getSpeed();

        //set the custom highlight renderer
        table.setDefaultRenderer(Object.class, new CustomRenderer());

        //set calculations table to resize false
        table.getTableHeader().setResizingAllowed(false);

        //Create model to be used by tabls
        knapsackModel = createDataModel();
        setColumnHeaders();

        //Assign model to table
        knapsackTableModel = new DefaultTableModel(knapsackModel, columnHeaders);
        table.setModel(knapsackTableModel);

        //Set algorithm speeds
        setSpeed(speed);

        //Configure the hightlighted cells
        //initialise all values to -1 so that no colours initially appear on the table
        redCell = new int[2];
        cyanCell = new int[2];
        greenCell = new int[2];
        orangeCell = new int[2];
        resetCellColors();

    }

    @Override
    public void run() {
        try {
            numItems = value.size() - 1;


            //MESSAGE: Zero out the first row
            calculationsArea.setText(
                    "<html><div style=\"font-size: 15pt; text-align: center\">" +
                    "First, we zero out the first row." +
                    "</div></html>");

            for (int w = 0; w <= capacity; w++) {
                setCell(orangeCell, 0, w);
                knapsackTableModel.setValueAt(new Integer(0), 0, w);
                Thread.sleep(actionTime);   //calm down dear it's an animation
            }

            resetCellColors();

            calculationsArea.setText(
                    "<html><div style=\"font-size: 15pt; text-align: center\">" +
                    "Next we loop through our " + numItems + " items.<br />" +
                    "to calculate our effecient solution." +
                    "</div></html>");
            Thread.sleep(textTime);

            // *** Now do the work!
            for (int k = 1; k <= numItems; k++) {
                
                //highlight current item
                knapsackGUI.setListLine(k-1);
                
                //if the capacity is larger or equal to the weight of the item
                for (int w = capacity; w >= weight.get(k); w--) {

                    //
                    calculationsArea.setText(
                            "<html><div style=\"font-size: 15pt; text-align: center\">"
                            + "Item <strong>" + k + "</strong> with weight <strong>" + weight.get(k) + "</strong> is less than the current capacity <strong>" + w + "</strong><br />"
                            + "leaving us with <span style=\"background-color: #00ffff\">" + (w - weight.get(k)) + "</span> space. <br />"
                            + "</div></html>");
                    
                    setCell(cyanCell, k - 1, w - weight.get(k));

                    Thread.sleep(textTime);

                    //highlight current cell and cells to be compared
                    setCell(redCell, k, w);
                    setCell(greenCell, k - 1, w);

                    if (value.get(k) + modelValueAt(k - 1, w - weight.get(k)) > modelValueAt(k - 1, w)) {

                        //message X html formatted
                        calculationsArea.setText(
                                "<html><div style=\"font-size: 15pt; text-align: center\">"
                                + "Item: <strong>" + k + "</strong> value: "
                                + "<span style=\"background-color: #fff000\">" + value.get(k) + "</span> + " + //yellow
                                "max value for space left over "
                                + "<span style=\"background-color: #00ffff\"> " + modelValueAt(k - 1, w - weight.get(k)) + "</span>" + //cyan
                                "\nis <strong>LARGER</strong> than the value "
                                + "<span style=\"background-color: #00ff00\">" + modelValueAt(k - 1, w) + "</span>" + "<br />" + //green
                                "\nwe therefore use the larger value: "
                                + "<span style=\"background-color: #fff000\">" + value.get(k) + "</span>" + //yellow
                                " + <span style=\"background-color: #00ffff\">" + modelValueAt(k - 1, w - weight.get(k)) + "</span>" + //cyan
                                " = <span style=\"background-color: #ff0000\">" + (value.get(k) + modelValueAt(k - 1, w - weight.get(k))) + "</span>"
                                + "</div></html>");

                        knapsackTableModel.setValueAt(new Integer(value.get(k) + modelValueAt(k - 1, w - weight.get(k))), k, w);

                        Thread.sleep(textTime * 2);
                        resetCellColors();

                    } else {

                        //TODO: FORMAT TEXT IN HTML
                        calculationsArea.setText(
                                "Item " + k + " value: " + value.get(k) + " + value at (" + (k - 1) + "," + (w - weight.get(k)) + "): " + modelValueAt(k - 1, w - weight.get(k))
                                + "\nis SMALLER than the value: " + modelValueAt(k - 1, w)
                                + "\nwe therefore use the larger value: " + (modelValueAt(k - 1, w)));

                        knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k - 1, w), k, w);

                        Thread.sleep(textTime * 2);
                        resetCellColors();
                    }
                }

                //remove the colors from all cells
                resetCellColors();

                //TODO: FORMAT IN HTML
                
                if(weight.get(k) - 1 > 5) {
                    weightLeft = 5;
                }
                else
                    weightLeft = weight.get(k) - 1;
                calculationsArea.setText(
                        "<html><div style=\"font-size: 15pt; text-align: center\">" +
                        "The item weight <span style=\"background-color: #fff000\">" + weight.get(k) + "</span> is larger than the space we have left " + //yellow
                        "<strong>" + weightLeft + "</strong>" +
                        " so we reuse the maximum values previously calculated." +
                        "</div></html>"
                        );
                
                //Highlight cell to indicate were to reuse values
                setCell(redCell, k, weightLeft);
                
                Thread.sleep(textTime);

                for (int w = 0; w < weight.get(k) && w <= capacity; w++) {
                    setCell(redCell, k, w);
                    setCell(greenCell, k - 1, w);
                    knapsackTableModel.setValueAt(knapsackTableModel.getValueAt(k - 1, w), k, w);
                    Thread.sleep(actionTime);
                    resetCellColors();
                }
            }
            
            //Loop throught table and highlight items used
            for (int i = numItems, remainingWeight = capacity; i > 0; i--) {
                
                if (remainingWeight >= weight.get(i)) {
                    
                    if (modelValueAt(i, remainingWeight) == (value.get(i) + modelValueAt(i-1, (remainingWeight - weight.get(i))))) {
                        itemsUsed.add(new Integer(i));
                        remainingWeight -= weight.get(i);
                    }
                }
            }
            
            //TODO: WRITE MESSAGE DISPLAYING FINAL VALUES
            Collections.sort(itemsUsed);
            
            calculationsArea.setText(
                    "<html><div style=\"font-size: 15pt; text-align: center\">" +
                    "The items used in our solution are: " +
                    itemsUsed.toString() + "<br />" +
                    "with a total value of: <strong>" + modelValueAt(numItems, capacity) + "</strong>" +
                    "</div></html>"
                    );
            
            calculationsArea.repaint();
            valueInputList.repaint();


        } catch (InterruptedException ex) {
            System.out.println("Interrupted.");
        }

        //Clear arrays for new values
        value.clear();
        weight.clear();    
        knapsackGUI.setListLine(-1);
    }

    //Gets the value at column x, row y of the model and returns an int
    private int modelValueAt(int x, int y) {

        return (Integer) knapsackTableModel.getValueAt(x, y);
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

    //color a cell at x,y position
    private void setCell(int[] array, int x, int y) {
        array[0] = x;
        array[1] = y;
        table.repaint();
    }

    //clear the colors on all cells by setting the color position to -1 (not in JTable)
    private void resetCellColors() {
        redCell[0] = -1;
        redCell[1] = -1;

        cyanCell[0] = -1;
        cyanCell[1] = -1;

        greenCell[0] = -1;
        greenCell[1] = -1;

        orangeCell[0] = -1;
        orangeCell[1] = -1;

        table.repaint();
    }

    //set the speed of the animation by changing the sleep ms value
    public void setSpeed(int speed) {

        this.speed = speed;
        actionTime = 31250 / speed;
        sleepTime = 50000 / speed;

        if (speed <= 50) {
            textTime = 4000;
        } else if (speed > 50 && speed < 80) {
            textTime = 3000;
        } else {
            textTime = 2000;
        }
    }

    class CustomRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel e = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((row == redCell[0]) && (column == redCell[1])) {
                e.setBackground(java.awt.Color.RED);
            } else if ((row == cyanCell[0]) && (column == cyanCell[1])) {
                e.setBackground(java.awt.Color.CYAN);
            } else if ((row == greenCell[0]) && (column == greenCell[1])) {
                e.setBackground(java.awt.Color.GREEN);
            } else if ((row == orangeCell[0]) && (column == orangeCell[1])) {
                e.setBackground(java.awt.Color.ORANGE);
            } else {
                e.setBackground(null);
            }
            return e;
        }
    }
}