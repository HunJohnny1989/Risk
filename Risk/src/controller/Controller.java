/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Event;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;
import model.*;
import model.dto.Color;
import model.dto.GameField;
import view.MainWindow;

/**
 *
 * @author Johnny
 */
public class Controller implements MyViewEvent{

    private static Model model;
    private static MainWindow mainWindow;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //TODO Kirajzolni Játékos adatait bekérő oldalt
        model = new Model( "Eszti", "Orsi", "John", "Tomi" );
 
        /* Create and display the form */
       // EventQueue.invokeLater(() -> {
            mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        //});
        GameField field = new GameField("src\\Model\\MapShape.xml");
        model.divideRandomTerritories(field.getTerritories());
        mainWindow.setGameField(field);
    }

    //Rákattintunk a játék kezdése gombra
    public void startGame(ActionEvent event){
        //TODO Kirajzolni a táblát, ekkor a játékos lerakhatja a kezdeti katonáit
        
        //ha elfogytak a "kézben" lévő katonák kezdődik a kör        
        
    }
    
    public void handleEvent(Event event) {

        /*Kölönböző esetek amiket le kell kezelni
        1. Támad
        2. Passzol
        3. Átcsoportosít
        4. Erősít
        */
        switch (event.id) {
            case Event.KEY_PRESS:
                break;
            case Event.KEY_ACTION:
                break;
            case Event.KEY_RELEASE:
                break;
            case Event.MOUSE_DOWN:
                break;
            case Event.MOUSE_UP:
                break;
            case Event.MOUSE_DRAG:
                break;
        }

    }

    @Override
    public void placeUnitTest() {
        System.out.println("placeUnitTest");
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
