/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ash
 */
public final class LCSAlgorithm implements Runnable {

    //static attributes
    private static final Direction UP = new Direction("UP");
    private static final Direction LEFT = new Direction("LEFT");
    private static final Direction UP_AND_LEFT = new Direction("UP AND LEFT");
    
    
    //attributes
    LCS lcsGUI;
    long textTime;
    JTable topTable, sideTable, coreTable;
    
    //Table Models
    DefaultTableModel LCSTopModel, LCSSideModel, LCSCoreModel;
    
    /** The length of an LCS of a subproblem.  <code>c[i][j]</code> is
     * the length of an LCS of prefixes <i>X</i><sub><i>i</i></sub>
     * and <i>Y</i><sub><i>j</i></sub>, for 0 &#x2264; <i>i</i>
     * &#x2264; <i>m</i>-1 and 0 &#x2264; <i>j</i> &#x2264;
     * <i>n</i>-1. */
    private Integer[][] core;

    /** The table entry used in constructing an LCS of prefixes
     * <i>X</i><sub><i>i</i></sub> and <i>Y</i><sub><i>j</i></sub>,
     * for 0 &#x2264; <i>i</i> &#x2264; <i>m</i>-1 and 0 &#x2264;
     * <i>j</i> &#x2264; <i>n</i>-1. */
    private Direction[][] b;

    /** How many entries are in <i>X</i>. */
    private final int m;

    /** How many entries are in <i>Y</i>. */
    private final int n;

    /** The input X.  Needed for reconstructing an optimal solution. */
    private final String x, y;
    private int speed;

    public LCSAlgorithm(LCS lcsGUI) {

        this.lcsGUI = lcsGUI;
        x = lcsGUI.getStringA();
        y = lcsGUI.getStringB();
        
        n = x.length();                     //ROW
	m = y.length();                     //COLUMN
        
	core = new Integer[m+1][n+1];
	b = new Direction[m+1][n+1];
        
        //dummy headers
        String[] coreHeaders = new String[m+1];
        
        for(Integer[] array : core) {
            System.out.println(Arrays.toString(array));
        }
        
        //Get tables from GUI
        this.topTable = lcsGUI.getTopTable();
        this.sideTable = lcsGUI.getSideTable();
        this.coreTable = lcsGUI.getCalculationsTable();
        
        //create array for info in top and side columns
        String[][] side = new String[n+1][2];
        String[] sideHeaders = new String[2];
        
        for(int i = 0; i < side.length; i++) {
            side[i][0] = "" + i;
            if(i == 0)
                side[0][1] = "xi";
            else
                side[i][1] = "" + x.charAt(i-1);
        }
        
        String[][] top = new String[2][m+1];
        String[] topHeaders = new String[m+1];
        
        for(int i = 0; i < top[0].length; i++) {
            top[0][i] = "" + i;
            if(i == 0)
                top[1][0] = "yi";
            else
                top[1][i] = "" + y.charAt(i-1);
        }
        
        
        //TODO: SETUP TABLE MODEL HERE
        LCSCoreModel = new DefaultTableModel(core, coreHeaders);
        LCSTopModel = new DefaultTableModel(top,topHeaders);
        LCSSideModel = new DefaultTableModel(side, sideHeaders);
        
        topTable.setModel(LCSTopModel);
        sideTable.setModel(LCSSideModel);
        coreTable.setModel(LCSCoreModel);
        coreTable.setTableHeader(null);
        coreTable.repaint();
    }

    @Override
    public void run() {
        try {

            //zero out the first row
            //set some message
            Thread.sleep(textTime);
            
            for (int i = 0; i <= m; i++) {
                core[i][0] = new Integer(0);
            }

            //zero out the first column
            for (int j = 0; j <= n; j++) {
                core[0][j] = new Integer(0);
            }

            //for all characters in m
            for (int i = 1; i <= m; i++) {
                //for all characters in n
                for (int j = 1; j <= n; j++) {

                    //if the 2 characters match
                    if (index(x, i) == index(y, j)) {

                        //get the current match number and add one
                        //assign this to the 
                        core[i][j] = new Integer(core[i - 1][j - 1].intValue() + 1);
                        b[i][j] = UP_AND_LEFT;
                    } //if the 2 characters don't match
                    else {
                        //Check to see if a higher match has been made already
                        //If so assign that higher match to this cell and label it up
                        if (core[i - 1][j] >= core[i][j - 1]) {
                            core[i][j] = core[i - 1][j];
                            b[i][j] = UP;
                        } //Else the previous match is less or equal to the current match
                        //assign the value from the left and assign label left
                        else {
                            core[i][j] = core[i][j - 1];
                            b[i][j] = LEFT;
                        }
                    }
                }
            }


        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }

    
    /**
     * Computes an LCS of two strings, storing the result in the
     * instance variables.  The instance variables are assumed to be
     * already allocated.  Implements the LCS-Length procedure on page
     * 353.
     *
     * @param x The string <i>X</i>.
     * @param y The string <i>Y</i>.
     */

    /**
     * Returns a given character from a <code>String</code>.
     * Compensates for character positions in a <code>String</code>
     * being indexed from 0, rather than from 1 as in Section 15.4.
     *
     * @param z A <code>String</code>.
     * @param k An index into <code>z</code>, but starting from 1.
     * @return Returns the <code>k</code>th character of
     * <code>z</code>.
     */
    private char index(String z, int k)
    {
	return z.charAt(k-1);
    }

    /**
     * Returns an LCS of prefixes <i>X</i><sub><i>i</i></sub> and
     * <i>Y</i><sub><i>j</i></sub>.  Implements the Print-LCS
     * procedure on page 355.
     *
     * @param i Index into <i>X</i>.
     * @param j Index into <i>Y</i>.
     * @return A <code>String</code> that is an LCS of
     * <i>X</i><sub><i>i</i></sub> and <i>Y</i><sub><i>j</i></sub>.
     */
    private String printLCS(int i, int j)
    {
	if (i == 0 || j == 0)
	    return "";

	if (b[i][j] == UP_AND_LEFT)
	    return printLCS(i-1, j-1) + index(x, i);
	else if (b[i][j] == UP)
	    return printLCS(i-1, j);
	else
	    return printLCS(i, j-1);
    }

    /** Returns an LCS of <i>X</i> and <i>Y</i> as a
     * <code>String</code>. */
    @Override
    public String toString()
    {
	return printLCS(m, n);
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }
    
    //METHOD TO FILL IN TOP AND SIDE TABLES

    /**
     * Inner class for a typesafe enum pattern for directions in a
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
}