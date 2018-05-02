package model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author orsi
 */
public class Continent {
    private String name;
    private List<Territory> territories; //sajat korzetei
    private int numOfTerritories; //korzeteinek szama
    private int numFreeTerritories; //szabad korzeteinek szama
    private Player occupierPlayer;
    
    public Continent(String name, List<Territory> territories){
        this.name = name;
        this.territories = territories;
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
    
    public Player getOccupierPlayer(){
        return this.occupierPlayer;
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Continent other = (Continent)obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
    * @author Sajti Tamás
    */
    void removePlayer() {
        occupierPlayer = null;
    }
    
}