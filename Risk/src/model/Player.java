package model;

import model.dto.BattleResult;
import model.dto.Territory;
import model.dto.Continent;
import model.dto.MissionCard;
import model.dto.RiskCard;
import model.dto.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author orsi
 */
public class Player implements MissionAgent, PlayerInterface {
    
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
    private boolean finishedAttack;
        
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
 
    public int getRemainingPlaceableTroopCount(){
        return this.remainingPlaceableTroopCount;
    }
    
    public boolean hasOccupiedTerritoryThisRound(){
        return this.hasOccupiedTerritoryThisRound;
    }
    
    public MissionCard getMissionCard(){
        return this.missionCard;
    }
    public List<RiskCard> getRiskCards(){
        return this.riskCards;
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
    @Override
    public BattleResult attack( Territory attackingTerritory, Territory defendingTerritory, int attackingTroopCount ){
        Battle battle = new Battle( attackingTerritory, defendingTerritory, attackingTroopCount );
        final BattleResult battleResult = battle.execute();
        return battleResult;
    }
    
    /**
    * @author Sajti Tamás
    */
    @Override
    public boolean isMissionCompleted(){
        return missionCard.isCompleted();
    }
    
    /**
    * @author orsi, Sajti Tamás
    */
    @Override
    public void placeTroops( Territory territory, int troopCount ){
        //nem vegleges
        if(troopCount > this.remainingPlaceableTroopCount){
            //nincs annyi had, adjon errort vagy valami
        }else{
            this.remainingPlaceableTroopCount = this.remainingPlaceableTroopCount - troopCount;
            this.occupiedTerritories.add(territory);
            if (territory.getTroopCount()==0)
            {
                territory.assignToPlayer(this);
            }
            territory.addTroops( troopCount );
        }
    }
    
    @Override
    public void redeemRiskCards(){
        //nem vegleges
    }
    
    /**
    * @author Sajti Tamás
    */
    @Override
    public void transfer( Territory fromTerritory, Territory toTerritory, int troopCount ){
        fromTerritory.removeTroops( troopCount );
        toTerritory.addTroops( troopCount );
    }

    
    /**
    * @author Sajti Tamás
    */
    public boolean loseBattle( int defenderTroopLossCount, Territory defendingTerritory ) {
        troopCount -= defenderTroopLossCount;
        // the defendingTerritory's new occupier player is set in winBattle, no need to set that here
        occupiedTerritories.remove( defendingTerritory );
        
        Continent continent = defendingTerritory.getContinent();
        //occupiedContinents.remove( continent );
        //continent.removePlayer();
        
        boolean hasPlayerLost = occupiedTerritories.isEmpty();
        if( hasPlayerLost )
            Model.getInstance().playerLost( this );
        return hasPlayerLost;
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
        for( Territory t : occupiedTerritories )
            if( t.getTroopCount() > 1 )
                count++;
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

    public void setRemainingPlaceableTroopCount(int remainingPlaceableTroopCount) {
        this.remainingPlaceableTroopCount = remainingPlaceableTroopCount;
    }

    public boolean isFinishedAttack() {
        return finishedAttack;
    }

    public void setFinishedAttack(boolean finishedAttack) {
        this.finishedAttack = finishedAttack;
    }

    /**
    * @author Sajti Tamás
    */
    public int getContinentalTroopBonusTotal() {
        return occupiedContinents.stream().map( continent -> continent.getTroopBonusCount() ).reduce( 0, ( Integer x, Integer y ) -> x + y );
    }
    
}
