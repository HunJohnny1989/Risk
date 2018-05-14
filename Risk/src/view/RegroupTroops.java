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
public class RegroupTroops extends PlaceTroops {
    
    public RegroupTroops(Frame parent, boolean modal, Surface surface) {
        super(parent, modal, surface);
        this.jButtonPlace.setText("Regroup");
        this.setTitle("Regroup troops");
    }
}
