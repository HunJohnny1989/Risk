package model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Player;

/**
 *
 * @author orsi
 */
public enum Continent {
    NORTH_AMERICA("North America", 5)
    , SOUTH_AMERICA("South America", 2)
    , EUROPE("Europe", 5)
    , AFRICA("Africa", 3)
    , ASIA("Asia", 7),
    AUSTRALIA("Australia", 2);
    
    private String name;
    private List<Territory> territories = new ArrayList<>(); //sajat korzetei
    private int numOfTerritories; //korzeteinek szama
    private Player occupierPlayer;
    private int troopBonusCount;
    
    private Continent(String name, int troopBonusCount) {
        this.name = name;
        this.troopBonusCount = troopBonusCount;
    }
 
    public static Continent parseContinent(String name){
        Continent continent;
        switch( name ) {
            case "North America":
            default:
                continent = NORTH_AMERICA;
                break;
            case "South America":
                continent = SOUTH_AMERICA;
                break;
            case "Europe":
                continent = EUROPE;
                break;
            case "Africa":
                continent = AFRICA;
                break;
            case "Asia":
                continent = ASIA;
                break;
            case "Australia":
                continent = AUSTRALIA;
                break;
        }
        return continent;
    }

    public int getTroopBonusCount() {
        return troopBonusCount;
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
   
    public Player getOccupierPlayer(){
        return this.occupierPlayer;
    }
    
    public void addTerritory(Territory territory){
        this.territories.add(territory);
        numOfTerritories++;
    }
    
    public void setOccupierPlayer(Player player){
        this.occupierPlayer = player;
    }

    /**
    * @author Sajti Tamás
    */
    public void removePlayer() {
        occupierPlayer = null;
    }
    
}