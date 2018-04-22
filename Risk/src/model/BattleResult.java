package model;

/**
 *
 * @author orsi
 */
public class BattleResult {
    private int attackerTroopLossCount;
    private int defenderTroopLossCount;
    private boolean hasTerritoryBeenConquered;
    
    public BattleResult(){
        //nem veglegs
    }
    
    public int getAttackerTroopLossCount(){
        return this.attackerTroopLossCount;
    }
    
    public int getDefenderTroopLossCount(){
        return this.defenderTroopLossCount;
    }
    
    public boolean isTerritoryBeenConquered(){
        return this.hasTerritoryBeenConquered;
    }
}
