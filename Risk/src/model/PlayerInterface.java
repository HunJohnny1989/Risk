/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.dto.BattleResult;
import model.dto.Territory;

/**
 *
 * @author Sajti Tamas
 */
interface PlayerInterface {
    BattleResult attack( Territory attackingTerritory, Territory defendingTerritory, int attackingTroopCount );
    boolean isMissionCompleted();
    void placeTroops( Territory territory, int troopCount );
    void redeemRiskCards();
    void transfer(Territory fromTerritory, Territory toTerritory, int troopCount );
}
