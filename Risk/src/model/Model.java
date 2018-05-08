package model;

import java.util.ArrayList;
import java.util.Collections;
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
        players = new ArrayList<>(playerNameList.length);
        for (int i = 0; i < playerNameList.length; i++) {
            players.add(new Player(playerNameList[i], Color.values()[i], randomMissionCard()));
        }
        instance = this;
    }

    /**
     * Split randomly 1 unit on each territory
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
            if(i == players.size()) i = 0;            
        }
        System.out.println("Randomly splitted the troops between Territories");
    }

    /**
     * @author Sajti Tamás
     */
    public PlayerInterface getCurrentPlayer() {
        return getCurrentPlayerInternal();
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
        if (getCurrentPlayerInternal().hasOccupiedTerritoryThisRound()) {
            getCurrentPlayerInternal().addRiskCard(randomRiskCard());
        }
    }

    /**
     * @author Sajti Tamás
     */
    public void selectNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @author Sajti Tamás
     */
    private Player getCurrentPlayerInternal() {
        return players.get(currentPlayerIndex);
    }

    private MissionCard randomMissionCard() {
        return new MissionCard(Missions.getMissions().get(random.nextInt(Missions.getMissions().size())));
    }

    private RiskCard randomRiskCard() {   // todo
//        random.nextInt( )
        return new RiskCard("");
    }

}
