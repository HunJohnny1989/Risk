package model.dto;

/**
 *
 * @author orsi
 */
public class BattleResult {
    private int attackerTroopLossCount;
    private int defenderTroopLossCount;
    private boolean hasTerritoryBeenConquered;
    private boolean hasDefenderLost;

    public BattleResult(int attackerTroopLossCount, int defenderTroopLossCount, boolean hasTerritoryBeenConquered, boolean hasDefenderLost ) {
        this.attackerTroopLossCount = attackerTroopLossCount;
        this.defenderTroopLossCount = defenderTroopLossCount;
        this.hasTerritoryBeenConquered = hasTerritoryBeenConquered;
        this.hasDefenderLost = hasDefenderLost;
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

    public boolean isHasDefenderLost() {
        return hasDefenderLost;
    }
    
}
