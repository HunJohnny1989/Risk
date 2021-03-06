/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Eszti
 */
public class PlaceTroops extends javax.swing.JDialog {

    private int numberOfTroops;
    private Surface surface;
    private Hashtable<Integer, JLabel> table;
    /**
     * Creates new form PlaceTroops
     */
    public PlaceTroops(java.awt.Frame parent, boolean modal, Surface surface) {
        super(parent, modal);
        initComponents(); 
        this.setLocationRelativeTo(parent);
        numberOfTroops = 0;
        this.surface = surface;
        table = new Hashtable<Integer, JLabel>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSliderNumberOfTroops = new javax.swing.JSlider();
        jButtonPlace = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelNumberOfTroops = new javax.swing.JLabel();

        setTitle("Place troops");
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Number of troops:");

        jSliderNumberOfTroops.setMaximum(10);
        jSliderNumberOfTroops.setMinimum(1);
        jSliderNumberOfTroops.setPaintLabels(true);
        jSliderNumberOfTroops.setPaintTicks(true);
        jSliderNumberOfTroops.setSnapToTicks(true);
        jSliderNumberOfTroops.setValue(1);
        jSliderNumberOfTroops.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderNumberOfTroopsStateChanged(evt);
            }
        });

        jButtonPlace.setText("Place");
        jButtonPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlaceActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelNumberOfTroops.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelNumberOfTroops.setText("Troops");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelNumberOfTroops)
                        .addGap(38, 38, 38)))
                .addComponent(jSliderNumberOfTroops, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonPlace)
                .addGap(69, 69, 69)
                .addComponent(jButtonCancel)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSliderNumberOfTroops, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelNumberOfTroops)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonPlace)
                    .addComponent(jButtonCancel))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        setVisible(false);
        this.surface.resetSurface();
        this.surface.setCancelledAction(true);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlaceActionPerformed
        numberOfTroops = jSliderNumberOfTroops.getValue();
        setVisible(false);
    }//GEN-LAST:event_jButtonPlaceActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        numberOfTroops = 0;
        this.jSliderNumberOfTroops.setValue(this.jSliderNumberOfTroops.getMinimum());
    }//GEN-LAST:event_formComponentShown

    private void jSliderNumberOfTroopsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderNumberOfTroopsStateChanged
        if (!this.jSliderNumberOfTroops.getValueIsAdjusting()) {
            this.jLabelNumberOfTroops.setText(Integer.toString(this.jSliderNumberOfTroops.getValue()));
        }
    }//GEN-LAST:event_jSliderNumberOfTroopsStateChanged
    
    public void setRange(int min, int max)
    {
        jSliderNumberOfTroops.setMinimum(min);
        jSliderNumberOfTroops.setMaximum(max);
        table.clear();
        
        if (max - min <=10)
        {
            for(int i = min; i <= max; i++)
            {
                table.put(i, new JLabel(Integer.toString(i)));
            }
            jSliderNumberOfTroops.setMinorTickSpacing(1);
        }
        else
        {
            table.put(min, new JLabel(Integer.toString(min)));
            for(int i = (min / 5 + 1)*5; i < max; i+=5)
            {
                table.put(i, new JLabel(Integer.toString(i)));
            }
            table.put(max, new JLabel(Integer.toString(max)));
            jSliderNumberOfTroops.setMinorTickSpacing(0);
        }
        jSliderNumberOfTroops.setLabelTable(table);
    }
    
    public void setValue(int value){
        jSliderNumberOfTroops.setValue(value);
    }
    
    public int getNumberOfTroops()
    {
        return numberOfTroops;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    protected javax.swing.JButton jButtonPlace;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelNumberOfTroops;
    private javax.swing.JSlider jSliderNumberOfTroops;
    // End of variables declaration//GEN-END:variables
}
