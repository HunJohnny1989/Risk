package model;

/**
 *
 * @author orsi
 */
public class Battle {
    private Territory attackingTerritory;
    private int attackingTroopCount;
    private Territory defendingTerritory;
    private int defendingTroopCount;
    
    public Battle(){
        //nem vegleges
    }
    
    public Territory getAttackingTerritory(){
        return this.attackingTerritory;
    }
    
    public int getAttackingTroopCount(){
        return this.attackingTroopCount;
    }
    
    public Territory getDefendingTerritory(){
        return this.defendingTerritory;
    }
    
    public int getDefendingTroopCount(){
        return this.defendingTroopCount;
    }
}
