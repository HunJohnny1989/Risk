package model;

import java.util.List;

/**
 *
 * @author orsi
 */
public class Territory {
    private String name;
    private int troopCount;
    private List<Territory> neighbourTerritories; //szomszedos korzetek
    private Player occupiedPlayer;
    
    public Territory(String name, List<Territory> neighbourTerritories){
        this.name = name;
        this.troopCount = 0; //nem vegleges
        this.neighbourTerritories = neighbourTerritories;
    }

    public int getTroopCount() {
        return troopCount;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Player getOccupiedPlayer(){
        return this.occupiedPlayer;
    }
   
    public List<Territory> getNeighbourTerritories(){
        return this.neighbourTerritories;
    }
    
    public void assignToPlayer(){
        //nem vegleges
    }
    
}
