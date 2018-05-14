package model.dto;

import java.util.List;

/**
 *
 * @author orsi
 */
public class BattleResult {
    private int attackerTroopLossCount;
    private int defenderTroopLossCount;
    private boolean hasTerritoryBeenConquered;
    private boolean hasDefenderLost;
    private List< Integer > attackerDiceRolls;
    private List< Integer > defenderDiceRolls;

    public BattleResult(int attackerTroopLossCount, int defenderTroopLossCount, boolean hasTerritoryBeenConquered, 
                        boolean hasDefenderLost, List<Integer> attackerDiceRolls, List<Integer> defenderDiceRolls) {
        this.attackerTroopLossCount = attackerTroopLossCount;
        this.defenderTroopLossCount = defenderTroopLossCount;
        this.hasTerritoryBeenConquered = hasTerritoryBeenConquered;
        this.hasDefenderLost = hasDefenderLost;
        this.attackerDiceRolls = attackerDiceRolls;
        this.defenderDiceRolls = defenderDiceRolls;
    }
 
    public int getAttackerTroopLossCount(){
        return this.attackerTroopLossCount;
    }
    
    public int getDefenderTroopLossCount(){
        return this.defenderTroopLossCount;
    }
    
    public boolean hasTerritoryBeenConquered(){
        return this.hasTerritoryBeenConquered;
    }

    public boolean hasDefenderLost() {
        return hasDefenderLost;
    }

    public List<Integer> getAttackerDiceRolls() {
        return attackerDiceRolls;
    }

    public List<Integer> getDefenderDiceRolls() {
        return defenderDiceRolls;
    }
    
}
