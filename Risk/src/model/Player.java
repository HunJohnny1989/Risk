package model;

import static java.lang.Math.random;
import model.dto.BattleResult;
import model.dto.Territory;
import model.dto.Continent;
import model.dto.MissionCard;
import model.dto.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author orsi
 */
public class Player implements MissionAgent {
    
    private static Random random = new Random();
    
    private boolean hasOccupiedTerritoryThisRound = false;
    private String name;
    private int remainingPlaceableTroopCount = 0;
    private Color color;
    private MissionCard missionCard;
    private int numOfInfantry = 0;
    private int numOfCavalry = 0;
    private int numOfArtillery = 0;
    private List<Territory> occupiedTerritories = new LinkedList<Territory>();
    private Set<Continent> occupiedContinents = new HashSet<Continent>();
    private int troopCount = 0;
    private boolean finishedAttack;
    private boolean finishedRegroup;
        
    public Player(String name, Color color){  
        this.name = name;
        this.color = color;
    }

    /**
    * @author Sajti Tamás
    */
    public int getNumOfInfantry() {
        return numOfInfantry;
    }

    /**
    * @author Sajti Tamás
    */
    public int getNumOfCavalry() {
        return numOfCavalry;
    }

    /**
    * @author Sajti Tamás
    */
    public int getNumOfArtillery() {
        return numOfArtillery;
    }
    
    /**
    * @author Sajti Tamás
    */
    public boolean canRiskCardsBeRedeemed() {
        return hasArtilleryToRedeem() || hasCavalryToRedeem() || hasInfantryToRedeem() ||
               hasEveryKindOfRiskCard();
    }

    /**
    * @author Sajti Tamás
    */
    private boolean hasInfantryToRedeem() {
        return numOfInfantry > 2;
    }

    /**
    * @author Sajti Tamás
    */
    private boolean hasCavalryToRedeem() {
        return numOfCavalry > 2;
    }

    /**
    * @author Sajti Tamás
    */
    private boolean hasArtilleryToRedeem() {
        return numOfArtillery > 2;
    }

    /**
    * @author Sajti Tamás
    */
    private boolean hasEveryKindOfRiskCard() {
        return numOfArtillery > 0 && numOfCavalry > 0 && numOfInfantry > 0;
    }
    
    /**
    * @author Sajti Tamás
    */
    public void redeemRiskCards(){
        if( hasEveryKindOfRiskCard() ) {
            redeemRiskCardsEveryKind();
            return;
        }
        if( hasArtilleryToRedeem() ) {
            redeemArtillery();
            return;
        }
        if( hasCavalryToRedeem() ) {
            redeemCavalry();
            return;
        }
        if( hasInfantryToRedeem() ) {
            redeemInfantry();
            return;
        }
    }
    
    /**
    * @author Sajti Tamás
    */
    private void redeemArtillery() {
        numOfArtillery -= 3;
        remainingPlaceableTroopCount += 8;
    }

    /**
    * @author Sajti Tamás
    */
    private void redeemCavalry() {
        numOfCavalry -= 3;
        remainingPlaceableTroopCount += 6;
    }

    /**
    * @author Sajti Tamás
    */
    private void redeemInfantry() {
        numOfInfantry -= 3;
        remainingPlaceableTroopCount += 4;
    }

    /**
    * @author Sajti Tamás
    */
    private void redeemRiskCardsEveryKind() {
        numOfArtillery--;
        numOfCavalry--;
        numOfInfantry--;
        remainingPlaceableTroopCount += 10;
    }
    
    public void setMissionCard( MissionCard missionCard ) {
        this.missionCard = missionCard;
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
    
    public void addRandomRiskCard(){
        int randomType = random.nextInt( 3 );
        
        switch(randomType){ //nem vegleges
            case 0: 
                this.numOfArtillery++;
                break;
            case 1:
                this.numOfCavalry++;
                break;
            default:
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
    
    /**
    * @author Sajti Tamás
    */
    public boolean isMissionCompleted(){
        return missionCard.isCompleted( this );
    }
    
    /**
    * @author orsi, Sajti Tamás
    */
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
    
    /**
    * @author Sajti Tamás
    */
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
        
        removeOccupiedContinent(defendingTerritory);
        
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
        checkDefendingContinentAndAddToList(defendingTerritory);
    }

    /**
    * @author orsi
    */
    private void checkDefendingContinentAndAddToList(Territory territory){
        List<Territory> territories = territory.getContinent().getTerritories();
        boolean hasAll = true;
        for(Territory t : territories){
            if(!this.occupiedTerritories.contains(t)){
                hasAll = false;
                break;
            }            
        }
        if(hasAll){
            territory.getContinent().setOccupierPlayer(this);
            occupyContinent(territory.getContinent());
            System.out.println(this.name + " player all occupied all territories from " + territory.getContinent().getName());
        }
    }

    /**
    * @author Sajti Tamás
    * for testing hasOccupiedContinents
    */
    void occupyContinent(Continent continent) {
        this.occupiedContinents.add(continent);
    }
    
    private void removeOccupiedContinent(Territory territory){
        if(occupiedContinents.contains(territory.getContinent())){
            occupiedContinents.remove(territory.getContinent());
            territory.getContinent().removePlayer();        
        }
    }
    
    
    /**
    * @author Sajti Tamás
    */
    public int getOccupiedTerritoryCount() {
        return occupiedTerritories.size();
    }

    /**
    * @author Sajti Tamás
    */
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
        return Model.getInstance().isPlayerDead( color );
    }

    /**
    * @author Sajti Tamás
    */
    @Override
    public boolean hasOccupiedContinents(Continent... continents ) {
        return occupiedContinents.containsAll( Stream.of( continents ).collect( Collectors.toSet() ) );
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
        return occupiedContinents.stream().mapToInt( continent -> continent.getTroopBonusCount() ).sum();
    }

    public boolean isFinishedRegroup() {
        return finishedRegroup;
    }

    public void setFinishedRegroup(boolean finishedRegroup) {
        this.finishedRegroup = finishedRegroup;
    }

    @Override
    public int getOccupiedContinentCount() {
        return occupiedContinents.size();
    }

}
