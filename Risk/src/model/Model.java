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

    public String getCurrentPhaseName() {
        switch (getCurrentPhase()) {
            case PLACE_TROOPS:
                return "Csapatok elhelyezése";
            case ATTACK:
                return "Támadás";
            case REGROUP:
                return "Átcsoportosítás";
        }
        return null;
    }

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

    public void nextPhase() {
        if (currentPhase.equals(Phase.ATTACK)) {
            currentPhase = Phase.REGROUP;
        } else if (currentPhase.equals(Phase.PLACE_TROOPS)) {
            currentPhase = Phase.ATTACK;
        } else if (currentPhase.equals(Phase.REGROUP)) {
            currentPhase = Phase.PLACE_TROOPS;
            final Player currentPlayer = players.get( currentPlayerIndex );
            currentPlayer.setRemainingPlaceableTroopCount( 
                    Math.max( 3, currentPlayer.getOccupiedTerritoryCount() / 3 ) + currentPlayer.getContinentalTroopBonusTotal() );
            //TODO kiszámolni hány katona jár a playereknek és odaadni nekik. continent kell me'g
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
