/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author ash
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // Create new menu instance and center it vertically and horizontally on
        // the screen.
        
        Menu menu = new Menu();
        menu.setLocationRelativeTo(null);
        menu.setSize(new Dimension(800, 600));
        menu.setVisible(true);
        
        JPanel jp = (JPanel) menu.getContentPane();
        menu.remove(jp);
        
        Thread.sleep(10000);
        
        LCS lcs = new LCS();
        lcs.setSize(new Dimension(800, 600));        
        menu.setContentPane(lcs);
        menu.repaint();
        
    }
}
