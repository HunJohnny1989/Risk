/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Frame;

/**
 *
 * @author Eszti
 */
public class AttackTerritory extends PlaceTroops {
    
    public AttackTerritory(Frame parent, boolean modal, Surface surface) {
        super(parent, modal, surface);
        this.jButtonPlace.setText("Attack");
        this.setTitle("Attack territory");
    }
}
