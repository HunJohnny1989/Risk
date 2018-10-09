package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import model.dto.Missions;
import java.util.List;
import java.util.Random;
import model.dto.Color;
import model.dto.MissionCard;
import model.dto.RiskCard;
import model.dto.Territory;
import model.dto.Phase;

/**
 *
 * @author orsi
 */
public class Model {

    protected static final int STARTING_TROOP_COUNT = 30;

    private static Model instance;

    private int currentPlayerIndex;
    private List< Player> players;
    private Phase currentPhase;
    private static Random random = new Random();

    public static Model getInstance() {
        return instance;
    }

    /**
     * @author Sajti Tamás
     */
    public Model(String... playerNameList) {
        currentPlayerIndex = 0;
        currentPhase = Phase.PLACE_TROOPS;
        players = new LinkedList<>();
        
        initPlayers(playerNameList);       
        handOutMissionCards( );                
        instance = this;
    }

    private void initPlayers(String[] playerNameList) {
        for (int i = 0; i < playerNameList.length; i++) {
            players.add(new Player(playerNameList[i], Color.values()[i]));
        }
    }

    private void handOutMissionCards() {
        Missions.init( getPlayers() );
        for (int i = 0; i < players.size(); i++) {
            players.get( i ).addMissionCard( Missions.getRandomMissionCard() );
        }
    }
    
    /**
     * @author orsi
     */
    public String getCurrentPhaseName() {
        return getCurrentPhase().toString();
    }
    
    /**
     * @author Sajti Tamás
     */
    public Phase getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Split randomly 1 unit on each territory
     *
     * @param list
     */
    public void divideRandomTerritories(List<Territory> list) {
        if (players == null) {
            return;
        } else {
            for (Player p : players) {
                p.setRemainingPlaceableTroopCount(Model.STARTING_TROOP_COUNT);
            }
        }
        Collections.shuffle(list);
        int i = 0;
        for (Territory t : list) {
            players.get(i).placeTroops(t, 1);
            i++;
            if (i == players.size()) {
                i = 0;
            }
        }
        System.out.println("Randomly splitted the troops between Territories");
    }

    /**
     * @author Sajti Tamás
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * @author Sajti Tamás
     */
    public void playerLost(Player player) {
        players.remove(player);
    }

    /**
     * @author Sajti Tamás
     */
    public void giveRiskCardIfNecessary() {
        if (getCurrentPlayer().hasOccupiedTerritoryThisRound()) {
            getCurrentPlayer().addRiskCard(randomRiskCard());
        }
    }

    /**
     * @author Sajti Tamás
     */
    public void selectNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void nextPhase() {
        if (currentPhase.equals(Phase.ATTACK)) {
            currentPhase = Phase.REGROUP;
        } else if (currentPhase.equals(Phase.PLACE_TROOPS)) {
            currentPhase = Phase.ATTACK;
        } else if (currentPhase.equals(Phase.REGROUP)) {
            currentPhase = Phase.PLACE_TROOPS;
        }
    }

    public void finishAttack() {
        getCurrentPlayer().setFinishedAttack(true);
        boolean allFinished = true;
        for (Player p : players) {
            if (!p.isFinishedAttack()) {
                allFinished = false;
            }
        }
        if (allFinished) {
            for (Player p : players) {
                p.setFinishedAttack(false);
            }
            nextPhase();
        }
        selectNextPlayer();
    }
    
    public void finisRegroup() {
        getCurrentPlayer().setFinishedRegroup(true);
        boolean allFinished = true;
        for (Player p : players) {
            if (!p.isFinishedRegroup()) {
                allFinished = false;
            }
        }
        if (allFinished) {
            for (Player p : players) {
                p.setFinishedRegroup(false);
                p.setRemainingPlaceableTroopCount(Math.max( 3, p.getOccupiedTerritoryCount() / 3 ) + p.getContinentalTroopBonusTotal() );
            }            
            nextPhase();
        }
        selectNextPlayer();
    }

    public List<Player> getPlayers() {
        return players;
    }

    private RiskCard randomRiskCard() {   // todo
//        random.nextInt( )
        return new RiskCard("");
    }

    /**
     * @author Sajti Tamás
     */
    boolean isPlayerDead(Color color) {
        return players.stream().filter( p -> p.getColor() == color ).count() == 0;
    }

}
