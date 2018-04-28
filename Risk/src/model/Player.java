package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author orsi
 */
public class Player {
    private boolean hasOccupiedTerritoryThisRound;
    private String name;
    private int remainingPlaceableTroopCount;
    private Color color;
    private MissionCard missionCard;
    private List<RiskCard> riskCards;
    private int numOfInfantry;
    private int numOfCavalry;
    private int numOfArtillery;
    private List<Territory> ownedTerritories;
    private List<Continent> ownedContinents;
        
    public Player(String name, Color color, MissionCard missionCard){
        this.ownedTerritories = new ArrayList<Territory>();
        this.ownedContinents = new ArrayList<Continent>();
        this.hasOccupiedTerritoryThisRound = false; //?
        this.name = name;
        this.remainingPlaceableTroopCount = 0; //?
        this.color = color;
        this.missionCard = missionCard;
        this.riskCards = new ArrayList<RiskCard>();
    }
    
    public String getName(){
        return this.name;
    }
    
    public Color getColor(){
        return this.color;
    }
    
    public int getNumOfInfantry(){
        return this.numOfInfantry;
    }
    
    public int getNumOfCavalry(){
        return this.numOfCavalry;
    }
    
    public int getNumOfArtillery(){
        return this.numOfArtillery;
    }
    
    public int getRemainingPlaceableTroopCount(){
        return this.remainingPlaceableTroopCount;
    }
    
    public boolean isOccupiedTerritoryThisRound(){
        return this.hasOccupiedTerritoryThisRound;
    }
    
    public MissionCard getMissionCard(){
        return this.missionCard;
    }
    public List<RiskCard> getRiskCards(){
        return this.riskCards;
    }
    
    public List<Territory> getOwnedTerritories(){
        return this.ownedTerritories;
    }
    
    public List<Continent> getOwnedContinents(){
        return this.ownedContinents;
    }
    
    public void addPlaceableTroops(int num){
        this.remainingPlaceableTroopCount += num; //nem vegleges
    }
    
    public void addRiskCard(RiskCard riskCard){
        String type = riskCard.getType();
        
        switch(type){ //nem vegleges
            case "artillery": 
                this.numOfArtillery++;
                break;
            case "cavalry":
                this.numOfCavalry++;
                break;
            case "infantry":
                this.numOfInfantry++;
                break;
        }
    }
    
    /**
    * @author Sajti Tamás
    */
    public BattleResult attack( Territory attackingTerritory, Territory defendingTerritory, int attackingTroopCount ){
        Battle battle = new Battle( attackingTerritory, defendingTerritory, attackingTroopCount );
        return battle.execute();
        
    }
    
    public boolean isMissionCompleted(){
        return false; //nem vegleges
    }
    
    public void placeTroops(int num, Territory territory){
        //nem vegleges
        if(num > this.remainingPlaceableTroopCount){
            //nincs annyi had, adjon errort vagy valami
        }else{
            //
        }
    }
    
    public void redeemRiskCards(){
        //nem vegleges
    }
    
    public void transfer(Territory fromTerritory, Territory toTerritory, int num){
        //nem vegleges
    }
    
}
