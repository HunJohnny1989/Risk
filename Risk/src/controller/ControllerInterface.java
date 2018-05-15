/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Player;
import model.dto.BattleResult;
import model.dto.Phase;
import model.dto.Territory;

/**
 *
 * @author Johnny
 */
public interface ControllerInterface {
    
    int getPlayerRemainingTroopCount();
    
    void removeAvailableTroops(int troopCount);
    
    Player getCurrentPlayer();
    
    Phase getCurrentPhase();
    
    BattleResult attackTerritory(Territory from, Territory to, int troopCount);
    
    void transfer(Territory from, Territory to, int troopCount);
    
    void finishAttack();
    
    void finishRegroup();
}
