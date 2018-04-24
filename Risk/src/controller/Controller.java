/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.beans.EventHandler;
import model.*;
/**
 *
 * @author Johnny
 */
public class Controller {
    
    
    public Model model;
    
    public View view;
    
    //Viewbóól érkező event feldolgozása továbbítás a modell réteg felé
    /**
     * 
     * @param event random event egynelőre itt valami más lesz
     */
    public void handleEvemt(EventHandler event){
        
        if(event.getAction().equals("Unit_attack")){
            
            BattleResult result = model.handleAttack(Territory from, Territory to, int troopCount);            
            
            view.updateTerritory(Territory from, result.getAttackerTroopLossCount());
            view.updateTerritory(Territory to, result.getDefenderTroopLossCount());
        }else if(event.getAction().equals("Unit_move")){
            
            MoveResult result = model.handleMove(Territory from, Territory to, int troopCount);            
            
            view.updateTerritory(Territory from, result.getMovedFromTroopCount());
            view.updateTerritory(Territory to, result.getMovedToTroopCount());
        }else if(event.getAction().equals("Skip_round")){
            model.nextPlayer();            
        }else if(event.getAction().equals("Reinforcement")){
            model.reinforceTerritory(Territory territory, int troopCount);
        }        
    }    
    
}
