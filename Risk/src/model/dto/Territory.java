package model.dto;

import model.dto.Continent;
import java.util.List;
import model.Player;

/**
 *
 * @author orsi
 */
public class Territory {
    private String name;
    private int troopCount;
    private List<Territory> neighbourTerritories; //szomszedos korzetek
    private Player occupierPlayer;
    private Continent continent;
    
    public Territory(String name, Continent continent, List<Territory> neighbourTerritories){
        this.name = name;
        this.continent = continent;
        this.troopCount = 0; //nem vegleges
        this.neighbourTerritories = neighbourTerritories;
    }

    public Continent getContinent() {
        return continent;
    }

    public int getTroopCount() {
        return troopCount;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Player getOccupierPlayer(){
        return this.occupierPlayer;
    }
   
    public List<Territory> getNeighbourTerritories(){
        return this.neighbourTerritories;
    }
    
    /**
    * @author Sajti Tamás
    */
    public void assignToPlayer( Player player ){
        occupierPlayer = player;
    }

}
