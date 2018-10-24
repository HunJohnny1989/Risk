/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.UIManager;
import model.*;
import model.dto.BattleResult;
import model.dto.GameField;
import model.dto.Missions;
import model.dto.Phase;
import model.dto.Territory;
import view.MainWindow;

/**
 *
 * @author Johnny
 */
public class Controller implements ControllerInterface {

    private static Model model;
    private static MainWindow mainWindow;
    private static GameField field;

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

        /* Create and display the form */
        // EventQueue.invokeLater(() -> {
        mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        //});
        
        field = new GameField("src\\Model\\MapShape.xml");
        startNewGame();
        mainWindow.setGameField(field);
    }

    @Override
    public int getPlayerRemainingTroopCount() {
        return model.getCurrentPlayer().getRemainingPlaceableTroopCount();
    }

    @Override
    public void removeAvailableTroops(int troopCount) {
        Player currentPlayer = model.getCurrentPlayer();
        currentPlayer.setRemainingPlaceableTroopCount(currentPlayer.getRemainingPlaceableTroopCount() - troopCount);
        if (currentPlayer.getRemainingPlaceableTroopCount() == 0) {
            model.selectNextPlayer();
            if (model.getCurrentPlayer().getRemainingPlaceableTroopCount() == 0) {
                model.nextPhase();
                 mainWindow.setGamePhase(model.getCurrentPhase());
            }
            mainWindow.setPLayer(model.getCurrentPlayer());
            mainWindow.setMissionString(model.getCurrentPlayer().getMissionCard().getMission());
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    @Override
    public Phase getCurrentPhase() {
        return model.getCurrentPhase();
    }

    @Override
    public BattleResult attackTerritory(Territory from, Territory to, int troopCount) {
        BattleResult result = model.getCurrentPlayer().attack(from, to, troopCount);
        from.removeTroops(result.getAttackerTroopLossCount());
        to.removeTroops(result.getDefenderTroopLossCount());
        return result;
    }

    @Override
    public void transfer(Territory from, Territory to, int troopCount) {
        model.getCurrentPlayer().transfer(from, to, troopCount);
    }
    
    @Override
    public void finishAttack(){
        model.finishAttack();
        mainWindow.setPLayer(model.getCurrentPlayer());
        mainWindow.setGamePhase(model.getCurrentPhase());
        mainWindow.setMissionString(model.getCurrentPlayer().getMissionCard().getMission());
    }
    
    @Override
    public void finishRegroup(){
        model.finisRegroup();
        mainWindow.setPLayer(model.getCurrentPlayer());
        mainWindow.setGamePhase(model.getCurrentPhase());
        mainWindow.setMissionString(model.getCurrentPlayer().getMissionCard().getMission());
    }
    
    //author Eszti
    @Override
    public Player getWinner()
    {
        for(Player p : model.getPlayers())
        {
            if(!p.hasKilledPlayer(p.getColor()) && p.isMissionCompleted())
            {
                return p;
            }
        }
        
        return null;
    }
    
    //author Eszti
    public static void startNewGame()
    {
         //TODO Kirajzolni Játékos adatait bekérő oldalt
        model = new Model("Eszti", "Orsi", "John", "Tomi");
        field.resetTerritories();
        model.divideRandomTerritories(field.getTerritories());
        mainWindow.setPLayer(model.getCurrentPlayer());
        mainWindow.setGamePhase(model.getCurrentPhase());
        mainWindow.setMissionString(model.getCurrentPlayer().getMissionCard().getMission() );
        mainWindow.refreshRiskCards(model.getCurrentPlayer());
        mainWindow.setRiskCardsCanBeExchanged(false);
    }
}
