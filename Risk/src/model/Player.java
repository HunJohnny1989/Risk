package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author orsi
 */
public class Player implements MissionAgent {
    private boolean hasOccupiedTerritoryThisRound;
    private String name;
    private int remainingPlaceableTroopCount;
    private Color color;
    private MissionCard missionCard;
    private List<RiskCard> riskCards;
    private int numOfInfantry;
    private int numOfCavalry;
    private int numOfArtillery;
    private List<Territory> occupiedTerritories;
    private List<Continent> occupiedContinents;
    private List<Color> killedPlayers;
    private int troopCount;
        
    public Player(String name, Color color, MissionCard missionCard){
        this.occupiedTerritories = new LinkedList<Territory>();
        this.occupiedContinents = new LinkedList<Continent>();        
        this.killedPlayers = new LinkedList<Color>();
        this.hasOccupiedTerritoryThisRound = false; //?
        this.name = name;
        this.remainingPlaceableTroopCount = 0; //?
        this.troopCount = 0;
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
    
    public List<Territory> getOccupiedTerritories(){
        return this.occupiedTerritories;
    }
    
    public List<Continent> getOccupiedContinents(){
        return this.occupiedContinents;
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
        final BattleResult battleResult = battle.execute();
        return battleResult;
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

    
    /**
    * @author Sajti Tamás
    */
    public void loseBattle(int defenderTroopLossCount, Territory defendingTerritory) {
        troopCount -= defenderTroopLossCount;
        // the defendingTerritory's new occupier player is set in winBattle
        occupiedTerritories.remove( defendingTerritory );
        
        Continent continent = defendingTerritory.getContinent();
        occupiedContinents.remove( continent );
        continent.removePlayer();
        
        if( occupiedTerritories.isEmpty() ) {
            Model.getInstance().playerLost( this );
        }
    }

    /**
    * @author Sajti Tamás
    */
    public void winBattle(int attackerTroopLossCount, Territory defendingTerritory) {
        troopCount -= attackerTroopLossCount;
        occupiedTerritories.add( defendingTerritory );
        defendingTerritory.assignToPlayer( this );
        hasOccupiedTerritoryThisRound = true;
    }

    
    
    /**
    * @author Sajti Tamás
    */
    @Override
    public int getOccupiedTerritoryCount() {
        return occupiedTerritories.size();
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public int getTerritoryCountWithAtLeastTwoTroops() {
        int count = 0;
        for( Territory t : occupiedTerritories ) {
            if( t.getTroopCount() > 1 )
                count++;
        }
        return count;
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public boolean hasKilledPlayer( Color color ) {
        return killedPlayers.contains( color );
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public boolean hasOccupiedContinent( Continent continent ) {
        return occupiedContinents.contains( continent );
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.color);
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
        final Player other = (Player) obj;
        if (this.color != other.color) {
            return false;
        }
        return true;
    }
    
}