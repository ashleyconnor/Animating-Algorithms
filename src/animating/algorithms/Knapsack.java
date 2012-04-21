/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animating.algorithms;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ash
 */
public class Knapsack extends javax.swing.JPanel {
    
    
    //attributes
    int listLine, psuedoLine, speed, capacity;
    ArrayList<Integer> valueList, weightList;
    DefaultListModel ListModel;
    KnapsackAlgorithm ka;
    ExecutorService executor;
    
    /**
     * Creates new form Knapsack
     */
    public Knapsack() {
        
        //init attributes
        psuedoLine = -1;
        listLine = -1;
        speed = 50;
        capacity = 1;
        ListModel = new DefaultListModel();
        valueList = new ArrayList<Integer>();
        weightList = new ArrayList<Integer>();
        
        //init GUI
        initComponents();
        
        //set custom renderer for pseduocode table and input list
        jTable2.setDefaultRenderer(String.class, new CustomRenderer());
        inputValueList.setCellRenderer(new CustomListRenderer());
    }
    
    //methods
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calculationsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        calculationsAreaText = new javax.swing.JTextArea();
        speedPanel = new javax.swing.JPanel();
        speedSlider = new javax.swing.JSlider();
        speedLabel = new javax.swing.JLabel();
        speedValueLabel = new javax.swing.JLabel();
        pseudoCodePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        tablePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        calculationsTable = new javax.swing.JTable();
        userInputPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        inputValueScrollPane = new javax.swing.JScrollPane();
        inputValueList = new javax.swing.JList();
        valueLabel = new javax.swing.JLabel();
        valueSpinner = new javax.swing.JSpinner();
        weightLabel = new javax.swing.JLabel();
        weightSpinner = new javax.swing.JSpinner();
        removeButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        weightLabel1 = new javax.swing.JLabel();
        capacitySpinner = new javax.swing.JSpinner();
        cancelButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1024, 768));
        setPreferredSize(new java.awt.Dimension(1024, 768));

        calculationsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Calculations"));

        calculationsAreaText.setColumns(20);
        calculationsAreaText.setEditable(false);
        calculationsAreaText.setRows(5);
        jScrollPane1.setViewportView(calculationsAreaText);

        org.jdesktop.layout.GroupLayout calculationsPanelLayout = new org.jdesktop.layout.GroupLayout(calculationsPanel);
        calculationsPanel.setLayout(calculationsPanelLayout);
        calculationsPanelLayout.setHorizontalGroup(
            calculationsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(calculationsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );
        calculationsPanelLayout.setVerticalGroup(
            calculationsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(calculationsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1)
                .addContainerGap())
        );

        speedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Speed"));
        speedPanel.setPreferredSize(new java.awt.Dimension(250, 80));

        speedSlider.setMinimum(10);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setFocusable(false);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        speedLabel.setText("Speed:");

        speedValueLabel.setText("50%");

        org.jdesktop.layout.GroupLayout speedPanelLayout = new org.jdesktop.layout.GroupLayout(speedPanel);
        speedPanel.setLayout(speedPanelLayout);
        speedPanelLayout.setHorizontalGroup(
            speedPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(speedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(speedSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(speedLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(speedValueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        speedPanelLayout.setVerticalGroup(
            speedPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(speedPanelLayout.createSequentialGroup()
                .add(speedPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(speedPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(speedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(speedPanelLayout.createSequentialGroup()
                        .add(15, 15, 15)
                        .add(speedPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(speedLabel)
                            .add(speedValueLabel))))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pseudoCodePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Psuedocode"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"code line 1"},
                {"code line 2"},
                {"code line 3"},
                {"code line 4"},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Pseudocode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setEnabled(false);
        jTable2.setRowSelectionAllowed(false);
        jTable2.setShowGrid(true);
        jTable2.setTableHeader(null);
        jScrollPane3.setViewportView(jTable2);

        org.jdesktop.layout.GroupLayout pseudoCodePanelLayout = new org.jdesktop.layout.GroupLayout(pseudoCodePanel);
        pseudoCodePanel.setLayout(pseudoCodePanelLayout);
        pseudoCodePanelLayout.setHorizontalGroup(
            pseudoCodePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pseudoCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );
        pseudoCodePanelLayout.setVerticalGroup(
            pseudoCodePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pseudoCodePanelLayout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Value Table"));
        tablePanel.setPreferredSize(new java.awt.Dimension(495, 400));

        calculationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        calculationsTable.setEnabled(false);
        calculationsTable.setGridColor(java.awt.Color.gray);
        calculationsTable.setRowSelectionAllowed(false);
        calculationsTable.setShowGrid(true);
        calculationsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(calculationsTable);

        org.jdesktop.layout.GroupLayout tablePanelLayout = new org.jdesktop.layout.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tablePanelLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        userInputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Input"));
        userInputPanel.setPreferredSize(new java.awt.Dimension(250, 320));

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        inputValueList.setModel(ListModel);
        inputValueList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        inputValueScrollPane.setViewportView(inputValueList);

        valueLabel.setText("Value:");

        valueSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(100), Integer.valueOf(1)));

        weightLabel.setText("Weight:");

        weightSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(100), Integer.valueOf(1)));

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        weightLabel1.setText("Capacity:");

        capacitySpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, Integer.valueOf(100), Integer.valueOf(1)));
        capacitySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                capacitySpinnerStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout userInputPanelLayout = new org.jdesktop.layout.GroupLayout(userInputPanel);
        userInputPanel.setLayout(userInputPanelLayout);
        userInputPanelLayout.setHorizontalGroup(
            userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userInputPanelLayout.createSequentialGroup()
                .add(6, 6, 6)
                .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(inputValueScrollPane)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, userInputPanelLayout.createSequentialGroup()
                        .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(weightLabel)
                            .add(valueLabel))
                        .add(62, 62, 62)
                        .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(valueSpinner)
                            .add(weightSpinner, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 133, Short.MAX_VALUE)
                        .add(weightLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(capacitySpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(userInputPanelLayout.createSequentialGroup()
                        .add(addButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(resetButton)))
                .addContainerGap())
        );
        userInputPanelLayout.setVerticalGroup(
            userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userInputPanelLayout.createSequentialGroup()
                .add(inputValueScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(valueLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, capacitySpinner, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .add(valueSpinner)
                    .add(weightLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(weightLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, weightSpinner, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(userInputPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addButton)
                    .add(removeButton)
                    .add(resetButton))
                .addContainerGap())
        );

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(calculationsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(12, 12, 12)
                        .add(pseudoCodePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(speedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                            .add(userInputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(cancelButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(startButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(tablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(calculationsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(pseudoCodePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(speedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(34, 34, 34)
                        .add(userInputPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 277, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(tablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(startButton))
                .add(0, 53, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //cancel the running of the algorithm
        executor.shutdownNow();
        
        //clear the data-structures ready for new input
        valueList.clear();
        weightList.clear();
        ListModel.clear();
        
        JOptionPane.showMessageDialog(this, "Algorithm cancelled.", "Cancelled", JOptionPane.OK_OPTION);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
       
        //FOR TESTING WILL BE REMOVED
        if(valueList.isEmpty() || weightList.isEmpty())
            enterTestData();
        
        //check if there are values in the arrays, display error if not
        if(valueList.isEmpty() || weightList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You cannot run the algorithm without values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        else if(valueList.size() < 2) {
            JOptionPane.showMessageDialog(this, "You need to have more than 1 weight/value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(capacity < 2) {
            JOptionPane.showMessageDialog(this, "The capacity should be larger than ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
        
            //TODO Disable buttons until algorithm has finished
            
            executor = Executors.newSingleThreadExecutor();
            ka = new KnapsackAlgorithm(this);
            executor.execute(ka);
            calculationsTable.getTableHeader().setResizingAllowed(false);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // add visual representation to jlist and add to alogrithm model
        Integer value = (Integer) valueSpinner.getValue();
        Integer weight = (Integer) weightSpinner.getValue();
        
        ListModel.addElement("Value: " + valueSpinner.getValue() + ", Weight: " + weightSpinner.getValue());
             
        if(valueList.isEmpty() && weightList.isEmpty()) {
            valueList.add(null);
            weightList.add(null);
        }
        valueList.add(value);
        weightList.add(weight);
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        
        // if an item is not selected then remove the last one
        // else remove the selected item        
        if(inputValueList.isSelectionEmpty()) {
            int position = ListModel.getSize() - 1;
            if(position >= 0) {
                ListModel.remove(position);
                valueList.remove(position);
                weightList.remove(position);
            }
        }
        else {
            int[] selected = inputValueList.getSelectedIndices();
            ListModel.removeElementAt(selected[0]);
            
            for (int i = 0; i < selected.length; i++) {
                valueList.remove(i);
                weightList.remove(i);
            }
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        ListModel.clear();
        valueList.clear();
        weightList.clear();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        speed = speedSlider.getValue();
        speedValueLabel.setText(speed + "%");
        if(ka != null)
            ka.setSpeed(speed);
    }//GEN-LAST:event_speedSliderStateChanged

    private void capacitySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_capacitySpinnerStateChanged
        capacity = Integer.parseInt(capacitySpinner.getValue().toString());
    }//GEN-LAST:event_capacitySpinnerStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextArea calculationsAreaText;
    private javax.swing.JPanel calculationsPanel;
    private javax.swing.JTable calculationsTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JSpinner capacitySpinner;
    private javax.swing.JList inputValueList;
    private javax.swing.JScrollPane inputValueScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel pseudoCodePanel;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JPanel speedPanel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JLabel speedValueLabel;
    private javax.swing.JButton startButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JPanel userInputPanel;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JSpinner valueSpinner;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JLabel weightLabel1;
    private javax.swing.JSpinner weightSpinner;
    // End of variables declaration//GEN-END:variables

    //setters and getters
    public JTable getCalculationsTable() {
        return calculationsTable;
    }
    
    public JList getValueInputList() {
        return inputValueList;
    }
    
    public JTextArea getCalculationsTextArea() {
        return calculationsAreaText;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public ArrayList<Integer> getValueList() {
        return valueList;
    }
    
    public ArrayList<Integer> getWeightList() {
        return weightList;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public int getPsuedoCodeLine() {
        return psuedoLine;
    }
    
    public void setPsuedoCodeLine(int line) {
        psuedoLine = line;
    }
    
    public void incPseudoCodeLine() {
        psuedoLine++;
    }
    
    public int getListLine() {
        return listLine;
    }
    
    public void setListLine(int line) {
        listLine = line;
    }

    private void enterTestData() {
        
        if(valueList.isEmpty() && weightList.isEmpty()) {
            //Test data
            
            capacity = Integer.parseInt(capacitySpinner.getValue().toString());
            System.out.println(capacity);
            
            if(capacity == 1)
                capacity = 18;

            valueList.add(null);
            valueList.add(12);
            valueList.add(10);
            valueList.add(8);
            valueList.add(11);
            valueList.add(14);
            valueList.add(7);
            valueList.add(9);

            weightList.add(null);
            weightList.add(4);
            weightList.add(6);
            weightList.add(5);
            weightList.add(7);
            weightList.add(3);
            weightList.add(1);
            weightList.add(6);
        }
    }
    
class CustomRenderer extends DefaultTableCellRenderer 
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
            JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if((row == psuedoLine) && (column == 0))
                d.setBackground(Color.RED);
            else
                d.setBackground(null);
            return d;
        }
    }

class CustomListRenderer extends DefaultListCellRenderer {
        
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         if (index == listLine) {
             c.setBackground(Color.RED);
         }
         return c;
        }
    }

}