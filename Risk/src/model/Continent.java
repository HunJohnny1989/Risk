package model;

import java.util.List;

/**
 *
 * @author orsi
 */
public class Continent {
    private String name;
    private List<Territory> territories; //sajat korzetei
    private int numOfTerritories; //korzeteinek szama
    private int numFreeTerritories; //szabad korzeteinek szama
    private boolean occupied; //elfoglalt-e
    
    public Continent(String name, List<Territory> territories){
        this.name = name;
        this.territories = territories;
        this.occupied = false;
        this.numOfTerritories = this.territories.size();
        this.numFreeTerritories = this.numOfTerritories;
    }
    
    public String getName(){
        return this.name;
    }
    
    public List<Territory> getTerritories(){
        return this.territories;
    }
    
    public int getNumOfTerritories(){
        return this.numOfTerritories;
    }
    
    public int getNumFreeTerritories(){
        return this.numFreeTerritories;
    }
    
    public boolean isOccupied(){
        return this.occupied;
    }
    
}
