package model;

/**
 *
 * @author orsi
 */
public class BattleResult {
    private int attackerTroopLossCount;
    private int defenderTroopLossCount;
    private boolean hasTerritoryBeenConquered;

    public BattleResult(int attackerTroopLossCount, int defenderTroopLossCount, boolean hasTerritoryBeenConquered) {
        this.attackerTroopLossCount = attackerTroopLossCount;
        this.defenderTroopLossCount = defenderTroopLossCount;
        this.hasTerritoryBeenConquered = hasTerritoryBeenConquered;
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
}
