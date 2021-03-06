package animating.algorithms;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * This class contains the algorithm used to find the LCS of 2 strings.
 * @author Ashley Connor <ash.connor@gmail.com>
 */
public final class LCSAlgorithm implements Runnable {

    //static attributes
    private static final Direction UP = new Direction("UP");
    private static final Direction LEFT = new Direction("LEFT");
    private static final Direction UP_AND_LEFT = new Direction("UP AND LEFT");
    //attributes
    LCS lcsGUI;
    long textTime, actionTime;
    JTable topTable, sideTable, coreTable;
    String[] coreHeaders, topHeaders, sideHeaders;
    String[][] top, side;
    JTextPane calculationsArea;
    //Table Models
    DefaultTableModel LCSTopModel, LCSSideModel, LCSCoreModel;
    private int[] redCell, cyanCell, greenCell, yellowCell;
    /**
     * The length of an LCS of a subproblem.
     * <code>c[i][j]</code> is the length of an LCS of prefixes
     * <i>X</i><sub><i>i</i></sub> and <i>Y</i><sub><i>j</i></sub>, for 0
     * &#x2264; <i>i</i> &#x2264; <i>m</i>-1 and 0 &#x2264; <i>j</i> &#x2264;
     * <i>n</i>-1.
     */
    private Integer[][] core, core2;
    /**
     * The table entry used in constructing an LCS of prefixes
     * <i>X</i><sub><i>i</i></sub> and <i>Y</i><sub><i>j</i></sub>, for 0
     * &#x2264; <i>i</i> &#x2264; <i>m</i>-1 and 0 &#x2264; <i>j</i> &#x2264;
     * <i>n</i>-1.
     */
    private Direction[][] b;
    /**
     * How many entries are in <i>X</i>.
     */
    private final int m;
    /**
     * How many entries are in <i>Y</i>.
     */
    private final int n;
    /**
     * The input X. Needed for reconstructing an optimal solution.
     */
    private final String x, y;
    private int speed;

    /**
     * 
     * @param lcsGUI
     */
    public LCSAlgorithm(LCS lcsGUI) {

        this.lcsGUI = lcsGUI;

        calculationsArea = lcsGUI.getCalculationsTextPane();

        x = lcsGUI.getStringA();
        y = lcsGUI.getStringB();

        m = x.length();                     //ROW
        n = y.length();                     //COLUMN

        core = new Integer[m + 1][n + 1];

        core2 = new Integer[n + 1][m + 1];

        b = new Direction[m + 1][n + 1];

        //dummy headers
        coreHeaders = new String[m + 1];

        //Get tables from GUI
        this.topTable = lcsGUI.getTopTable();
        this.sideTable = lcsGUI.getSideTable();
        this.coreTable = lcsGUI.getCalculationsTable();

        //initialise
        redCell = new int[2];
        cyanCell = new int[2];
        greenCell = new int[2];
        yellowCell = new int[2];

        //speed
        speed = lcsGUI.getSpeed();
        setSpeed(speed);

        resetCellColors();

        //create array for info in top and side columns
        side = new String[n + 1][2];
        sideHeaders = new String[2];

        for (int i = 0; i < side.length; i++) {
            side[i][0] = "" + i;
            if (i == 0) {
                side[0][1] = "yi";
            } else {
                side[i][1] = "" + y.charAt(i - 1);
            }
        }

        top = new String[2][m + 1];
        topHeaders = new String[m + 1];

        for (int i = 0; i < top[0].length; i++) {
            top[0][i] = "" + i;
            if (i == 0) {
                top[1][0] = "xi";
            } else {
                top[1][i] = "" + x.charAt(i - 1);
            }
        }


        //TODO: SETUP TABLE MODEL HERE
        LCSCoreModel = new DefaultTableModel(core2, coreHeaders);
        LCSTopModel = new DefaultTableModel(top, topHeaders);
        LCSSideModel = new DefaultTableModel(side, sideHeaders);

        topTable.setModel(LCSTopModel);
        sideTable.setModel(LCSSideModel);
        coreTable.setModel(LCSCoreModel);

        //TODO: CUSTOM REDERERS FOR TOP AND SIDE
        topTable.setDefaultRenderer(Object.class, new TopCustomRenderer());
        sideTable.setDefaultRenderer(Object.class, new SideCustomRenderer());

        coreTable.setDefaultRenderer(Object.class, new CoreCustomRenderer());

    }

    /**
     * The run method runs the algorithm on strings a & b.
     */
    @Override
    public void run() {
        try {

            System.out.println(x);
            System.out.println(y);
            //zero out the first row
            //set some message

            calculationsArea.setText(
                    "<html><div style=\"font-size: 15pt; text-align: center\">"
                    + "First we zero out the first row and column."
                    + "</div></html>");
            Thread.sleep(textTime);

            //zero out the first row
            for (int i = 0; i <= m; i++) {
                setAt(new Integer(0), 0, i);
                core[i][0] = new Integer(0);
                setCell(redCell, 0, i);
                coreTable.repaint();
                Thread.sleep(actionTime);
            }

            //zero out the first column
            for (int j = 0; j <= n; j++) {
                core[0][j] = new Integer(0);
                setAt(new Integer(0), j, 0);
                setCell(redCell, j, 0);
                coreTable.repaint();
                Thread.sleep(actionTime);
            }

            resetCellColors();

            //for all characters in m
            for (int i = 1; i <= m; i++) {
                //for all characters in n
                for (int j = 1; j <= n; j++) {
                    
                    resetCellColors();

                    setCell(cyanCell, j, 1);
                    setCell(greenCell, 1, i);
                    setCell(redCell, j, i);

                    calculationsArea.setText(
                            "<html><div style=\"font-size: 20pt; text-align: center\">"
                            + "We check to see if "
                            + "<span style=\"background-color: #00ff00\">" + index(x, i) + "</span>" + //green
                            " matches <span style=\"background-color: #00ffff\">" + index(y, j) + "</span>"
                            + "</div></html>");

                    Thread.sleep(textTime);

                    //if the 2 characters match
                    if (index(x, i) == index(y, j)) {

                        setCell(yellowCell, i - 1, j - 1);
                        core[i][j] = new Integer(core[i - 1][j - 1].intValue() + 1);
                        setAt(new Integer(valueAt(j - 1, i - 1) + 1), j, i);
                        b[i][j] = UP_AND_LEFT;
                        
                        calculationsArea.setText(
                                "<html><div style=\"font-size: 20pt; text-align: center\">"
                                + "They DO match!<br />So we get the current match total " +
                                "<span style=\"background-color: #fff000\">" + valueAt(j - 1, i - 1) + "</span>" + //yellow
                                " and add <strong>1</strong> bringing our match total to <span style=\"background-color: #ff0000\">" + ((valueAt(j - 1, i - 1)) + 1) + "</span>" + //red
                                "</div></html>");

                        Thread.sleep(textTime);


                    } //if the 2 characters don't match
                    else {
                        //Check to see if a higher match has been made already
                        //If so assign that higher match to this cell and label it up
                        calculationsArea.setText(
                                "<html><div style=\"font-size: 20pt; text-align: center\">" + 
                                "They DON'T match!<br />So we check to see if a higher match has been made already." +
                                "</div></html>");
                        
                        Thread.sleep(textTime);
                        
                        if (valueAt(j, i - 1) >= valueAt(j - 1, i)) {
                            
                            calculationsArea.setText(
                                "<html><div style=\"font-size: 20pt; text-align: center\">"
                                + "This value "
                                + "<span style=\"background-color: #fff000\">" + valueAt(j, i - 1) + "</span>" + //yellow
                                " is <strong>&#8805;</strong> <span style=\"background-color: #ff0000\">" + (valueAt(j - 1, i)) + "</span>" + //red
                                "</div></html>");

                            setCell(yellowCell, j, i - 1);
                            core[i][j] = core[i - 1][j];
                            setAt(valueAt(j, i - 1), j, i);
                            b[i][j] = UP;
                            
                            Thread.sleep(textTime);
                            
                        } //Else the previous match is less than the current match
                        //assign the value from the left and assign label left
                        else {
                            
                            calculationsArea.setText(
                                "<html><div style=\"font-size: 20pt; text-align: center\">"
                                + "This value "
                                + "<span style=\"background-color: #fff000\">" + valueAt(j, i - 1) + "</span>" + //yellow
                                " is <strong>&#60;</strong> <span style=\"background-color: #ff0000\">" + (valueAt(j - 1, i)) + "</span>" + //red
                                "</div></html>");

                            setCell(yellowCell, j - 1, i);
                            setAt(valueAt(j - 1, i), j, i);
                            core[i][j] = core[i][j - 1];
                            b[i][j] = LEFT;
                            
                            Thread.sleep(textTime);
                        }
                    }
                }
            }
            
            calculationsArea.setText(
                "<html><div style=\"font-size: 20pt; text-align: center\">"+ 
                "The final substring of:<br /> " + x + ", " + y + 
                "<br />= " + toString() +
                "</div></html>");
            resetCellColors();
            System.out.println(toString());

        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }

    /**
     * Computes an LCS of two strings, storing the result in the instance
     * variables. The instance variables are assumed to be already allocated.
     * @param x The string <i>X</i>.
     * @param y The string <i>Y</i>.
     */
    /**
     * Returns a given character from a
     * <code>String</code>. Compensates for character positions in a
     * <code>String</code> being indexed from 0, rather than from 1 as in
     * Section 15.4.
     *
     * @param z A
     * <code>String</code>.
     * @param k An index into
     * <code>z</code>, but starting from 1.
     * @return Returns the
     * <code>k</code>th character of
     * <code>z</code>.
     */
    private char index(String z, int k) {
        return z.charAt(k - 1);
    }

    /**
     * Returns an LCS of prefixes <i>X</i><sub><i>i</i></sub> and
     * <i>Y</i><sub><i>j</i></sub>. Implements the Print-LCS procedure on page
     * 355.
     *
     * @param i Index into <i>X</i>.
     * @param j Index into <i>Y</i>.
     * @return A
     * <code>String</code> that is an LCS of <i>X</i><sub><i>i</i></sub> and
     * <i>Y</i><sub><i>j</i></sub>.
     */
    private String printLCS(int i, int j) {
        if (i == 0 || j == 0) {
            return "";
        }

        if (b[i][j] == UP_AND_LEFT) {
            return printLCS(i - 1, j - 1) + index(x, i);
        } else if (b[i][j] == UP) {
            return printLCS(i - 1, j);
        } else {
            return printLCS(i, j - 1);
        }
    }

    /**
     * Returns an LCS of <i>X</i> and <i>Y</i> as a
     * <code>String</code>.
     * @return 
     */
    @Override
    public String toString() {
        return printLCS(m, n);
    }

    /**
     * Set the speed attribute.
     * @param speed
     */
    public void setSpeed(int speed) {   
        this.speed = speed;
        actionTime = 31250 / speed;

        if (speed <= 50) {
            textTime = 4000;
        } else if (speed > 50 && speed < 80) {
            textTime = 3000;
        } else {
            textTime = 2000;
        }
    }

    /**
     * Get the value of the cell from the table at column x and row y. Return the primitive
     * integer.
     * @param x
     * @param y
     * @return int
     */
    private int valueAt(int x, int y) {

        return (Integer) coreTable.getValueAt(x, y);
    }

    /**
     * Set the <code>Integer</code> value Z in the cell at column x and row y
     * @param z
     * @param x
     * @param y 
     */
    private void setAt(Integer z, int x, int y) {

        coreTable.setValueAt(z, x, y);
    }

    /**
     * Reset all the colours used to highlight the table
     */
    private void resetCellColors() {
        redCell[0] = -1;
        redCell[1] = -1;

        cyanCell[0] = -1;
        cyanCell[1] = -1;

        greenCell[0] = -1;
        greenCell[1] = -1;

        yellowCell[0] = -1;
        yellowCell[1] = -1;

        coreTable.repaint();
        topTable.repaint();
        sideTable.repaint();
    }

    /**
     * Set colour <code>array</code> at position x,y in the table
     *
     * @param colourArray
     * @param x
     * @param y
     */
    private void setCell(int[] cell, int x, int y) {
        cell[0] = x;
        cell[1] = y;

        coreTable.repaint();
        topTable.repaint();
        sideTable.repaint();
    }

    /**
     * Inner class for a static enum directions in a
     * two-dimensional array.
     */
    private static class Direction {

        /**
         * The direction indicated.
         */
        private final String name;

        /**
         * Creates a new
         * <code>Direction</code>.
         *
         * @param name The name to store and use.
         */
        public Direction(String name) {
            this.name = name;
        }

        /**
         * Returns the
         * <code>String</code> representation of this
         * <code>Direction</code>.
         */
        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * A custom class for highlighting of the top table with a green cell
     */
    class TopCustomRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel e = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row == greenCell[0] && column == greenCell[1]) {
                e.setBackground(Color.GREEN);
            } else {
                e.setBackground(null);
            }
            return e;
        }
    }
    /**
     * A custom class for highlighting of the side table with a cyan cell 
     */
    class SideCustomRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel e = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row == cyanCell[0] && column == cyanCell[1]) {
                e.setBackground(Color.CYAN);
            } else {
                e.setBackground(null);
            }
            return e;
        }
    }

    /**
     * A custom class for highlighting of the core table with a red cell
     */
    class CoreCustomRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel e = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row == redCell[0] && column == redCell[1]) {
                e.setBackground(Color.RED);
                return e;
            }
            if (row == yellowCell[0] && column == yellowCell[1]) {
                e.setBackground(Color.yellow);
                return e;
            } else {
                e.setBackground(null);
            }
            return e;
        }
    }
}